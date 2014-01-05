/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium;

import java.io.File;

import lib.cofh.util.ConfigHandler;
import net.minecraftforge.common.Configuration;
import ccm.nucleum.omnium.utils.handler.configuration.IConfig;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * This class should be the super class of any CCM Mod, as it offers a few nice Configuration helpers
 */
public class CCMMod
{
    public static File configDir;
    static
    {
        configDir = new File(Loader.instance().getConfigDir().getAbsolutePath() + "/CCM-Modding/");
        configDir.mkdirs();
    }
    /* DATA FIELDS */
    /** Current Configuration File */
    private ConfigHandler config;
    /** Current Logger */
    private CCMLogger logger;

    /* Methods */
    protected void initConfig()
    {
        // Get all the files
        File configFile = new File(configDir.getAbsolutePath() + "/" + name() + ".cfg");
        // Create the config handler
        config = new ConfigHandler();
        // Set the Configuration inside the Handler
        config.setConfiguration(new Configuration(configFile, true));
    }

    public ConfigHandler config()
    {
        return config;
    }

    public CCMLogger logger()
    {
        return logger;
    }

    protected void initLogger()
    {
        logger = CCMLogger.init(name());
    }

    public ModContainer mod()
    {
        return Loader.instance().getIndexedModList().get(id());
    }

    public String id()
    {
        return this.getClass().getAnnotation(Mod.class).modid();
    }

    public String name()
    {
        return mod().getName();
    }

    public String version()
    {
        return mod().getVersion();
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
    protected static void loadMod(final CCMMod mod, final IConfig config)
    {
        mod.initLogger();
        mod.initConfig();
        if (config != null)
        {
            CCMLogger.DEFAULT.info("Loading configuration for %s", mod.name());
            // Loads a pre-existing Configuration file.
            mod.config().load();
            config.init(mod.config());
            // Init the config
            mod.config().init();
            mod.config().save();
        }
    }
}