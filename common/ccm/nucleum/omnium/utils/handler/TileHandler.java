/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.registry.GameRegistry;

public final class TileHandler
{
    /** Single instance of this class */
    private static final TileHandler INSTANCE = new TileHandler();

    // /////////////////////////////
    // Instance Stuff
    // /////////////////////////////
    /** Map of all the TileEntitys */
    private final Map<Integer, TileEntity> tiles;

    private TileHandler()
    {
        tiles = new HashMap<Integer, TileEntity>();
    }

    public static TileHandler instance()
    {
        return INSTANCE;
    }

    static Map<Integer, TileEntity> tiles()
    {
        return instance().tiles;
    }

    // /////////////////////////////
    // Getters and "Setters"
    // /////////////////////////////
    /**
     * @param name
     *            The name of the TileEntity that you wish to get
     * @return The TileEntity's instance
     */
    public static TileEntity getTile(final String name)
    {
        final int id = hash(name).hashCode();

        if (tiles().containsKey(id))
        {
            return tiles().get(hash(name).hashCode());
        } else
        {
            throw new RuntimeException(String.format("Tring to retrive: %s but it didn't exist", name));
        }
    }

    /**
     * Registers a TileEntity into the game and stores the instance for later use
     * 
     * @param name
     *            The name of the TileEntity
     * @param tile
     *            The TileEntity's instance
     */
    public static void registerTile(final String name, final TileEntity tile)
    {
        final String id = hash(name);

        GameRegistry.registerTileEntity(tile.getClass(), id);

        tiles().put(id.hashCode(), tile);
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
        return ("CCM.ENTITY.TILE." + name + "." + name.hashCode());
    }
}