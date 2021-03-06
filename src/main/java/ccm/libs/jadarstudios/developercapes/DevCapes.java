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

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

/**
 * This library adds capes for people you specify.
 * Use DevCapesUtil to add your capes if you do not call the addFileUrl method
 * in a client proxy.
 *
 * @author Jadar
 */
public class DevCapes
{

    private static DevCapes instance;
    public static final double version = 2.2;

    private final HashMap<String, String>                  users;
    private final HashMap<String, ResourceLocation>        capeResources;
    private final HashMap<String, ThreadDownloadImageData> downloadThreads;

    private DevCapesTickHandler tickHandler = null;

    /**
     * Object constructor.
     */
    private DevCapes()
    {
        users = new HashMap<String, String>();
        capeResources = new HashMap<String, ResourceLocation>();
        downloadThreads = new HashMap<String, ThreadDownloadImageData>();
    }

    /**
     * Get's the current DeveloperCapesAPI instance, or creates a new one if
     * necessary.
     */
    public static DevCapes getInstance()
    {
        if (instance == null)
        {
            instance = new DevCapes();
        }
        return instance;
    }

    /**
     * <b>DO NOT CALL THIS UNLESS YOU KNOW IT IS BEING CALLED FROM A CLIENT ONLY CLASS/METHOD!</b><br>
     * <b>USE <i>"DevCapesUtil.addFileUrl(String);"</i> INSTEAD!</b><p>
     * Set up capes. All cape URLs are in the txt file passed in.<br>
     * <a href="https://github.com/jadar/DeveloperCapesAPI/blob/master/SampleCape.txt">Sample Cape Config</a>
     *
     * @param parTxtUrl The URL of the .txt file containing the groups, members of
     *                  said groups, and the group's cape URL.
     */
    public void addFileUrl(String parTxtUrl)
    {
        try
        {
            URL url = new URL(parTxtUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            String username;
            String group;
            String capeUrl;

            while ((line = reader.readLine()) != null)
            {

                // excludes commented lines
                if (!line.startsWith("#"))
                {
                    // loops through characters.
                    for (int i = 0; i < line.length(); i++)
                    {
                        // when char : is found do stuff.
                        if (line.charAt(i) == '=')
                        {
                            group = line.substring(0, i);
                            String subLine = line.substring(i + 1);

                            if (subLine.startsWith("http"))
                            {
                                capeUrl = subLine;

                                ResourceLocation r = new ResourceLocation("DevCapes/" + group);
                                ThreadDownloadImageData t = makeDownloadThread(r, capeUrl, null, new DevCapesImageBufferDownload());

                                this.addCapeResource(group, r);
                                this.addDownloadThread(group, t);
                            }
                            else
                            {
                                username = subLine.toLowerCase();
                                addUser(username, group);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Makes sure to set up only one tick handler.
        if (tickHandler == null)
        {
            // Creates the tick handler for capes.
            tickHandler = new DevCapesTickHandler();
            // Sets up the tick handler for capes.
            TickRegistry.registerTickHandler(tickHandler, Side.CLIENT);
        }

    }

    /**
     * Used to add user to users HashMap.
     *
     * @param parUsername The Username to add.
     * @param parGroup    The group to add that Username to.
     */
    void addUser(String parUsername, String parGroup)
    {
        if (getUserGroup(parUsername) == null)
        {
            users.put(parUsername, parGroup);
        }
    }

    /**
     * Used to get user from users HashMap.
     *
     * @param parUsername The Username to get from the users HashMap.
     * @return The Username found in the users HashMap.
     */
    public String getUserGroup(String parUsername)
    {
        return users.get(parUsername.toLowerCase());
    }

    /**
     * Adds a cape ResourceLocation that is predownloaded.
     *
     * @param parGroup
     * @param parResource
     */
    void addCapeResource(String parGroup, ResourceLocation parResource)
    {
        if (getCapeResource(parGroup) == null)
        {
            capeResources.put(parGroup, parResource);
        }
    }

    /**
     * Gets a cape ResourceLocation.
     *
     * @param parGroup
     * @return
     */
    public ResourceLocation getCapeResource(String parGroup)
    {
        return capeResources.get(parGroup);
    }

    /**
     * Adds an ThreadDownloadImageData. Needed to change cape.
     *
     * @param parGroup
     * @param parResource
     */
    void addDownloadThread(String parGroup, ThreadDownloadImageData parResource)
    {
        if (getDownloadThread(parGroup) == null)
        {
            downloadThreads.put(parGroup, parResource);
        }
    }

    /**
     * Gets the ThreadDownloadImageData that is associated with the group.
     *
     * @param parGroup
     * @return
     */
    public ThreadDownloadImageData getDownloadThread(String parGroup)
    {
        return downloadThreads.get(parGroup);
    }

    /**
     * Used to download images. Copied from AbstractClientPlayer to remove
     * a conditional.
     *
     * @param par0ResourceLocation
     * @param par1Str
     * @param par2ResourceLocation
     * @param par3IImageBuffer
     * @return
     */
    private static ThreadDownloadImageData makeDownloadThread(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();

        TextureObject object = new ThreadDownloadImageData(par1Str, par2ResourceLocation, par3IImageBuffer);
        // Binds ResourceLocation to this.
        texturemanager.loadTexture(par0ResourceLocation, object);

        return (ThreadDownloadImageData) object;
    }
}