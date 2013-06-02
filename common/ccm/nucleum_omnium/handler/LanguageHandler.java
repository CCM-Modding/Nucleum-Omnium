package ccm.nucleum_omnium.handler;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.LanguageRegistry;

import ccm.nucleum_omnium.helper.LanguageHelper;

public class LanguageHandler
{

    public LanguageHandler(String path,
                           String[] supportedLangs)
    {
        ArrayList<String> langs = new ArrayList<String>();

        for (String lang : supportedLangs){
            langs.add(path + lang + ".xml");
        }

        loadLangs(langs);
    }

    /***
     * Loads in all the localization files from the supportedLangs String Array
     */
    private void loadLangs(ArrayList<String> langs)
    {
        // For every file specified in supportedLangs, load them into the Language Registry
        for (String localizationFile : langs){
            LanguageRegistry.instance().loadLocalization(localizationFile,
                                                         LanguageHelper.getLocaleFromFileName(localizationFile),
                                                         LanguageHelper.isXMLLanguageFile(localizationFile));
        }
    }
}