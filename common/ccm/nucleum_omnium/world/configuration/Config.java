package ccm.nucleum_omnium.world.configuration;

import java.util.logging.Level;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.world.utils.lib.Archive;
import ccm.nucleum_omnium.world.utils.lib.Properties;

public class Config
{

    public Configuration config;

    public Config(Configuration config)
    {
        this.config = config;
    }

    public Configuration getConfig()
    {
        return config;
    }

    private void init(final Configuration config)
    {
        try{
            Handler.log(NucleumOmnium.instance, "Loading World Configuration");
            // Loads a pre-existing Configuration file.
            config.load();

            config.addCustomCategoryComment("World Generation", "This Category only affects World Generatiion");

            Properties.retroOreGen = config.get("World Generation", "Should " + Archive.MOD_NAME + " regenerate all ores, added by CCM Mods?", false).getBoolean(false);

        }catch(final Exception e){
            Handler.log(NucleumOmnium.instance, Level.SEVERE, Archive.MOD_NAME + " has had a problem loading its configuration\n");
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

    public int get(final String category, final String key, final int defaultValue)
    {

        return this.config.get(category, key, defaultValue).getInt();
    }

    public boolean get(final String category, final String key, final boolean defaultValue)
    {

        return this.config.get(category, key, defaultValue).getBoolean(defaultValue);
    }

    public String get(final String category, final String key, final String defaultValue)
    {

        return this.config.get(category, key, defaultValue).getString();
    }

    public Property getProperty(final String category, final String key, final int defaultValue)
    {

        return this.config.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final boolean defaultValue)
    {

        return this.config.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final String defaultValue)
    {

        return this.config.get(category, key, defaultValue);
    }

    public ConfigCategory getCategory(final String category)
    {

        return this.config.getCategory(category);
    }

    public boolean hasCategory(final String category)
    {

        return this.config.hasCategory(category);
    }

    public boolean hasKey(final String category, final String key)
    {

        return this.config.hasKey(category, key);
    }
}