package ccm.nucleum_omnium.utils.lib;

import ccm.nucleum_omnium.base.BaseNIC;

public final class TileConstant extends BaseNIC {

	/*
	 * Base Stuff
	 */
	public static final String	CONTAINER				= "container.";

	public static final String	INVENTORY				= CONTAINER + "inventory";

	public static final String	TE						= Archive.MOD_CHANNEL + ".TILE_ENTITY.";

	/*
	 * NBT TE Stuff
	 */
	public static final String	NBT_TE_DIRECTION		= TE + "DIRECTION";

	public static final String	NBT_TE_CUSTOM_NAME		= TE + "NAME";

	public static final String	NBT_TE_OWNER			= TE + "OWNER";

	public static final String	NBT_TE_INVENTORY_SIZE	= TE + "INVENTORY_SIZE";

	public static final String	NBT_TE_SRC_LOGIC		= TE + "SRC_LOGIC";

	public static final String	NBT_TE_PROGRESS			= TE + "PROGRESS";

	public static final String	NBT_TE_STATE			= TE + "STATE";

	public static final String	NBT_TE_PAST_STATE		= TE + "PAST_STATE";
}
