/*
 * Copyright (c) 2014 CCM modding crew.
 * View members of the CCM modding crew on https://github.com/orgs/CCM-Modding/members
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ccm.nucleumOmnium.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.common.DimensionManager;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Copy from N-O 1
 */
public class CommandTps extends CommandBase
{
    private static final DecimalFormat timeFormatter = new DecimalFormat("########0.000");

    @Override
    public String getCommandName()
    {
        return "tps";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/tps [worldID]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        int dim = 0;
        boolean summary = true;
        if (args.length > 0)
        {
            dim = parseInt(sender, args[0]);
            summary = false;
        }
        if (summary)
        {
            for (Integer dimId : DimensionManager.getIDs())
            {
                double worldTickTime = mean(MinecraftServer.getServer().worldTickTimes.get(dimId)) * 1.0E-6D;
                double worldTPS = Math.min(1000.0 / worldTickTime, 20);
                sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.forge.tps.summary", String.format("Dim %d", dimId), timeFormatter.format(worldTickTime), timeFormatter.format(worldTPS)));
            }
            double meanTickTime = mean(MinecraftServer.getServer().tickTimeArray) * 1.0E-6D;
            double meanTPS = Math.min(1000.0 / meanTickTime, 20);
            sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.forge.tps.summary", "Overall", timeFormatter.format(meanTickTime), timeFormatter.format(meanTPS)));
        }
        else
        {
            double worldTickTime = mean(MinecraftServer.getServer().worldTickTimes.get(dim)) * 1.0E-6D;
            double worldTPS = Math.min(1000.0 / worldTickTime, 20);
            sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.forge.tps.summary", String.format("Dim %d", dim), timeFormatter.format(worldTickTime), timeFormatter.format(worldTPS)));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender)
    {
        return true;
    }

    private static long mean(long[] values)
    {
        long sum = 0l;
        for (long v : values)
        {
            sum += v;
        }

        return sum / values.length;
    }

    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args)
    {
        if (args.length == 1)
        {
            String[] strings = new String[DimensionManager.getIDs().length];
            for (int i = 0; i < DimensionManager.getIDs().length; i++)
                strings[i] = DimensionManager.getIDs()[i].toString();
            return getListOfStringsMatchingLastWord(args, strings);
        }
        return null;
    }
}
