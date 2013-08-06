/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium;

import static ccm.nucleum_omnium.utils.lib.Archive.INVALID_FINGERPRINT_MSG;
import static ccm.nucleum_omnium.utils.lib.Archive.MOD_CHANNEL;
import static ccm.nucleum_omnium.utils.lib.Archive.MOD_FIGERPRINT;
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
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

import ccm.nucleum_network.PacketHandler;
import ccm.nucleum_omnium.proxy.CommonProxy;
import ccm.nucleum_omnium.utils.handler.CommandHandler;
import ccm.nucleum_omnium.utils.handler.GUIHandler;
import ccm.nucleum_omnium.utils.handler.LogHandler;
import ccm.nucleum_omnium.utils.handler.ModLoadingHandler;
import ccm.nucleum_omnium.utils.handler.config.ConfigurationHandler;
import ccm.nucleum_omnium.utils.handler.config.NOConfig;
import ccm.nucleum_omnium.utils.handler.mods.ModHandler;
import ccm.nucleum_omnium.utils.handler.mods.MystcraftHandler;
import ccm.nucleum_omnium.utils.helper.DataHelper;
import ccm.nucleum_omnium.utils.language.OmniumLP;

import lib.org.modstats.ModstatInfo;

@Mod(modid = MOD_ID,
     name = MOD_NAME,
     certificateFingerprint = MOD_FIGERPRINT,
     useMetadata = true)
@NetworkMod(clientSideRequired = true,
            serverSideRequired = false,
            channels = MOD_CHANNEL,
            packetHandler = PacketHandler.class)
@ModstatInfo(prefix = MOD_PREFIX)
public class NucleumOmnium extends BaseMod implements IMod
{

    @Instance(MOD_ID)
    public static NucleumOmnium   instance;

    @SidedProxy(serverSide = SERVER_PROXY,
                clientSide = CLIENT_PROXY)
    public static CommonProxy     proxy;

    /**
     * The current MC Server Instance
     */
    public static MinecraftServer server;

    @EventHandler
    public void invalidFingerprint(final FMLFingerprintViolationEvent event)
    {
        /*
         * Report (log) to the user that the version of Nucleum Omnium they are using has been
         * changed/tampered with
         */
        LogHandler.invalidFP(this, INVALID_FINGERPRINT_MSG);
    }

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event)
    {
        if (!ModLoadingHandler.isModLoaded(this))
        {
            LogHandler.initLog(this);

            config = initializeConfig(event);

            ConfigurationHandler.init(this, NOConfig.class);
        }
    }

    @EventHandler
    public void init(final FMLInitializationEvent event)
    {

        proxy.initCapes();

        proxy.initEventHandling();

        ModHandler.addMod(new MystcraftHandler());

        // Registers the GUI Handler
        NetworkRegistry.instance().registerGuiHandler(NucleumOmnium.instance, GUIHandler.instance());

        OmniumLP.init();
    }

    @EventHandler
    public void PostInit(final FMLPostInitializationEvent event)
    {

        ModHandler.init();

        ModLoadingHandler.loadMod(this);
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