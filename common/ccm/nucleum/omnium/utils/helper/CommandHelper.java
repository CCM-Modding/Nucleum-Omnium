/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

/**
 * CommandHelper
 * <p>
 * 
 * @author Captain_Shadows
 */
public class CommandHelper
{
    /**
     * Gets a player
     * 
     * @param sender
     * @return The EntityPlayerMP corresponding to who send it
     */
    public static EntityPlayerMP getPlayer(final ICommandSender sender)
    {
        return CommandBase.getCommandSenderAsPlayer(sender);
    }

    /**
     * Gets a player name form a specified string
     * 
     * @param name
     *            The name of the User
     * @param sender
     * @return The EntityPlayerMP corresponding to the name
     */
    public static EntityPlayerMP getPlayer(final ICommandSender sender, final String name)
    {
        final EntityPlayerMP player = CommandBase.func_82359_c(sender, name);

        if (player == null)
        {
            throw new PlayerNotFoundException();
        }
        return player;
    }

    public static void sendChat(final ICommandSender sender)
    {
        sender.sendChatToPlayer(ChatMessageComponent.func_111077_e(""));
    }

    public static void sendChat(final ICommandSender sender, final String msg, final Object... objects)
    {
        sender.sendChatToPlayer(ChatMessageComponent.func_111077_e(String.format(msg, objects)));
    }
}