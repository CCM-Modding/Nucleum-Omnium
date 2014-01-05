/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
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

    public static void sendChat(final ICommandSender sender)
    {
        sender.sendChatToPlayer(ChatMessageComponent.createFromText(""));
    }

    public static void sendChat(final ICommandSender sender, final String msg, final Object... objects)
    {
        sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions(msg, objects));
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