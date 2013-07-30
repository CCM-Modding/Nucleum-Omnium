package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.registry.GameRegistry;

import ccm.nucleum_omnium.helper.enums.EnumHelper;

public final class TileHandler {

    /**
     * Map of all the TileEntitys
     */
    private final Map<Integer, TileEntity> tileList;

    /**
     * Private single instance
     */
    private static final TileHandler       INSTANCE = new TileHandler();

    /**
     * Private constructor
     */
    private TileHandler() {
        tileList = new HashMap<Integer, TileEntity>();
    }

    /**
     * Registers a TileEntity into the game and stores the instance for later use
     * 
     * @param tileID
     *            The name of the TileEntity
     * @param te
     *            The TileEntity's instance
     */
    public static void registerTileEntity(final String tileID, final TileEntity te) {

        final String id = hash(tileID);

        GameRegistry.registerTileEntity(te.getClass(), id);

        INSTANCE.tileList.put(id.hashCode(), te);
    }

    /**
     * @param tileID
     *            The name of the TileEntity that you wish to get
     * @return The TileEntity's instance
     */
    public static TileEntity getTileEntity(final String tileID) {
        final int id = hash(tileID).hashCode();

        if (INSTANCE.tileList.containsKey(id)) {
            return INSTANCE.tileList.get(hash(tileID).hashCode());
        } else {
            throw new RuntimeException(String.format("Tring to retrive: %s but it didn't exist", tileID));
        }
    }

    /**
     * @param enu
     *            The enum constant associated with the TileEntity
     * @return The TileEntity's instance
     */
    public static TileEntity getEnumTE(final Enum<?> enu) {
        return getTileEntity(EnumHelper.getTileID(enu));
    }

    /**
     * @param name
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Block's name
     */
    private static String hash(final String name) {
        return ("ccm.tile." + name + "." + name.hashCode());
    }
}