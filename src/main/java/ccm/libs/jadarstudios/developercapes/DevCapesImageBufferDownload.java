/*
 * Copyright (c) 2014 CCM modding crew.
 * View members of the CCM modding crew on https://github.com/orgs/CCM-Modding/members
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * DeveloperCapes by Jadar
 * License: MIT License (https://raw.github.com/jadar/DeveloperCapes/master/LICENSE)
 * version 2.1
 */
package ccm.libs.jadarstudios.developercapes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.IImageBuffer;

import java.awt.*;
import java.awt.image.BufferedImage;

@SideOnly(Side.CLIENT)
class DevCapesImageBufferDownload implements IImageBuffer
{
    @Override
    public BufferedImage parseUserSkin(BufferedImage par1BufferedImage)
    {
        if (par1BufferedImage == null)
        {
            return null;
        }
        else
        {
            int imageWidth = (par1BufferedImage.getWidth(null) <= 64) ? 64 : (par1BufferedImage.getWidth(null));
            int imageHeight = (par1BufferedImage.getHeight(null) <= 32) ? 32 : (par1BufferedImage.getHeight(null));

            BufferedImage capeImage = new BufferedImage(imageWidth, imageHeight, 2);

            Graphics graphics = capeImage.getGraphics();
            graphics.drawImage(par1BufferedImage, 0, 0, null);
            graphics.dispose();

            return capeImage;
        }
    }
}
