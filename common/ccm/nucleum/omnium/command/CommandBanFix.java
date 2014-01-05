/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.command;

import java.util.concurrent.TimeUnit;

import net.minecraft.command.CommandServerBan;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;
import ccm.nucleum.omnium.utils.helper.JavaHelper;
import ccm.nucleum.omnium.utils.lib.Commands;

/**
 * CommandTmpBan
 * <p>
 * 
 * @author Captain_Shadows
 */
public class CommandBanFix extends CommandServerBan
{
    @Override
    public String getCommandUsage(final ICommandSender sender)
    {
        return Commands.COMMAND_BAN_USAGE;
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if ((args.length >= 1) && (args[0].length() > 0))
        {
            final EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args[0]);
            final BanEntry banentry = new BanEntry(args[0]);
            banentry.setBannedBy(sender.getCommandSenderName());
            final StringBuilder sb = new StringBuilder();
            sb.append("You have been banned from the server for ");
            if (args.length >= 2)
            {
                banentry.setBanReason(func_82360_a(sender, args, 1));
            }
            if (args.length >= 4)
            {
                if (JavaHelper.isNumeric(args[2]))
                {
                    final TimeUnit scale = getUnit(args[3]);
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
            notifyAdmins(sender, "commands.ban.success", new Object[] { args[0] });
        } else
        {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
    }

    TimeUnit getUnit(final String name)
    {
        if (name.equals("S") || name.equalsIgnoreCase("Seconds"))
        {
            return TimeUnit.DAYS;
        } else if (name.equals("M") || name.equalsIgnoreCase("Minutes"))
        {
            return TimeUnit.DAYS;
        } else if (name.equals("H") || name.equalsIgnoreCase("Hours"))
        {
            return TimeUnit.DAYS;
        } else if (name.equals("D") || name.equalsIgnoreCase("Days"))
        {
            return TimeUnit.DAYS;
        } else
        {
            return null;
        }
    }
}