/**
 * DeveloperCapes by Jadar
 * License: MIT License (https://raw.github.com/jadar/DeveloperCapes/master/LICENSE)
 * version 2.1
 */
package lib.com.jadarstudios.developercapes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.minecraft.client.renderer.IImageBuffer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DevCapesImageBufferDownload implements IImageBuffer
{
    @Override
    public BufferedImage parseUserSkin(BufferedImage bufferedImage)
    {
        if (bufferedImage == null)
        {
            return null;
        }
        int imageWidth = (bufferedImage.getWidth(null) <= 64) ? 64 : (bufferedImage.getWidth(null));
        int imageHeight = (bufferedImage.getHeight(null) <= 32) ? 32 : (bufferedImage.getHeight(null));
        BufferedImage capeImage = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics graphics = capeImage.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return capeImage;
    }
}