package ccm.nucleum.helper;

import net.minecraft.item.Item;

public class TextureHelper extends BaseHelper {

    /**
     * @param name
     *            Name of the thing to make a texture
     * @return The texture version of the name
     */
    public static String getTextureFromName(final String name, final String texturePath) {
        return texturePath + name;
    }

    /**
     * @return The texture.
     */
    public static String getTexture(final Item item, final String texturePath) {
        return texturePath + ItemHelper.getItemName(item);
    }
}