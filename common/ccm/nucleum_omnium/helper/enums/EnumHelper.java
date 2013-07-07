package ccm.nucleum_omnium.helper.enums;

import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.utils.lib.TileConstant;

/**
 * EnumHelper
 * 
 * @author Captain_Shadows
 */
public class EnumHelper extends BaseNIC {

	/**
	 * @param enu
	 *            The enum constant
	 * @return a modified version of the constants name()
	 */
	public static String getTileID(final Enum<?> enu) {
		return TileConstant.CONTAINER + enu.name() + ".name";
	}
}
