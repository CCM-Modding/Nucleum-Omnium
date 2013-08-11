/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.utils.handler.LogHandler;

public final class GuiHandler implements IGuiHandler
{

    /**
     * List of all the GUI Handlers
     */
    private final Map<Integer, GuiHandling> handlerList;

    /**
     * Private single instance
     */
    private static final GuiHandler         INSTANCE = new GuiHandler();

    /**
     * Private constructor
     */
    private GuiHandler()
    {
        handlerList = new HashMap<Integer, GuiHandling>();
    }

    /**
     * @return The GUIHandler's Instance
     */
    public static GuiHandler instance()
    {
        return INSTANCE;
    }

    /**
     * Registers a GUI, and a Container on the client
     * 
     * @param guiID
     *            The name of the Block that this GUI and Container are associated to
     * @param gui
     *            The GUI class
     * @param container
     *            The Container class
     */
    public static void registerGuiClient(final String guiID, final GuiHandling handler)
    {
        instance().handlerList.put(hash(guiID), handler);
    }

    /**
     * Registers a Container on the server
     * 
     * @param guiID
     *            The name of the Block that this Container is associated to
     * @param container
     *            The container class
     */
    public static void registerGuiServer(final String guiID, final GuiHandling handler)
    {
        instance().handlerList.put(hash(guiID), handler);
    }

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

    @Override
    public Object getClientGuiElement(final int ID,
                                      final EntityPlayer player,
                                      final World world,
                                      final int x,
                                      final int y,
                                      final int z)
    {
        return instance().handlerList.get(ID).getClientGuiElement(player, world, x, y, z);
    }

    @Override
    public Object getServerGuiElement(final int ID,
                                      final EntityPlayer player,
                                      final World world,
                                      final int x,
                                      final int y,
                                      final int z)
    {
        return instance().handlerList.get(ID).getServerGuiElement(player, world, x, y, z);
    }

    /**
     * Opens the desired GUI for the Player
     */
    public static void openGui(final String guiID,
                               final EntityPlayer player,
                               final World world,
                               final int x,
                               final int y,
                               final int z)
    {
        final int fix = hash(guiID);
        if (instance().handlerList.containsKey(fix))
        {
            player.openGui(NucleumOmnium.instance, fix, world, x, y, z);
        }
        else
        {
            LogHandler.severe(NucleumOmnium.instance,
                              "Player: %s, tried to open %s but it is not registered!! \n",
                              player.username,
                              guiID);
        }
    }
}