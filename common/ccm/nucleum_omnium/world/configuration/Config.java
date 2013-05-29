package ccm.nucleum_omnium.world.configuration;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Properties;

public class Config
{

    public static void init(final Configuration config)
    {
        try{
            Handler.log(NucleumOmnium.instance, "Loading World Configuration");
            // Loads a pre-existing Configuration file.
            config.load();

            config.addCustomCategoryComment("World Generation", "This Category only affects World Generatiion");

            Properties.retroOreGen = config.get("World Generation", "Should " + Archive.NW_NAME + " regenerate all ores, added by CCM Mods?", false).getBoolean(false);

        }catch(final Exception e){
            Handler.log(NucleumOmnium.instance, Level.SEVERE, Archive.NW_NAME + " has had a problem loading its configuration/n");
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
}