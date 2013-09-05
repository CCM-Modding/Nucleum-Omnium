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

    public static final String TE = "CCM.ENTITY.TILE.";
    
    public static final String TE_PLANT = TE + "PLANT.";

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
    
    public static final String NBT_PLANT_STAGE = TE_PLANT + "STAGE";
    
    public static final String NBT_PLANT_STAGES = TE_PLANT + "STAGES";
    
    public static final String NBT_PLANT_RATE = TE_PLANT + "RATE";
}