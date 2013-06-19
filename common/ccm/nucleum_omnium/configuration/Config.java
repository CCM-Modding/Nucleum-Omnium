package ccm.nucleum_omnium.configuration;

import java.util.logging.Level;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Properties;
import cpw.mods.fml.common.FMLCommonHandler;

public final class Config {
    
    public static void init(final AdvConfiguration config) {
        try {
            Handler.log(NucleumOmnium.instance, "Loading configuration");
            // Loads a pre-existing Configuration file.
            config.load();
            if (FMLCommonHandler.instance().getSide().isServer()) {
                Config.initServerConfigs(config);
            } else {
                Config.initClientConfigs(config);
            }
        } catch (final Exception e) {
            Handler.log(NucleumOmnium.instance, Level.SEVERE, Archive.MOD_NAME + " has had a problem loading its configuration\n");
            e.printStackTrace();
        } finally {
            config.save();
        }
    }
    
    private static void initServerConfigs(final AdvConfiguration config) {
        // config.addCustomCategoryComment(Properties.SERVER_SIDE,
        // "This Category only has server side options");
    }
    
    private static void initClientConfigs(final AdvConfiguration config) {
        config.addCustomCategoryComment(Properties.CLIENT_SIDE, "This Category only has client side options");
        
        Properties.rain = config.getProp(Properties.CLIENT_SIDE, "Should " + Archive.MOD_NAME + " turn off the rain sound for your client?", false);
    }
}