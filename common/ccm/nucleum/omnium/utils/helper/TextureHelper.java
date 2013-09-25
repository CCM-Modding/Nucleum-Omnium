/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.item.Item;

import ccm.nucleum.omnium.BaseNIC;
import ccm.nucleum.omnium.utils.helper.item.ItemHelper;

/**
 * TextureHelper
 * <p>
 * 
 * @author Captain_Shadows
 */
public class TextureHelper extends BaseNIC
{
    /**
     * @return The texture
     */
    public static String getTexture(final Item item, final String texturePath)
    {
        return texturePath + ItemHelper.getItemName(item);
    }

    /**
     * @param name
     *            Name of the thing to make a texture
     * @return The texture version of the name
     */
    public static String getTexture(final String name, final String texturePath)
    {
        return texturePath + name;
    }
}