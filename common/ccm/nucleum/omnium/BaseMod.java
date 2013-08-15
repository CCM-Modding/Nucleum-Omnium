/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import ccm.nucleum.omnium.configuration.AdvConfiguration;

/**
 * This class should be the super class of any CCM Mod, as it not only offers a few nice Configuration helpers, but it also keeps from having to implement some of the methods
 * inside of {@link IMod}
 * 
 * @author Captain_Shadows
 */
public abstract class BaseMod implements IMod
{

    /**
     * Configuration Folder
     */
    private File config_Folder;

    /**
     * Current Configuration File
     */
    public static AdvConfiguration config;

    /**
     * @return A new instance of {@link AdvConfiguration}
     */
    protected AdvConfiguration getAdvConfigFile()
    {
        return new AdvConfiguration(getConfigurationFile(), true);
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
        return new File(config_Folder.getAbsolutePath() + "/" + getName() + ".cfg");
    }

    /**
     * This is a shorter way of creating a new Configuration File
     * 
     * @param evt
     *            A FMLPreInitializationEvent
     * @return A new instance of {@link AdvConfiguration}
     */
    protected void initializeConfig(final FMLPreInitializationEvent evt)
    {

        setConfigFolderBase(evt.getModConfigurationDirectory());

        setConfigFile(getAdvConfigFile());
    }

    /**
     * @param folder
     *            The Folder in which to store all files in
     */
    protected void setConfigFolderBase(final File folder)
    {
        config_Folder = new File(folder.getAbsolutePath() + "/" + getConfigFolder() + "/");
    }

    @Override
    public AdvConfiguration getConfigFile()
    {
        return config;
    }

    @Override
    public void setConfigFile(final AdvConfiguration conf)
    {
        config = conf;
    }

    @Override
    public String getModId()
    {
        return this.getClass().getAnnotation(Mod.class).modid();
    }

    @Override
    public String getName()
    {
        return this.getClass().getAnnotation(Mod.class).name();
    }
}