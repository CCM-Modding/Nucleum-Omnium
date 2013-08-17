/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.lib;

import ccm.nucleum.omnium.base.BaseNIC;

public final class NBTConstants extends BaseNIC
{
    /*
     * Base Stuff
     */
    public static final String CONTAINER = "container.";

    public static final String INVENTORY = CONTAINER + "inventory";

    public static final String TE = "CCM.TILE_ENTITY.";

    public static final String TIMER = "CCM.TIMER.";

    /*
     * NBT TE Stuff
     */
    public static final String NBT_TE_DIRECTION = TE + "DIRECTION";

    public static final String NBT_TE_CUSTOM_NAME = TE + "NAME";

    public static final String NBT_TE_OWNER = TE + "OWNER";

    public static final String NBT_TE_INVENTORY_SIZE = TE + "INVENTORY_SIZE";

    public static final String NBT_TE_SRC_LOGIC = TE + "SRC_LOGIC";

    public static final String NBT_TE_PROGRESS = TE + "PROGRESS";

    public static final String NBT_TE_STATE = TE + "STATE";

    /*
     * NBT Timer Stuff
     */
    public static final String NBT_TIMER_START = TIMER + "START";

    public static final String NBT_TIMER_END = TIMER + "END";
}