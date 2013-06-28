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

	private static Map<Integer, Class<? extends GuiContainer>>	guiList			= new HashMap<Integer, Class<? extends GuiContainer>>();

	private static Map<Integer, Class<? extends Container>>		containerList	= new HashMap<Integer, Class<? extends Container>>();

	private static GUIHandler									instance;

	public static GUIHandler instance() {
		if (GUIHandler.instance == null) {
			GUIHandler.instance = new GUIHandler();
		}
		return GUIHandler.instance;
	}

	public static void registerGuiServer(final String guiID, final Class<? extends Container> container) {
		GUIHandler.containerList.put(hash(guiID), container);
	}

	public static void registerGuiClient(	final String guiID,
											final Class<? extends GuiContainer> gui,
											final Class<? extends Container> container) {
		GUIHandler.guiList.put(hash(guiID), gui);
		GUIHandler.containerList.put(hash(guiID), container);
	}

	public static void openGui(	final String guiID,
								final EntityPlayer player,
								final World world,
								final int x,
								final int y,
								final int z) {
		player.openGui(NucleumOmnium.instance, hash(guiID), world, x, y, z);
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
			container = GUIHandler.containerList.get(ID)
												.getConstructor(InventoryPlayer.class, TileEntity.class)
												.newInstance(player.inventory, te);
		} catch (final Exception e) {
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
			gui = GUIHandler.guiList.get(ID)
									.getConstructor(InventoryPlayer.class, TileEntity.class)
									.newInstance(player.inventory, te);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return gui;
	}

	private static int hash(final String name) {
		return ("ccm.gui." + name + "." + name.hashCode()).hashCode();
	}
}