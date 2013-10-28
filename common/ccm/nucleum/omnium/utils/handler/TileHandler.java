/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.registry.GameRegistry;

import ccm.nucleum.omnium.utils.helper.CCMLogger;

public final class TileHandler
{
    /** Map of all the TileEntitys */
    private static final Map<Integer, Class<? extends TileEntity>> tiles = new HashMap<Integer, Class<? extends TileEntity>>();

    // /////////////////////////////
    // Getters and "Setters"
    // /////////////////////////////
    /**
     * @param name
     *            The name of the TileEntity that you wish to get
     * @return The TileEntity's instance
     */
    public static Class<? extends TileEntity> getTile(final String name)
    {
        final int id = hash(name).hashCode();

        if (tiles.containsKey(id))
        {
            return tiles.get(id);
        } else
        {
            throw new RuntimeException(String.format("Tring to retrive: %s but it didn't exist", name));
        }
    }

    public static TileEntity getTileInstance(String name)
    {
        TileEntity tile = null;
        try
        {
            tile = getTile(name).newInstance();
        } catch (Exception e)
        {
            CCMLogger.DEFAULT_LOGGER.printCatch(e, "FAILED TO CREATE A NEW INSTANCE OF %s", name);
        }
        return tile;
    }

    /**
     * Registers a TileEntity into the game and stores the instance for later use
     * 
     * @param name
     *            The name of the TileEntity
     * @param tile
     *            The TileEntity's instance
     */
    public static void registerTile(final String name, final Class<? extends TileEntity> tile)
    {
        final String id = hash(name);

        GameRegistry.registerTileEntity(tile, id);

        tiles.put(id.hashCode(), tile);
    }

    // /////////////////////////////
    // Internal Helpers
    // /////////////////////////////
    /**
     * @param name
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Tile's name
     */
    private static String hash(final String name)
    {
        return ("CCM.ENTITY.TILE." + name.toUpperCase() + "." + name.hashCode());
    }
}