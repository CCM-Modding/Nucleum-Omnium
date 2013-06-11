package ccm.nucleum_network;

import java.util.logging.Level;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import lib.org.modstats.ModstatInfo;

import ccm.nucleum_omnium.BaseMod;
import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.handler.Handler;

import ccm.nucleum_network.utils.lib.Archive;

@Mod(modid = Archive.MOD_ID,
     name = Archive.MOD_NAME,
     version = Archive.MOD_VERSION,
     certificateFingerprint = Archive.MOD_FIGERPRINT)
@NetworkMod(clientSideRequired = true,
            serverSideRequired = false,
            channels = Archive.MOD_CHANNEL)
@ModstatInfo(prefix = Archive.MOD_PREFIX)
public class NucleumNetwork extends BaseMod implements IMod
{

    /**
     * The Instance
     */
    @Instance(Archive.MOD_ID)
    public static NucleumNetwork instance;

    @FingerprintWarning
    public void invalidFingerprint(final FMLFingerprintViolationEvent event)
    {
        /*
         * Report (log) to the user that the version of Harvestry they are using
         * has been changed/tampered with
         */
        Handler.log(this, Level.SEVERE, Archive.INVALID_FINGERPRINT_MSG);
    }
}
