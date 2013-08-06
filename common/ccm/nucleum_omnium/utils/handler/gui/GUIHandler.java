/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.utils.handler.LogHandler;

public final class GUIHandler implements IGuiHandler
{

    /**
     * List of all the GUI Containers
     */
    private final Map<Integer, GuiHandling> guiList;

    /**
     * List of all the Containers
     */
    private final Map<Integer, GuiHandling> containerList;

    /**
     * Private single instance
     */
    private static final GUIHandler         INSTANCE = new GUIHandler();

    /**
     * @param name
     *            The name of the Block
     * @return The "unique" hash code of the Block's name
     */
    private static int hash(final String name)
    {
        final String fix = "ccm.gui." + name + "." + name.hashCode();
        return fix.hashCode();
    }

    /**
     * @return The GUIHandler's Instance
     */
    public static GUIHandler instance()
    {
        return INSTANCE;
    }

    /**
     * Opens the desired GUI for the Player
     * 
     * @param guiID
     *            The name of the machine
     * @param player
     *            The player trying to open it
     * @param world
     *            The world that the player and machine are in
     * @param x
     *            The x coordinate of the Block
     * @param y
     *            The y coordinate of the Block
     * @param z
     *            The z coordinate of the Block
     */
    public static void openGui(final String guiID,
                               final EntityPlayer player,
                               final World world,
                               final int x,
                               final int y,
                               final int z)
    {
        final int fix = hash(guiID);
        if (instance().containerList.containsKey(fix))
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
    public static void registerGuiClient(final String guiID,
                                         final Class<? extends GuiContainer> gui,
                                         final Class<? extends Container> container)
    {
        instance().guiList.put(hash(guiID), gui);
        instance().containerList.put(hash(guiID), container);
    }

    /**
     * Registers a Container on the server
     * 
     * @param guiID
     *            The name of the Block that this Container is associated to
     * @param container
     *            The container class
     */
    public static void registerGuiServer(final String guiID, final Class<? extends Container> container)
    {
        instance().containerList.put(hash(guiID), container);
    }

    /**
     * Private constructor
     */
    private GUIHandler()
    {
        guiList = new HashMap<Integer, GuiHandling>();
        containerList = new HashMap<Integer, GuiHandling>();
    }

    @Override
    public Object getClientGuiElement(final int ID,
                                      final EntityPlayer player,
                                      final World world,
                                      final int x,
                                      final int y,
                                      final int z)
    {
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        GuiContainer gui = null;
        try
        {
            gui = instance().guiList.get(ID)
                                    .getConstructor(InventoryPlayer.class, TileEntity.class)
                                    .newInstance(player.inventory, te);
        } catch (final Exception e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return gui;
    }

    @Override
    public Object getServerGuiElement(final int ID,
                                      final EntityPlayer player,
                                      final World world,
                                      final int x,
                                      final int y,
                                      final int z)
    {
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        Container container = null;
        try
        {
            container = instance().containerList.get(ID)
                                                .getConstructor(InventoryPlayer.class, TileEntity.class)
                                                .newInstance(player.inventory, te);
        } catch (final Exception e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return container;
    }
}