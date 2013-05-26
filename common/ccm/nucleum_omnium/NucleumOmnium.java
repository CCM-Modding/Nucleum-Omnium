package ccm.nucleum_omnium;

import java.util.logging.Level;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import ccm.nucleum_omnium.handler.CommandHandler;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.handler.ModHandler;
import ccm.nucleum_omnium.handler.mods.ModsMystcraft;
import ccm.nucleum_omnium.proxy.CommonProxy;
import ccm.nucleum_omnium.stats.StatEventHandler;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Locations;
import ccm.nucleum_omnium.worldgen.WorldGenHandler;
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
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Archive.MOD_ID,
     name = Archive.MOD_NAME,
     version = Archive.MOD_VERSION,
     useMetadata = false,
     dependencies = Archive.MOD_DEPENDANCIES,
     certificateFingerprint = Archive.MOD_FIGERPRINT)
@NetworkMod(clientSideRequired = true,
            serverSideRequired = false,
            channels = Archive.MOD_CHANNEL)
public class NucleumOmnium implements IMod
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
    
    public static boolean mystLoaded = false;

    @Override
    public String getId()
    {
        return Archive.MOD_ID;
    }

    @Override
    public String getName()
    {
        return Archive.MOD_NAME;
    }

    @Override
    public String getPrefix()
    {
        return Archive.MOD_PREFIX;
    }

    @Override
    public String getVersion()
    {
        return Archive.MOD_VERSION;
    }

    @FingerprintWarning
    public void invalidFingerprint(final FMLFingerprintViolationEvent event)
    {
        /*
         * Report (log) to the user that the version of Harvestry they are using
         * has been changed/tampered with
         */
        Handler.log(this, Level.SEVERE, Archive.INVALID_FINGERPRINT_MSG);
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent event)
    {

        // Initialize the custom commands
        CommandHandler.initCommands(event);
        
        server = event.getServer();
    }

    @PreInit
    public void preInit(final FMLPreInitializationEvent evt)
    {
        Handler.initLog(this);

        GameRegistry.registerWorldGenerator(WorldGenHandler.instance);

        MinecraftForge.ORE_GEN_BUS.register(WorldGenHandler.instance);
    }

    @Init
    public void init(final FMLInitializationEvent event)
    {
        proxy.initCapes();

        proxy.initEventHandling();

        proxy.initModelHandlers();

        StatEventHandler.addModToList(this);

        ModHandler.addModToHandle(new ModsMystcraft(), "Mystcraft");
    }

    @PostInit
    public void PostInit(final FMLPostInitializationEvent event)
    {
        ModHandler.init();
    }
}