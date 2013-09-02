/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatMessageComponent;

import cpw.mods.fml.common.FMLCommonHandler;

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
     * @return The EntityPlayerMP corresponding to who send it
     */
    public static EntityPlayerMP getPlayer(final ICommandSender sender)
    {
        return CommandBase.getCommandSenderAsPlayer(sender);
    }

    /**
     * Gets a player name form a specified string
     * 
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

    /** OP detection */
    public static boolean isPlayerOp(final String username)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            return true;
        }

        final MinecraftServer server = FMLCommonHandler.instance().getSidedDelegate().getServer();

        // SP and LAN
        if (server.isSinglePlayer())
        {
            if ((server instanceof IntegratedServer) && server.getServerOwner().equalsIgnoreCase(username))
            {
                return true;
            }
        }

        // SMP
        return server.getConfigurationManager().getOps().contains(username);
    }
}