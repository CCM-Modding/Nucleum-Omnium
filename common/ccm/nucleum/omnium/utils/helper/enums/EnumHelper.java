/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper.enums;

import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.base.BaseNIC;
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
     * @param enu
     *            The enum constant
     * @return a modified version of the constants name()
     */
    public static String getTileID(final Enum<?> enu)
    {
        return NBTConstants.CONTAINER + enu.name() + ".name";
    }

    /**
     * @param machinegrill
     * @return
     */
    public static TileEntity getTile(final Enum<? extends IBlockEnum> enu)
    {
        return TileHandler.getTile(enu.name());
    }

    /**
     * @param machinegrinder
     * @param string
     * @return
     */
    public static String getTexture(final Enum<? extends IBlockEnum> enu, final String location)
    {
        return TextureHelper.getTexture(enu.name(), location);
    }

}