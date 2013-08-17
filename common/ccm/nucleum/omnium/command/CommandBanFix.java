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
        return "commands.ban.fix.usage";
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if ((args.length >= 1) && (args[0].length() > 0))
        {
            final EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args[0]);
            final BanEntry banentry = new BanEntry(args[0]);
            banentry.setBannedBy(sender.getCommandSenderName());

            if (args.length >= 2)
            {
                banentry.setBanReason(func_82360_a(sender, args, 1));
            }

            if (args.length >= 4)
            {
                if (JavaHelper.isNumeric(args[3]))
                {
                    final String scale = args[2];
                    if (scale.equals("S") || scale.equalsIgnoreCase("Seconds"))
                    {
                        banentry.setBanEndDate(JavaHelper.getDate(TimeUnit.SECONDS, Integer.parseInt(args[3])));
                    }
                    if (scale.equals("M") || scale.equalsIgnoreCase("Minutes"))
                    {
                        banentry.setBanEndDate(JavaHelper.getDate(TimeUnit.MINUTES, Integer.parseInt(args[3])));
                    }
                    if (scale.equals("H") || scale.equalsIgnoreCase("Hours"))
                    {
                        banentry.setBanEndDate(JavaHelper.getDate(TimeUnit.HOURS, Integer.parseInt(args[3])));
                    }
                    if (scale.equals("D") || scale.equalsIgnoreCase("Days"))
                    {
                        banentry.setBanEndDate(JavaHelper.getDate(TimeUnit.DAYS, Integer.parseInt(args[3])));
                    }
                }
            }

            MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(banentry);

            if (entityplayermp != null)
            {
                entityplayermp.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
            }

            notifyAdmins(sender, "commands.ban.success", new Object[]
            { args[0] });
        } else
        {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
    }
}