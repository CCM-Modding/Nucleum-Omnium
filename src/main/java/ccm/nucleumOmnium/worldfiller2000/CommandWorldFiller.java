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

package ccm.nucleumOmnium.worldfiller2000;

import ccm.nucleumOmnium.worldfiller2000.Filler;
import ccm.nucleumOmnium.worldfiller2000.Shape;
import ccm.nucleumOmnium.worldfiller2000.TickHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.Arrays;
import java.util.List;

public class CommandWorldFiller extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "worldfiller";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "Use \"/worldfiller help\" for help.";
    }

    @Override
    public List getCommandAliases()
    {
        return Arrays.asList("wf");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length <= 1 || args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("set") && args.length < 6))
        {
            helpText(sender);
            return;
        }
        int dim = parseInt(sender, args[1]);
        World world = DimensionManager.getWorld(dim);
        if (world == null)
        {
            throw new WrongUsageException("World " + dim + " isn't loaded or doesn't exists.");
        }
        Filler filler = TickHandler.INSTANCE.map.get(dim);
        if (args[0].equalsIgnoreCase("start"))
        {
            if (filler == null) throw new WrongUsageException("World " + dim + " doesn't have a filler set.");
            if (filler.isEnabled()) throw new WrongUsageException("Filler for " + dim + " already started.");
            filler.start();
        }
        else if (args[0].equalsIgnoreCase("stop"))
        {
            if (filler == null) throw new WrongUsageException("World " + dim + " doesn't have a filler set.");
            if (!filler.isEnabled()) throw new WrongUsageException("Filler for " + dim + " already stopped.");
            filler.stop();
        }
        else if (args[0].equalsIgnoreCase("set"))
        {
            if (filler != null && filler.isEnabled()) throw new WrongUsageException("Filler for world " + dim + " is running. Stop before configuring.");
            if (filler != null) TickHandler.INSTANCE.map.remove(dim);

            Shape shape = Shape.valueOf(args[2].toUpperCase());
            int centerX = parseInt(sender, args[3]) / 16;
            int centerZ = parseInt(sender, args[4]) / 16;
            int radius = parseIntWithMin(sender, args[5], 1);

            new Filler(dim, shape, centerX, centerZ, radius);
            sender.sendChatToPlayer(ChatMessageComponent.createFromText("Filler made. Don't forget to start it.").setColor(EnumChatFormatting.GOLD));
        }
        else if (args[0].equalsIgnoreCase("speed"))
        {
            if (filler == null) throw new WrongUsageException("World " + dim + " doesn't have a filler set.");
            if (args.length < 3)
            {
                sender.sendChatToPlayer(ChatMessageComponent.createFromText("The filler for " + dim + " is doing " + filler.getSpeed() + " chunks/sec."));
            }
            else
            {
                filler.setSpeed(parseIntWithMin(sender, args[2], 1));
            }
        }
        else helpText(sender);
    }

    private void helpText(ICommandSender sender)
    {
        sender.sendChatToPlayer(ChatMessageComponent.createFromText("-= Command usage =-").setColor(EnumChatFormatting.AQUA));
        sender.sendChatToPlayer(ChatMessageComponent.createFromText("Start and stop: /wf <start|stop> <dim>").setColor(EnumChatFormatting.AQUA));
        sender.sendChatToPlayer(ChatMessageComponent.createFromText("Set: /wf <set> <dim> <round|square> <centerX> <centerZ> <rad>").setColor(EnumChatFormatting.AQUA));
        sender.sendChatToPlayer(ChatMessageComponent.createFromText("Speed: /wf <speed> <dim> [chunks per tick]").setColor(EnumChatFormatting.AQUA));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 1:
                return getListOfStringsMatchingLastWord(args, "help", "start", "stop", "set", "speed");
            case 2:
                return getListOfStringsMatchingLastWord(args, getDimIds());
            case 3:
                if (args[0].equalsIgnoreCase("set")) return getListOfStringsMatchingLastWord(args, "round", "square");
        }
        return null;
    }

    public String[] getDimIds()
    {
        String[] out = new String[DimensionManager.getIDs().length];
        for (int i = 0; i < out.length; i++) out[i] = DimensionManager.getIDs()[i].toString();
        return out;
    }
}
