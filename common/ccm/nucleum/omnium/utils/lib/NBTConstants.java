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

    public static final String NBT_INVENTORY = "Items";

    public static final String NBT_ENTITY = NBT_CMM + ".ENTITY";

    public static final String NBT_TILE_ENTITY = NBT_ENTITY + ".TILE";

    public static final String NBT_INVENTORY_SLOT = "Slot";

    public static final String NBT_INVENTORY_SIZE = NBT_CMM + "INVENTORY.SIZE";

    public static final String NBT_CONTAINER = NBT_CMM + ".CONTAINER";

    public static final String NBT_CONTAINER_ELEMENT = NBT_CONTAINER + ".ELEMENT";

    /*
     * NBT Tile Stuff
     */
    public static final String NBT_TE_DIRECTION = NBT_TILE_ENTITY + ".DIRECTION";

    public static final String NBT_TE_CUSTOM_NAME = "CustomName";

    public static final String NBT_TE_OWNER = NBT_TILE_ENTITY + ".OWNER";

    public static final String NBT_TE_PROGRESS = NBT_TILE_ENTITY + ".PROGRESS";

    public static final String NBT_TE_STATE = NBT_TILE_ENTITY + ".STATE";

    /*
     * Container Elements
     */
    public static final String NBT_CONTAINER_ELEMENT_TIMED = NBT_CONTAINER_ELEMENT + ".TIMED";

    public static final String NBT_CONTAINER_ELEMENT_TIMED_LEFT = NBT_CONTAINER_ELEMENT_TIMED + ".LEFT";

    public static final String NBT_CONTAINER_ELEMENT_TIMED_RECORD = NBT_CONTAINER_ELEMENT_TIMED + ".RECOREDED";
}