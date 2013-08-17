/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper.enums;

import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

/**
 * EnumHelper
 * 
 * @author Captain_Shadows
 */
public class EnumHelper extends BaseNIC
{

    /**
     * @param enu
     *            The enum constant
     * @return a modified version of the constants name()
     */
    public static String getTileID(final Enum<?> enu)
    {
        return NBTConstants.CONTAINER + enu.name() + ".name";
    }
}
