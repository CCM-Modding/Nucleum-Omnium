package nucleum_omnium;

import nucleum_omnium.proxy.CommonProxy;
import nucleum_omnium.utils.lib.Archive;
import nucleum_omnium.utils.lib.Locations;
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

@Mod(modid = Archive.MOD_ID,
     name = Archive.MOD_NAME,
     version = Archive.MOD_VERSION,
     useMetadata = true,
     dependencies = Archive.MOD_DEPENDANCIES,
     certificateFingerprint = Archive.MOD_FIGERPRINT)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = Archive.MOD_CHANNEL/*, packetHandler = PacketHandler.class*/)
public class NucleumOmnium
{

    /**
     * The Harvestry Instance
     */
    @Instance(Archive.MOD_ID)
    public static NucleumOmnium   instance;

    /**
     * The Harvestry proxy
     */
    @SidedProxy(serverSide = Locations.SERVER_PROXY, clientSide = Locations.CLIENT_PROXY)
    public static CommonProxy proxy;

    @FingerprintWarning
    public void invalidFingerprint(final FMLFingerprintViolationEvent event)
    {
        /*
         * Report (log) to the user that the version of Harvestry they are using
         * has been changed/tampered with
         */
        //Handler.log(Level.SEVERE, Archive.INVALID_FINGERPRINT_MSG);
    }

    @PreInit
    public void preInit(final FMLPreInitializationEvent evt)
    {

    }

    @Init
    public void init(final FMLInitializationEvent event)
    {

    }

    @PostInit
    public void PostInit(final FMLPostInitializationEvent event)
    {}
}