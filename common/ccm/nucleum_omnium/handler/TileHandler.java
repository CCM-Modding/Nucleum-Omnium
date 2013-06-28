package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileHandler {

	private static Map<Integer, Class<? extends TileEntity>>	tileList	= new HashMap<Integer, Class<? extends TileEntity>>();

	private static TileHandler									instance;

	public static TileHandler instance() {
		if (TileHandler.instance == null) {
			TileHandler.instance = new TileHandler();
		}
		return TileHandler.instance;
	}

	public void registerTileEntity(final String tileID, final Class<? extends TileEntity> te) {

		GameRegistry.registerTileEntity(te, hash(tileID));

		tileList.put(hash(tileID).hashCode(), te);
	}

	public Class<? extends TileEntity> getTileEntity(final String tileID) {
		return tileList.get(hash(tileID).hashCode());
	}

	private static String hash(final String name) {
		return ("ccm.tile." + name + "." + name.hashCode());
	}
}