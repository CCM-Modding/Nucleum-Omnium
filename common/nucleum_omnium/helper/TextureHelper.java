package nucleum_omnium.helper;

import net.minecraft.item.Item;

public class TextureHelper
{

    /**
     * @param name
     *            Name of the thing to make a texture
     * @return The texture version of the name
     */
    public static String getTextureFromName(final String name, String texturePath)
    {
        return texturePath + name;
    }

    /**
     * @return The texture.
     */
    public static String getTexture(final Item item, String texturePath)
    {
        return texturePath + ItemHelper.getItemName(item);
    }

    private TextureHelper()
    {}
}