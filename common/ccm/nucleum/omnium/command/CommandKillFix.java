/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.command;

import static ccm.nucleum.omnium.utils.helper.CommandHelper.sendChat;
import net.minecraft.command.CommandKill;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import ccm.nucleum.omnium.utils.helper.CommandHelper;

public class CommandKillFix extends CommandKill
{
    @Override
    public String getCommandUsage(final ICommandSender sender)
    {
        return "commands.kill.fix.usage";
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if (args.length == 1)
        {
            final EntityPlayerMP playerDead = getPlayer(sender, args[0]);
            playerDead.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
            sendChat(playerDead, "commands.kill.success");
        } else
        {
            final EntityPlayerMP playerDead = CommandHelper.getPlayer(sender);
            if (args.length == 0)
            {
                playerDead.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
                sendChat(playerDead, "commands.kill.success");
            } else
            {
                sendChat(playerDead, getCommandUsage(sender));
            }
        }
    }
}