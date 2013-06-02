package ccm.nucleum_omnium;

import java.io.File;

import net.minecraftforge.common.Configuration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class BaseMod implements IMod
{

    protected Mod  mod = this.getClass().getAnnotation(Mod.class);

    protected File config_Folder;

    protected void setConfigFolderBase(final File folder)
    {
        this.config_Folder = new File(folder.getAbsolutePath() + "/" + this.getConfigBaseFolder() + "/");
    }

    protected File getConfigFile()
    {
        return new File(this.config_Folder.getAbsolutePath() + "/" + this.getName() + ".cfg");
    }

    protected Configuration getConfig()
    {
        return new Configuration(this.getConfigFile());
    }

    protected String getConfigBaseFolder()
    {
        return "CCM-Modding";
    }

    protected Configuration initializeConfig(final FMLPreInitializationEvent evt)
    {

        this.setConfigFolderBase(evt.getModConfigurationDirectory());

        return this.getConfig();
    }

    @Override
    public String getId()
    {
        return this.mod.modid();
    }

    @Override
    public String getName()
    {
        return this.mod.name();
    }

    @Override
    public String getPrefix()
    {
        return this.mod.modid();
    }

    @Override
    public String getVersion()
    {
        return this.mod.version();
    }
}