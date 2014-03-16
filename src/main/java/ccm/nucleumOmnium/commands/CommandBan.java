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

import ccm.nucleumOmnium.helpers.JavaHelper;
import net.minecraft.command.CommandServerBan;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;

import java.util.concurrent.TimeUnit;

/**
 * Copy from N-O 1
 */
public class CommandBan extends CommandServerBan
{
    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/ban <target player> [reason ...] [time scale] {time}";
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if ((args.length >= 1) && (args[0].length() > 0))
        {
            EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args[0]);
            BanEntry banentry = new BanEntry(args[0]);
            banentry.setBannedBy(sender.getCommandSenderName());
            StringBuilder sb = new StringBuilder();
            sb.append("You have been banned from the server for ");
            if (args.length >= 2)
            {
                banentry.setBanReason(func_82360_a(sender, args, 1));
            }
            if (args.length >= 4)
            {
                if (JavaHelper.isNumeric(args[2]))
                {
                    TimeUnit scale = getUnit(args[3]);
                    banentry.setBanEndDate(JavaHelper.getDate(scale, Integer.parseInt(args[3])));
                    sb.append(args[3]);
                    sb.append(" ");
                    sb.append(scale.name());
                }
            }
            MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(banentry);
            if (entityplayermp != null)
            {
                entityplayermp.playerNetServerHandler.kickPlayerFromServer(sb.toString());
            }
            notifyAdmins(sender, "commands.ban.success", args[0]);
        }
        else
        {
            throw new WrongUsageException("commands.ban.usage");
        }
    }

    TimeUnit getUnit(final String name)
    {
        if (name.equalsIgnoreCase("S") || name.equalsIgnoreCase("Seconds"))
        {
            return TimeUnit.SECONDS;
        }
        else if (name.equalsIgnoreCase("M") || name.equalsIgnoreCase("Minutes"))
        {
            return TimeUnit.MINUTES;
        }
        else if (name.equalsIgnoreCase("H") || name.equalsIgnoreCase("Hours"))
        {
            return TimeUnit.HOURS;
        }
        else if (name.equalsIgnoreCase("D") || name.equalsIgnoreCase("Days"))
        {
            return TimeUnit.DAYS;
        }
        else
        {
            return null;
        }
    }
}
