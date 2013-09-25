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
    /*
     * Data Fields
     */
    /** List of all the GUI Handlers */
    private final Map<Integer, AbstractGuiHandler> handlerList;

    /** Private single instance */
    private static final GuiHandler INSTANCE = new GuiHandler();

    /*
     * Initialization Related Things
     */
    /** Private constructor */
    private GuiHandler()
    {
        handlerList = new HashMap<Integer, AbstractGuiHandler>();
    }

    /**
     * @return The GUIHandler's Instance
     */
    public static GuiHandler instance()
    {
        return INSTANCE;
    }

    /*
     * Overrides
     */
    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        return instance().handlerList.get(ID).getClientGuiElement(player, world, x, y, z);
    }

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        return instance().handlerList.get(ID).getServerGuiElement(player, world, x, y, z);
    }

    /*
     * Data Registers
     */
    /**
     * Registers a GuiHandler on the client
     * 
     * @param guiID
     *            The name of the Block that this GUI and Container are associated to
     */
    public static void registerGuiClient(final String guiID, final AbstractGuiHandler handler)
    {
        instance().handlerList.put(hash(guiID), handler);
    }

    /**
     * Registers a GuiHandler on the server
     * 
     * @param guiID
     *            The name of the Block that this GUI and Container are associated to
     */
    public static void registerGuiServer(final String guiID, final AbstractGuiHandler handler)
    {
        instance().handlerList.put(hash(guiID), handler);
    }

    /*
     * Easier Data Registers
     */

    /**
     * Registers a GuiHandler on the client
     * 
     * @param guiID
     *            The name of the Block that this GUI and Container are associated to
     */
    public static void registerGuiClient(final String guiID, final Class<? extends GuiContainer> gui, final Class<? extends Container> container)
    {
        instance().handlerList.put(hash(guiID), new TileGuiHandler(gui, container));
    }

    /** Registers a GuiHandler on the server */
    public static void registerGuiServer(final String guiID, final Class<? extends Container> container)
    {
        instance().handlerList.put(hash(guiID), new TileGuiHandler(container));
    }

    /*
     * Helpers
     */
    /**
     * @param name
     *            The name of the Block
     * @return The "unique" hash code of the Block's name
     */
    private static int hash(final String name)
    {
        final String fix = "CCM.GUI." + name.toUpperCase() + "." + name.hashCode();
        return fix.hashCode();
    }

    /** Opens the desired GUI for the Player */
    public static void openGui(final String guiID, final EntityPlayer player, final int x, final int y, final int z)
    {
        final int fix = hash(guiID);
        if (instance().handlerList.containsKey(fix))
        {
            player.openGui(NucleumOmnium.instance, fix, player.worldObj, x, y, z);
        } else
        {
            CCMLogger.DEFAULT_LOGGER.severe("PLAYER: %s, TRIED TO OPEN %s BUT IT IS NOT REGISTERED!!!\n", player.username, guiID);
        }
    }
}