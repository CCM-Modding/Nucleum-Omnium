package ccm.nucleum_omnium.handler;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.LanguageRegistry;

import ccm.nucleum_omnium.helper.LanguageHelper;

public class LanguageHandler
{

    public LanguageHandler(final String path,
                           final String[] supportedLangs)
    {
        final ArrayList<String> langs = new ArrayList<String>();

        for (final String lang : supportedLangs){
            langs.add(path + "/" + lang + ".xml");
        }

        this.loadLangs(langs);
    }

    /***
     * Loads in all the localization files from the langs String Array
     */
    private void loadLangs(final ArrayList<String> langs)
    {
        // For every file specified in langs, load them into the Language Registry
        for (final String localizationFile : langs){
            LanguageRegistry.instance()
                    .loadLocalization(localizationFile, LanguageHelper.getLangFromFileName(localizationFile), LanguageHelper.isXMLLanguageFile(localizationFile));
        }
    }
}