/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import ccm.nucleum.omnium.configuration.ConfigurationWrapper;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

/**
 * This class should be the super class of any CCM Mod, as it not only offers a few nice Configuration helpers, but it also keeps from having to implement some of the methods
 * inside of {@link IMod}
 */
public abstract class CCMMod implements IMod
{
    /* DATA FIELDS */
    /** Configuration Folder */
    private File configFolder;

    /** Current Configuration File */
    private static ConfigurationWrapper config;

    /** Current Logger */
    private static CCMLogger logger;

    /* INSTANCE METHODS */
    /**
     * @return A new instance of {@link ConfigurationWrapper}
     */
    protected ConfigurationWrapper getAdvConfigFile()
    {
        return new ConfigurationWrapper(getConfigurationFile(), true);
    }

    /**
     * @return The name of the Config Folder to put all configs in. Defaults to "CCM-Modding"
     */
    protected String getConfigFolder()
    {
        return "CCM-Modding";
    }

    /**
     * @return A new {@link File}
     */
    protected File getConfigurationFile()
    {
        return new File(configFolder.getAbsolutePath() + "/" + getName() + ".cfg");
    }

    /**
     * @param folder
     *            The Folder in which to store all files in
     */
    protected void setConfigurationBaseFolder(final File folder)
    {
        configFolder = new File(folder.getAbsolutePath() + "/" + getConfigFolder() + "/");
    }

    /* OVERRIDEN IMod METHODS */
    @Override
    public void initConfig(final FMLPreInitializationEvent evt)
    {
        setConfigurationBaseFolder(evt.getModConfigurationDirectory());
        setConfiguration(getAdvConfigFile());
    }

    @Override
    public ConfigurationWrapper getConfiguration()
    {
        return config;
    }

    @Override
    public void setConfiguration(final ConfigurationWrapper conf)
    {
        config = conf;
    }

    @Override
    public CCMLogger getLogger()
    {
        return logger;
    }

    @Override
    public void setLogger(CCMLogger log)
    {
        logger = log;
    }

    @Override
    public String getModID()
    {
        return this.getClass().getAnnotation(Mod.class).modid();
    }

    @Override
    public String getName()
    {
        return this.getClass().getAnnotation(Mod.class).name();
    }
}