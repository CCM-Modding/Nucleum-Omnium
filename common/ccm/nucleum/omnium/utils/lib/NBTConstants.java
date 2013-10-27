/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.lib;


public final class NBTConstants
{
    /*
     * Base Stuff
     */
    public static final String NBT_CMM = "CCM";

    public static final String NBT_INVENTORY = NBT_CMM + ".INVENTORY";

    public static final String NBT_ENTITY = NBT_CMM + ".ENTITY";
    
    public static final String NBT_TILE_ENTITY = NBT_ENTITY + ".TILE";
    
    public static final String NBT_INVENTORY_SLOT = NBT_INVENTORY + ".SLOT";
    
    public static final String NBT_INVENTORY_SIZE = NBT_INVENTORY + ".SIZE";

    /*
     * NBT Tile Stuff
     */
    public static final String NBT_TE_DIRECTION = NBT_TILE_ENTITY + ".DIRECTION";

    public static final String NBT_TE_CUSTOM_NAME = NBT_TILE_ENTITY + ".NAME";

    public static final String NBT_TE_OWNER = NBT_TILE_ENTITY + ".OWNER";

    public static final String NBT_TE_PROGRESS = NBT_TILE_ENTITY + ".PROGRESS";

    public static final String NBT_TE_STATE = NBT_TILE_ENTITY + ".STATE";
}