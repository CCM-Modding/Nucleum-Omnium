package ccm.nucleum_omnium;

import java.util.logging.Level;

import lib.org.modstats.ModstatInfo;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

import ccm.nucleum_omnium.configuration.Config;
import ccm.nucleum_omnium.handler.CommandHandler;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.handler.ModHandler;
import ccm.nucleum_omnium.handler.mods.ModsMystcraft;
import ccm.nucleum_omnium.proxy.CommonProxy;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Languages;
import ccm.nucleum_omnium.utils.lib.Locations;

@Mod(modid = Archive.MOD_ID,
     name = Archive.MOD_NAME,
     version = Archive.MOD_VERSION,
     dependencies = Archive.MOD_DEPENDANCIES,
     certificateFingerprint = Archive.MOD_FIGERPRINT)
@NetworkMod(clientSideRequired = true,
            serverSideRequired = false,
            channels = Archive.MOD_CHANNEL)
@ModstatInfo(prefix = Archive.MOD_PREFIX)
public class NucleumOmnium extends BaseMod implements IMod
{

    /**
     * The Instance
     */
    @Instance(Archive.MOD_ID)
    public static NucleumOmnium   instance;

    /**
     * The proxy
     */
    @SidedProxy(serverSide = Locations.SERVER_PROXY,
                clientSide = Locations.CLIENT_PROXY)
    public static CommonProxy     proxy;

    public static MinecraftServer server;

    public static boolean         mystLoaded = false;

    protected Mod                 mod        = this.getClass().getAnnotation(Mod.class);

    @FingerprintWarning
    public void invalidFingerprint(final FMLFingerprintViolationEvent event)
    {
        /*
         * Report (log) to the user that the version of Harvestry they are using
         * has been changed/tampered with
         */
        Handler.log(this, Level.SEVERE, Archive.INVALID_FINGERPRINT_MSG);
    }

    @PreInit
    public void preInit(final FMLPreInitializationEvent evt)
    {
        if (!Handler.isModLoaded(this)){

            Handler.initLog(this);

            Config.init(this.initializeConfig(evt));
        }
    }

    @Init
    public void init(final FMLInitializationEvent event)
    {
        proxy.initCapes();

        proxy.initEventHandling();

        proxy.initModelHandlers();

        ModHandler.addModToHandle(new ModsMystcraft(), "Mystcraft");

        this.loadLangs(Languages.LANGUAGE_FILES);
    }

    @PostInit
    public void PostInit(final FMLPostInitializationEvent event)
    {
        ModHandler.init();

        Handler.loadMod(this);
    }

    @ServerStarting
    public void serverStarting(final FMLServerStartingEvent event)
    {

        // Initialize the custom commands
        CommandHandler.initCommands(event);

        server = event.getServer();
    }
}