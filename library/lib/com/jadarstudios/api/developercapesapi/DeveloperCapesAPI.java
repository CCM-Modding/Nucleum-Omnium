/**
 * Copyright (c) Jadar, 2013
 * Developer Capes API by Jadar
 * License: Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * version 1.3.1
 */
package lib.com.jadarstudios.api.developercapesapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class DeveloperCapesAPI {

    private static DeveloperCapesAPI      instance;

    private static final Minecraft        mc        = Minecraft.getMinecraft();

    private final HashMap<String, String> users;

    private final HashMap<String, String> groupUrls;

    private boolean                       tickSetUp = false;

    /**
     * Object constructor.
     */
    private DeveloperCapesAPI() {
        this.users = new HashMap<String, String>();
        this.groupUrls = new HashMap<String, String>();
    }

    /**
     * Get's the current DeveloperCapesAPI instance, or creates a new one if
     * necessary.
     */
    public static DeveloperCapesAPI getInstance() {
        if (DeveloperCapesAPI.instance == null)
            DeveloperCapesAPI.instance = new DeveloperCapesAPI();
        return DeveloperCapesAPI.instance;
    }

    /**
     * Set up capes. All cape URLs are in the txt file passed in.
     * https://github.com/jadar/DeveloperCapesAPI/blob/master/SampleCape.txt
     * 
     * @param parTxtUrl
     *            The URL of the .txt file containing the groups, members of
     *            said groups, and the group's cape URL.
     */
    public void init(final String parTxtUrl) {
        try {
            final URL url = new URL(parTxtUrl);
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;

            String username = "";
            String group = "";
            String capeUrl = "";

            while ((line = reader.readLine()) != null)
                // excludes commented lines
                if (!line.startsWith("#"))
                    // loops through characters.
                    for (int i = 0; i < line.length(); i++)
                        // when char : is found do stuff.
                        if (line.charAt(i) == '=') {
                            group = line.substring(0, i);
                            final String subLine = line.substring(i + 1);

                            if (subLine.startsWith("http")) {
                                capeUrl = subLine;
                                DeveloperCapesAPI.getInstance().addGroupUrl(group, capeUrl);
                                DeveloperCapesAPI.mc.renderEngine.obtainImageData(capeUrl,
                                        new DeveloperCapesImageBufferDownload());
                                continue;
                            } else {
                                username = subLine.toLowerCase();
                                DeveloperCapesAPI.getInstance().addUser(username, group);
                            }
                        }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // Makes sure to set up only one tick handler.
        if (!DeveloperCapesAPI.instance.tickSetUp) {
            // Sets up the tick handler for capes.
            TickRegistry.registerTickHandler(new DeveloperCapesTickHandler(), Side.CLIENT);
            DeveloperCapesAPI.instance.tickSetUp = true;
        }
    }

    /**
     * Used to add user to users HashMap.
     * 
     * @param parUsername
     *            The Username to add.
     * @param parGroup
     *            The group to add that Username to.
     */
    public void addUser(final String parUsername, final String parGroup) {
        if (this.getUserGroup(parUsername) == null)
            this.users.put(parUsername, parGroup);
    }

    /**
     * Used to get user from users HashMap.
     * 
     * @param parUsername
     *            The Username to get from the users HashMap.
     * @return The Username found in the users HashMap.
     */
    public String getUserGroup(final String parUsername) {
        return this.users.get(parUsername.toLowerCase());
    }

    /**
     * Used to add group and URL to groupUrls HashMap.
     * 
     * @param parGroup
     *            The group to add.
     * @param parCapeUrl
     *            The corresponding URL to add.
     */
    public void addGroupUrl(final String parGroup, final String parCapeUrl) {
        if (this.getGroupUrl(parGroup) == null)
            this.groupUrls.put(parGroup, parCapeUrl);
    }

    /**
     * Used to get URL from groupUrl by the group name.
     * 
     * @param group
     *            The name of the group to get the URL from.
     * @return The group URL.
     */
    public String getGroupUrl(final String group) {
        return this.groupUrls.get(group);
    }
}