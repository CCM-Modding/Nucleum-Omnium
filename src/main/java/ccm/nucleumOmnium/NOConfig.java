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

package ccm.nucleumOmnium;

import net.minecraftforge.common.Configuration;

import java.io.File;

public class NOConfig
{
    private static final String CMD    = "cmd";
    private static final String CLIENT = "client";
    private static final String TWEAKS = "tweaks";

    private final Configuration config;

    public boolean override_ban  = true;
    public boolean override_kill = true;

    public boolean  worldFiller                 = true;
    public boolean  dungeonMaster               = true;
    public String[] fuelEdits                   = {"106:100"};
    public boolean  oreDictionaryFixes          = true;
    public String[] oreDictionaryFixesWhiteList = {"ingot."};

    public boolean noRainNoise = false;

    public NOConfig(File ccmFolder)
    {
        this.config = new Configuration(new File(ccmFolder, "CCM.cfg"));

        /**
         * CMD
         */
        config.addCustomCategoryComment(CMD, "Command related settings");

        override_ban = config.get(CMD, "override_ban", override_ban, "Override the ban command with our own enhanced version. Allows temp bans.").getBoolean(override_ban);
        override_kill = config.get(CMD, "override_kill", override_kill, "Override the kill command with our own enhanced version. Allows killing others.").getBoolean(override_kill);

        /**
         * TWEAKS
         */
        config.addCustomCategoryComment(TWEAKS, "Tweaks");

        worldFiller = config.get(TWEAKS, "worldFiller", worldFiller, "Enable the world filler.").getBoolean(worldFiller);
        dungeonMaster = config.get(TWEAKS, "dungeonMaster", dungeonMaster, "Enable the dungeon master, see separate config.").getBoolean(dungeonMaster);
        fuelEdits = config.get(TWEAKS, "fuelEdits", fuelEdits, "Allows you to add fuels to the fuel registry. Fueltime is in ticks. Use itemID:metadate:fueltime or itemID:fueltime or oredictEntry:fueltime").getStringList();
        oreDictionaryFixes = config.get(TWEAKS, "oreDictionaryFixes", oreDictionaryFixes, "Unifies all recipe outputs, see this config file for the whitelist.").getBoolean(oreDictionaryFixes);
        oreDictionaryFixesWhiteList = config.get(TWEAKS, "oreDictionaryFixesWhiteList", oreDictionaryFixesWhiteList, "Whitelist for oreDictionaryFixes, use . for a partial match. (aka ingot. will macht all ingots).").getStringList();

        /**
         * CLIENT
         */
        config.addCustomCategoryComment(CLIENT, "Client only settings");

        noRainNoise = config.get(CLIENT, "noRainNoise", noRainNoise, "Stops the rain sounds from playing.").getBoolean(noRainNoise);

        this.config.save();
    }
}
