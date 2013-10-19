/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper.enums;

import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.BaseNIC;
import ccm.nucleum.omnium.utils.handler.TileHandler;
import ccm.nucleum.omnium.utils.helper.TextureHelper;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

/**
 * EnumHelper
 * 
 * @author Captain_Shadows
 */
public class EnumHelper extends BaseNIC
{

    /**
     * @return a modified version of the constants name()
     */
    public static String getTileID(final Enum<?> enu)
    {
        return NBTConstants.CONTAINER + enu.name() + ".name";
    }

    /**
     * @return
     */
    public static TileEntity getTile(final Enum<? extends IBlockEnum> enu)
    {
        return TileHandler.getTileInstance(enu.name());
    }

    /**
     * @return
     */
    public static String getTexture(final Enum<? extends IBlockEnum> enu, final String location)
    {
        return TextureHelper.getTexture(enu.name(), location);
    }

}