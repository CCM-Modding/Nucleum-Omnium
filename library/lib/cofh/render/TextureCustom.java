package lib.cofh.render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureStitched;
import net.minecraft.client.texturepacks.ITexturePack;

/**
 * Allows for the creation of non-square textures.
 * 
 * @author King Lemming
 */
public class TextureCustom extends TextureStitched
{

    protected TextureCustom(final String name)
    {

        super(name);
    }

    @Override
    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    public boolean loadTexture(final TextureManager manager,
                               final ITexturePack texturepack,
                               final String name,
                               final String fileName,
                               final BufferedImage image,
                               final ArrayList textures)
    {

        final Texture texture = manager.makeTexture(name, 2, image.getWidth(), image.getHeight(), 10496, 6408, 9728, 9728, false, image);
        textures.add(texture);
        return true;
    }

}
