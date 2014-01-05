/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.inventory.container.BaseContainer;
import ccm.nucleum.omnium.inventory.gui.BaseGui;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class GuiHandler implements IGuiHandler
{
    private final Map<Integer, Wrapper> guis;
    public static final GuiHandler INSTANCE = new GuiHandler();

    private GuiHandler()
    {
        guis = new HashMap<Integer, Wrapper>();
    }

    @SideOnly(Side.CLIENT)
    public static void addGui(String guiID, BaseContainer container, BaseGui gui)
    {
        INSTANCE.guis.put(hashGUI(guiID), new Wrapper(container, gui));
    }

    public static void addContainer(String guiID, BaseContainer container)
    {
        INSTANCE.guis.put(hashGUI(guiID), new Wrapper(container));
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return INSTANCE.guis.get(ID).getServer().getServerGuiElement(player, world, x, y, z);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return INSTANCE.guis.get(ID).getClient().getClientGuiElement(player, world, x, y, z);
    }

    /** Opens the desired GUI for the Player */
    public static void openGui(final String guiID, final EntityPlayer player, final int x, final int y, final int z)
    {
        final int id = hashGUI(guiID);
        if (INSTANCE.guis.containsKey(id))
        {
            player.openGui(NucleumOmnium.instance, id, player.worldObj, x, y, z);
        } else
        {
            CCMLogger.DEFAULT.severe("PLAYER: %s, TRIED TO OPEN %s BUT IT IS NOT REGISTERED!!!\n", player.username, guiID);
        }
    }

    private static int hashGUI(String id)
    {
        return ("CCM.GUI." + id.toUpperCase() + "." + id.hashCode()).hashCode();
    }
}