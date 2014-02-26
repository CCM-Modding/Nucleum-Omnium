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

import ccm.libs.jadarstudios.developercapes.DevCapesUtil;
import ccm.libs.org.mcstats.Metrics;
import ccm.nucleumOmnium.commands.CommandBan;
import ccm.nucleumOmnium.commands.CommandKill;
import ccm.nucleumOmnium.dungeonMaster.DungeonMaster;
import ccm.nucleumOmnium.dungeonMaster.DungeonMasterCMD;
import ccm.nucleumOmnium.misc.FuelAdding;
import ccm.nucleumOmnium.network.ConnectionHandler;
import ccm.nucleumOmnium.network.PacketHandler;
import ccm.nucleumOmnium.recipeStuff.OreDictionaryFixes;
import ccm.nucleumOmnium.util.EventHandler;
import ccm.nucleumOmnium.util.Players;
import ccm.nucleumOmnium.worldfiller2000.CommandWorldFiller;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static ccm.nucleumOmnium.util.NOConstants.*;

/**
 * The core for all things CCM
 *
 * @version 2.xx
 *
 * @author Dries007
 * @author ClayCorp
 * @author CaptainShadows
 */
@Mod(modid = NO_MODID, useMetadata = true)
@NetworkMod(clientSideRequired = false, serverSideRequired = false, channels = {CHANNEL_HS}, packetHandler = PacketHandler.class, connectionHandler = ConnectionHandler.class)
public class NucleumOmnium
{
    public boolean serverHasMod = false;

    @Mod.Instance(NO_MODID)
    public static NucleumOmnium instance;

    @Mod.Metadata(NO_MODID)
    private ModMetadata metadata;

    private NOConfig config;
    private Logger   logger;
    private File     ccmFolder;

    public static Logger getLogger()
    {
        return instance.logger;
    }

    public static NOConfig getConfig()
    {
        return instance.config;
    }

    private static String getVersion()
    {
        return instance.metadata.version;
    }

    public static File getCCMFolder()
    {
        return instance.ccmFolder;
    }

    public boolean neededOnClient()
    {
        return false;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ccmFolder = new File(event.getModConfigurationDirectory(), "CCM");
        if (!ccmFolder.exists()) //noinspection ResultOfMethodCallIgnored
            ccmFolder.mkdirs();

        logger = event.getModLog();
        config = new NOConfig(getCCMFolder());

        Players.init();
        FuelAdding.init();

        MinecraftForge.EVENT_BUS.register(EventHandler.EVENT_HANDLER);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        DevCapesUtil.addFileUrl(URL_CAPES);

        if (config.dungeonMaster) DungeonMaster.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (config.oreDictionaryFixes) OreDictionaryFixes.init();

        try
        {
            Metrics metrics = new Metrics(NO_MODID, getVersion());
            metrics.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        if (config.override_ban) event.registerServerCommand(new CommandBan());
        if (config.override_kill) event.registerServerCommand(new CommandKill());
        if (config.worldFiller) event.registerServerCommand(new CommandWorldFiller());
        if (config.dungeonMaster) event.registerServerCommand(new DungeonMasterCMD());
        if (config.dungeonMaster) DungeonMaster.loadFromFile();
    }
}