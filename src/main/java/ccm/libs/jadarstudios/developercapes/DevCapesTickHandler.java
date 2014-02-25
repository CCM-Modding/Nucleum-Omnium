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

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.List;

@SideOnly(Side.CLIENT)
class DevCapesTickHandler implements ITickHandler
{

    private static final Minecraft mc       = Minecraft.getMinecraft();
    private static final DevCapes  instance = DevCapesUtil.getInstance();

    // Keep at false when packaging..
    private final boolean debug = false;

    private static final Field downloadImageCapeField = getHackField(2);
    private static final Field locationCapeField      = getHackField(4);

    private int counter = 0;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        try
        {
            // Will not run if there is no world, and if there are no player entities
            // in the playerEntities list.
            if ((mc.theWorld != null) && (mc.theWorld.playerEntities.size() > 0))
            {
                // List of players.
                @SuppressWarnings("unchecked") List<AbstractClientPlayer> players = mc.theWorld.playerEntities;

                // resets the counter if it is too high.
                if (counter >= players.size()) counter = 0;

                AbstractClientPlayer p = players.get(counter);
                if (p != null)
                {

                    String lowerUsername = p.username.toLowerCase();

                    if (instance.getUserGroup(lowerUsername) != null)
                    {
                        // If the player had no cape before, (or is some cases
                        // has a cape from another mod,) then it will be true.
                        // This statement checks for false. Will not replace any
                        // capes.
                        if (!((ThreadDownloadImageData) downloadImageCapeField.get(p)).isTextureUploaded())
                        {
                            String userGroup = instance.getUserGroup(lowerUsername);

                            if (debug) System.out.println("Changing the cape of: " + p.username);
                            // Sets the cape URL.
                            locationCapeField.set(p, instance.getCapeResource(userGroup));
                            downloadImageCapeField.set(p, instance.getDownloadThread(userGroup));
                        }
                    }
                }

                counter++;
            }
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * Not used, stub method.
     */
    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel()
    {
        return "DeveloperCapesTickHandler";
    }

    /**
     * Them cheaty ways...
     */
    private static Field getHackField(int i)
    {
        Field f = AbstractClientPlayer.class.getDeclaredFields()[i];
        f.setAccessible(true);
        return f;
    }
}
