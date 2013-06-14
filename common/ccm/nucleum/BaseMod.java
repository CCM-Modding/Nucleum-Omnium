package ccm.nucleum;

import java.io.File;

import ccm.nucleum.configuration.AdvConfiguration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * This class should be the super class of any CCM Mod, as it not only offers a
 * few nice Configuration helpers, but it also keeps from having to implement
 * the methods inside of {@link IMod}
 * 
 * @author CaptainShadows
 */
public abstract class BaseMod implements IMod {
    
    private File config_Folder;
    
    /**
     * @param folder
     *            The Folder in which to store all files in
     */
    protected void setConfigFolderBase(final File folder) {
        config_Folder = new File(folder.getAbsolutePath() + "/" + getConfigFolder() + "/");
    }
    
    /**
     * @return A new {@link File}
     */
    protected File getConfigFile() {
        return new File(config_Folder.getAbsolutePath() + "/" + getName() + ".cfg");
    }
    
    /**
     * @return A new instance of {@link AdvConfiguration}
     */
    protected AdvConfiguration getConfig() {
        return new AdvConfiguration(getConfigFile(), true);
    }
    
    /**
     * @return The name of the Config Folder to put all configs in. Defaults to
     *         "CCM-Modding"
     */
    protected String getConfigFolder() {
        return "CCM-Modding";
    }
    
    /**
     * This is a shorter way of creating a new Configuration File
     * 
     * @param evt
     *            A FMLPreInitializationEvent
     * @return A new instance of {@link AdvConfiguration}
     */
    protected AdvConfiguration initializeConfig(final FMLPreInitializationEvent evt) {
        
        setConfigFolderBase(evt.getModConfigurationDirectory());
        
        return getConfig();
    }
    
    @Override
    public String getModId() {
        return this.getClass().getAnnotation(Mod.class).modid();
    }
    
    @Override
    public String getName() {
        return this.getClass().getAnnotation(Mod.class).name();
    }
    
    @Override
    public String getVersion() {
        return this.getClass().getAnnotation(Mod.class).version();
    }
}