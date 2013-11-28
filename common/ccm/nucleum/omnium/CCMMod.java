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
 * This class should be the super class of any CCM Mod, as it not only offers a few nice Configuration helpers, but it also keeps from having to implement some of the methods
 * inside of {@link IMod}
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
        File configFile = new File(configFolder.getAbsolutePath() + "/" + getName() + ".cfg");

        // Create the config handler
        ConfigHandler config = new ConfigHandler(this.getClass().getAnnotation(Mod.class).version());

        // Set the Configuration inside the Handler
        config.setConfiguration(new Configuration(configFile, true));

        // Set the handler
        CCMMod.config = config;
    }

    public ConfigHandler getConfigHandler()
    {
        return config;
    }

    public CCMLogger getLogger()
    {
        return logger;
    }

    public void setLogger(CCMLogger log)
    {
        logger = log;
    }

    public String getModID()
    {
        return this.getClass().getAnnotation(Mod.class).modid();
    }

    public String getName()
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
                mod.getLogger().debug("LOADING CONFIGURATION FOR %s", mod.getName());

                // Loads a pre-existing Configuration file.
                mod.getConfigHandler().getConfiguration().load();

                config.setConfiguration(mod.getConfigHandler());
                config.init();

                // Init the config
                mod.getConfigHandler().init();

            } catch (final Exception e)
            {
                mod.getLogger().printCatch(e, "%s HAS HAD A PROBLEM LOADING ITS CONFIGURATION", mod.getName());
            } finally
            {
                mod.getConfigHandler().save();
            }
        }
    }
}