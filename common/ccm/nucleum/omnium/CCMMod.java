/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium;

import java.io.File;

import lib.cofh.util.ConfigHandler;
import net.minecraftforge.common.Configuration;
import ccm.nucleum.omnium.utils.handler.configuration.IConfig;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * This class should be the super class of any CCM Mod, as it offers a few nice Configuration helpers
 */
public abstract class CCMMod
{
    /* DATA FIELDS */
    /** Current Configuration File */
    private static ConfigHandler config;

    /** Current Logger */
    private static CCMLogger logger;

    /* Methods */
    public void initConfig(final FMLPreInitializationEvent evt)
    {
        // Get all the files
        File configFolder = new File(evt.getModConfigurationDirectory().getAbsolutePath() + "/CCM-Modding/");
        File configFile = new File(configFolder.getAbsolutePath() + "/" + name() + ".cfg");

        // Create the config handler
        ConfigHandler config = new ConfigHandler();

        // Set the Configuration inside the Handler
        config.setConfiguration(new Configuration(configFile, true));

        // Set the handler
        CCMMod.config = config;
    }

    public ConfigHandler config()
    {
        return config;
    }

    public CCMLogger logger()
    {
        return logger;
    }

    public void setLogger(CCMLogger log)
    {
        logger = log;
    }

    public String id()
    {
        return this.getClass().getAnnotation(Mod.class).modid();
    }

    public String name()
    {
        return this.getClass().getAnnotation(Mod.class).name();
    }

    /**
     * Does all the common Pre-Initialization logic
     * 
     * @param mod
     *            The mod that is Pre-Initializing
     * @param evt
     *            The {@link FMLPreInitializationEvent} that is passed to the method
     * @param config
     *            The configuration class for this mod
     */
    public static void loadMod(final CCMMod mod, final FMLPreInitializationEvent evt, final IConfig config)
    {
        mod.setLogger(CCMLogger.initLogger(evt));
        mod.initConfig(evt);

        if (config != null)
        {
            try
            {
                mod.logger().debug("LOADING CONFIGURATION FOR %s", mod.name());

                // Loads a pre-existing Configuration file.
                mod.config().getConfiguration().load();

                config.setConfiguration(mod.config());
                config.init();

                // Init the config
                mod.config().init();

            } catch (final Exception e)
            {
                mod.logger().printCatch(e, "%s HAS HAD A PROBLEM LOADING ITS CONFIGURATION", mod.name());
            } finally
            {
                mod.config().save();
            }
        }
    }
}