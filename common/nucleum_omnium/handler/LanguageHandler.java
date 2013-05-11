package nucleum_omnium.handler;

import java.util.logging.Level;

import nucleum_omnium.helper.LanguageHelper;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class LanguageHandler
{

    /**
     * Creates the Localizations for everything in the Mod.
     */
    public static void loadLanguages(String[] langFiles)
    {
        Handler.log(Level.INFO, "Loading Languages");
        for (final String localizationFile : langFiles){
            LanguageRegistry.instance().loadLocalization(localizationFile,
                                                         LanguageHelper.getLocaleFromFileName(localizationFile),
                                                         LanguageHelper.isXMLLanguageFile(localizationFile));
        }
    }
}