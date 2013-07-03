package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import ccm.nucleum_omnium.helper.enums.EnumHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileHandler {

	private static Map<Integer, TileEntity>	tileList	= new HashMap<Integer, TileEntity>();

	private static final TileHandler		instance	= new TileHandler();

	public static TileHandler instance() {
		return instance;
	}

	public void registerTileEntity(final String tileID, final TileEntity te) {

		GameRegistry.registerTileEntity(te.getClass(), hash(tileID));

		tileList.put(hash(tileID).hashCode(), te);
	}

	public TileEntity getEnumTE(final Enum<?> enu) {
		return getTileEntity(EnumHelper.getTileID(enu));
	}

	public TileEntity getTileEntity(final String tileID) {
		return tileList.get(hash(tileID).hashCode());
	}

	private static String hash(final String name) {
		return ("ccm.tile." + name + "." + name.hashCode());
	}
}