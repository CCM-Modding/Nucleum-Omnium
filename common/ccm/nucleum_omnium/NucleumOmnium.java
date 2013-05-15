package ccm.nucleum_omnium;

import java.util.logging.Level;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;
import ccm.nucleum_omnium.client.model.tcn.TechneModelLoader;
import ccm.nucleum_omnium.handler.Handler;
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
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
     * The Harvestry Instance
     */
    @Instance(Archive.MOD_ID)
    public static NucleumOmnium instance;

    /**
     * The Harvestry proxy
     */
    @SidedProxy(serverSide = Locations.SERVER_PROXY,
                clientSide = Locations.CLIENT_PROXY)
    public static CommonProxy   proxy;
    
    @Override
    public String getModId()
    {
        // TODO Auto-generated method stub
        return Archive.MOD_ID;
    }

    @Override
    public String getModName()
    {
        // TODO Auto-generated method stub
        return Archive.MOD_NAME;
    }

    @Override
    public String getModPrefix()
    {
        // TODO Auto-generated method stub
        return Archive.MOD_PREFIX;
    }

    @Override
    public String getModVersion()
    {
        // TODO Auto-generated method stub
        return Archive.MOD_VERSION;
    }

    @FingerprintWarning
    public void invalidFingerprint(final FMLFingerprintViolationEvent event)
    {
        /*
         * Report (log) to the user that the version of Harvestry they are using
         * has been changed/tampered with
         */
        Handler.log(Level.SEVERE, Archive.INVALID_FINGERPRINT_MSG);
    }

    @PreInit
    public void preInit(final FMLPreInitializationEvent evt)
    {
        GameRegistry.registerWorldGenerator(WorldGenHandler.instance);

        MinecraftForge.ORE_GEN_BUS.register(WorldGenHandler.instance);
    }

    @Init
    public void init(final FMLInitializationEvent event)
    {
        proxy.initCapes();
        MinecraftForge.EVENT_BUS.register(new StatEventHandler());
        StatEventHandler.addModToList(this);
        AdvancedModelLoader.registerModelHandler(new TechneModelLoader());
    }

    @PostInit
    public void PostInit(final FMLPostInitializationEvent event)
    {}
}