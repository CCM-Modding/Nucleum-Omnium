/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

public final class GuiHandler implements IGuiHandler
{
    private Map<Integer, GuiWrapper> guis;
    
    private GuiHandler()
    {
        guis = new HashMap<Integer, GuiWrapper>();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
    
    
    /** Opens the desired GUI for the Player */
    public static void openGui(final String guiID, final EntityPlayer player, final int x, final int y, final int z)
    {
        //final int fix = hash(guiID);
        //if (instance().handlerList.containsKey(fix))
        //{
        //    player.openGui(NucleumOmnium.instance, fix, player.worldObj, x, y, z);
        //} else
        //{
        //    CCMLogger.DEFAULT_LOGGER.severe("PLAYER: %s, TRIED TO OPEN %s BUT IT IS NOT REGISTERED!!!\n", player.username, guiID);
        //}
    }
}