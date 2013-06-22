package ccm.nucleum_omnium.utils.lib;

import ccm.nucleum_omnium.BaseNIClass;

public final class TileConstant extends BaseNIClass {
    
    /*
     * Base Stuff
     */
    public static final String CONTAINER          = "container.";
    
    public static final String INVENTORY          = CONTAINER + "inventory";
    
    /*
     * NBT TE Stuff
     */
    public static final String NBT_TE_Direction   = Archive.MOD_CHANNEL + "direction";
    
    public static final String NBT_TE_State       = Archive.MOD_CHANNEL + "state";
    
    public static final String NBT_TE_PAST_State  = Archive.MOD_CHANNEL + "past_state";
    
    public static final String NBT_TE_Custom_Name = Archive.MOD_CHANNEL + "name";
    
    public static final String NBT_TE_Owner       = Archive.MOD_CHANNEL + "owner";
}
