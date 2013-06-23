/**
 * 
 */
package ccm.nucleum_omnium.helper.enums;

import ccm.nucleum_omnium.BaseNIClass;
import ccm.nucleum_omnium.utils.lib.TileConstant;

/**
 * EnumHelper
 * 
 * @author Captain_Shadows
 */
public class EnumHelper extends BaseNIClass {
    
    public static String getTileID(Enum<?> enu) {
        return TileConstant.CONTAINER + enu.name();
    }
}
