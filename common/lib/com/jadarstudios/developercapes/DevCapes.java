/**
 * DeveloperCapes by Jadar
 * License: MIT License (https://raw.github.com/jadar/DeveloperCapes/master/LICENSE)
 * version 2.1
 */
package lib.com.jadarstudios.developercapes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * This library adds capes for people you specify. Use DevCapesUtil to add your capes if you do not call the addFileUrl method in a client
 * proxy.
 * 
 * @author Jadar
 */
public class DevCapes
{
    private static DevCapes instance;
    public static final double version = 2.2;
    public DevCapesVersionChecker versionChecker;
    private final HashMap<String, String> users;
    private final HashMap<String, ResourceLocation> capeResources;
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
        versionChecker = new DevCapesVersionChecker();
        Thread vc = new Thread(versionChecker);
        vc.setDaemon(true);
        vc.setName("DevCapesVersionChecker");
        vc.run();
    }

    /**
     * Get's the current DeveloperCapesAPI instance, or creates a new one if necessary.
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
     * <b>USE <i>"DevCapesUtil.addFileUrl(String);"</i> INSTEAD!</b>
     * <p>
     * Set up capes. All cape URLs are in the txt file passed in.<br>
     * <a href="https://github.com/jadar/DeveloperCapesAPI/blob/master/SampleCape.txt">Sample Cape Config</a>
     * 
     * @param txtUrl
     *            The URL of the .txt file containing the groups, members of said groups, and the group's cape URL.
     */
    public void addFileUrl(String txtUrl)
    {
        try
        {
            URL url = new URL(txtUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String username = "";
            String group = "";
            String capeUrl = "";
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
                                addCapeResource(group, r);
                                addDownloadThread(group, t);
                                continue;
                            }
                            username = subLine.toLowerCase();
                            addUser(username, group);
                        }
                    }
                }
            }
        } catch (IOException e)
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
     * @param user
     *            The Username to add
     * @param group
     *            The group to add that Username to
     */
    public void addUser(String user, String group)
    {
        if (getUserGroup(user) == null)
        {
            users.put(user, group);
        }
    }

    /**
     * @param user
     *            The Username to get from the users HashMap
     * @return The group found in the users HashMap
     */
    public String getUserGroup(String user)
    {
        return users.get(user.toLowerCase());
    }

    /**
     * Adds a cape ResourceLocation that is predownloaded.
     * 
     * @param group
     *            The group that the cape belongs to
     * @param res
     *            The {@link ResourceLocation} of the cape
     */
    public void addCapeResource(String group, ResourceLocation res)
    {
        if (getCapeResource(group) == null)
        {
            capeResources.put(group, res);
        }
    }

    /**
     * @param group
     *            The cape's group
     * @return The cape's ResourceLocation
     */
    public ResourceLocation getCapeResource(String group)
    {
        return capeResources.get(group);
    }

    /**
     * Adds an ThreadDownloadImageData. Needed to change cape.
     * 
     * @param group
     *            The group that the cape belongs to
     * @param res
     *            The {@link ThreadDownloadImageData} to download the cape
     */
    public void addDownloadThread(String group, ThreadDownloadImageData res)
    {
        if (getDownloadThread(group) == null)
        {
            downloadThreads.put(group, res);
        }
    }

    /**
     * @param group
     *            The group that the cape belongs to
     * @return The {@link ThreadDownloadImageData} to download the cape
     */
    public ThreadDownloadImageData getDownloadThread(String group)
    {
        return downloadThreads.get(group);
    }

    /**
     * Used to download images. Copied from AbstractClientPlayer to remove a conditional.
     * 
     * @param storage
     * @param url
     * @param location
     * @param buffer
     * @return The {@link ThreadDownloadImageData} to download the cape
     */
    public static ThreadDownloadImageData makeDownloadThread(ResourceLocation storage, String url, ResourceLocation location, IImageBuffer buffer)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        TextureObject object = new ThreadDownloadImageData(url, location, buffer);
        // Binds ResourceLocation to this.
        texturemanager.loadTexture(storage, object);
        return (ThreadDownloadImageData) object;
    }
}