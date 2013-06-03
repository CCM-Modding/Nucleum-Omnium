package ccm.nucleum_omnium.configuration;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

import cpw.mods.fml.common.FMLCommonHandler;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Properties;

public class Config
{

    public static void init(final Configuration config)
    {
        try{
            Handler.log(NucleumOmnium.instance, "Loading configuration");
            // Loads a pre-existing Configuration file.
            config.load();
            if (FMLCommonHandler.instance().getSide().isServer()){
                initServerConfigs(config);
            }else{
                initClientConfigs(config);
            }
        }catch(final Exception e){
            Handler.log(NucleumOmnium.instance, Level.SEVERE, Archive.MOD_NAME + " has had a problem loading its configuration/n");
            e.printStackTrace();
        }finally{
            if (config.hasChanged()){
                /*
                 * If a pre-existing Configuration file didn't exist it creates
                 * a new one. If there were changes to the existing
                 * Configuration file, It saves them.
                 */
                config.save();
            }
        }
    }

    private static void initServerConfigs(final Configuration config)
    {
        // config.addCustomCategoryComment(Properties.SERVER_SIDE,
        // "This Category only has server side options");
    }

    private static void initClientConfigs(final Configuration config)
    {
        config.addCustomCategoryComment(Properties.CLIENT_SIDE, "This Category only has client side options");

        Properties.rain = config.get(Properties.CLIENT_SIDE, "Should " + Archive.MOD_NAME + " turn off the rain sound for your client?", false).getBoolean(false);
    }
}