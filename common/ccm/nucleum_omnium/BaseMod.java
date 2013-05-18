package ccm.nucleum_omnium;

import java.io.File;

import net.minecraftforge.common.Configuration;
import ccm.nucleum_omnium.helper.LanguageHelper;
import cpw.mods.fml.common.registry.LanguageRegistry;

public abstract class BaseMod implements IMod
{

    protected File config_Folder;

    protected void setConfigFolderBase(final File folder)
    {
        this.config_Folder = new File(folder.getAbsolutePath() + "/" + this.getConfigBaseFolder() + "/");
    }

    protected File getConfigFile()
    {
        return new File(this.config_Folder.getAbsolutePath() + "/" + this.getModName() + ".cfg");
    }

    protected Configuration getConfig()
    {
        return new Configuration(this.getConfigFile());
    }

    protected String getConfigBaseFolder()
    {
        return "CCM-Modding";
    }

    protected void loadLangs(final String[] languages)
    {
        for (final String localizationFile : languages){
            LanguageRegistry.instance().loadLocalization(localizationFile,
                                                         LanguageHelper.getLocaleFromFileName(localizationFile),
                                                         LanguageHelper.isXMLLanguageFile(localizationFile));
        }
    }
}