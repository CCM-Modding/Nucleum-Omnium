package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ccm.nucleum_omnium.NucleumOmnium;
import cpw.mods.fml.common.network.IGuiHandler;

public final class GUIHandler implements IGuiHandler {

	/**
	 * List of all the GUI Containers
	 */
	private final Map<Integer, Class<? extends GuiContainer>>	guiList;

	/**
	 * List of all the Containers
	 */
	private final Map<Integer, Class<? extends Container>>		containerList;

	/**
	 * Private single instance
	 */
	private static final GUIHandler								INSTANCE	= new GUIHandler();

	/**
	 * Private constructor
	 */
	private GUIHandler() {
		guiList = new HashMap<Integer, Class<? extends GuiContainer>>();
		containerList = new HashMap<Integer, Class<? extends Container>>();
	}

	/**
	 * @return The GUIHandler's Instance
	 */
	public static GUIHandler instance() {
		return INSTANCE;
	}

	/**
	 * Registers a Container on the server
	 * 
	 * @param guiID
	 *            The name of the Block that this Container is associated to
	 * @param container
	 *            The container class
	 */
	public static void registerGuiServer(final String guiID, final Class<? extends Container> container) {
		instance().containerList.put(hash(guiID), container);
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
	public static void registerGuiClient(	final String guiID,
											final Class<? extends GuiContainer> gui,
											final Class<? extends Container> container) {
		instance().guiList.put(hash(guiID), gui);
		instance().containerList.put(hash(guiID), container);
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
	public static void openGui(	final String guiID,
								final EntityPlayer player,
								final World world,
								final int x,
								final int y,
								final int z) {
		int fix = hash(guiID);
		if (instance().containerList.containsKey(fix)) {
			player.openGui(NucleumOmnium.instance, fix, world, x, y, z);
		} else {
			LogHandler.log(	NucleumOmnium.instance,
							String.format(	"Player: %s, tried to open %s but it is not registered!! \n",
											player.username,
											guiID));
		}
	}

	/**
	 * @param name
	 *            The name of the Block
	 * @return The "unique" hash code of the Block's name
	 */
	private static int hash(final String name) {
		final String fix = "ccm.gui." + name + "." + name.hashCode();
		LogHandler.log(fix);
		return fix.hashCode();
	}

	@Override
	public Object getServerGuiElement(	final int ID,
										final EntityPlayer player,
										final World world,
										final int x,
										final int y,
										final int z) {
		final TileEntity te = world.getBlockTileEntity(x, y, z);
		Container container = null;
		try {
			container = instance().containerList.get(ID)
												.getConstructor(InventoryPlayer.class, TileEntity.class)
												.newInstance(player.inventory, te);
		} catch (final Exception e) {
			e.getCause();
			e.printStackTrace();
		}
		return container;
	}

	@Override
	public Object getClientGuiElement(	final int ID,
										final EntityPlayer player,
										final World world,
										final int x,
										final int y,
										final int z) {
		final TileEntity te = world.getBlockTileEntity(x, y, z);
		GuiContainer gui = null;
		try {
			gui = instance().guiList.get(ID)
									.getConstructor(InventoryPlayer.class, TileEntity.class)
									.newInstance(player.inventory, te);
		} catch (final Exception e) {
			e.getCause();
			e.printStackTrace();
		}
		return gui;
	}
}