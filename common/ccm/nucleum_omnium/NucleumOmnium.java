/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium;

import static ccm.nucleum_omnium.utils.lib.Archive.MOD_CHANNEL;
import static ccm.nucleum_omnium.utils.lib.Archive.MOD_ID;
import static ccm.nucleum_omnium.utils.lib.Archive.MOD_NAME;
import static ccm.nucleum_omnium.utils.lib.Archive.MOD_PREFIX;
import static ccm.nucleum_omnium.utils.lib.Locations.CLIENT_PROXY;
import static ccm.nucleum_omnium.utils.lib.Locations.SERVER_PROXY;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

import ccm.nucleum_network.PacketHandler;
import ccm.nucleum_omnium.proxy.CommonProxy;
import ccm.nucleum_omnium.utils.handler.CommandHandler;
import ccm.nucleum_omnium.utils.handler.ModLoadingHandler;
import ccm.nucleum_omnium.utils.handler.config.ConfigurationHandler;
import ccm.nucleum_omnium.utils.handler.config.NOConfig;
import ccm.nucleum_omnium.utils.handler.gui.GuiHandler;
import ccm.nucleum_omnium.utils.handler.mods.ModHandler;
import ccm.nucleum_omnium.utils.handler.mods.MystcraftHandler;
import ccm.nucleum_omnium.utils.helper.DataHelper;

import lib.org.modstats.ModstatInfo;

@Mod(modid = MOD_ID, name = MOD_NAME, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = MOD_CHANNEL, packetHandler = PacketHandler.class)
@ModstatInfo(prefix = MOD_PREFIX)
public class NucleumOmnium extends BaseMod implements IMod
{

    @Instance(MOD_ID)
    public static NucleumOmnium instance;

    @SidedProxy(serverSide = SERVER_PROXY, clientSide = CLIENT_PROXY)
    public static CommonProxy proxy;

    /**
     * The current MC Server Instance
     */
    public static MinecraftServer server;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event)
    {
        ModLoadingHandler.loadMod(this);

        initializeConfig(event);
        ConfigurationHandler.init(this, NOConfig.class);
    }

    @EventHandler
    public void init(final FMLInitializationEvent event)
    {
        proxy.initCapes();
        proxy.initEventHandling();

        ModHandler.addMod(new MystcraftHandler());

        // Registers the GUI Handler
        NetworkRegistry.instance().registerGuiHandler(NucleumOmnium.instance, GuiHandler.instance());
    }

    @EventHandler
    public void PostInit(final FMLPostInitializationEvent event)
    {
        ModHandler.init();
    }

    @EventHandler
    public void serverStarting(final FMLServerStartingEvent event)
    {
        // Initialize the custom commands
        CommandHandler.initCommands(event);

        server = event.getServer();

        DataHelper.init();
    }
}