package ccm.nucleum_omnium.utils.language;

import java.util.ArrayList;
import java.util.List;

import ccm.nucleum_omnium.helper.LanguageHelper;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * The Super Class of ANY Language Pack within all the CCM Mods
 * 
 * @author CaptainShadows
 */
public abstract class ILanguagePack {
    
    protected String           currentPath;
    
    /**
     * Private {@code List<String>} that contains the languages, Shared with all
     * Language Packs
     */
    private final List<String> supportedLanguages = new ArrayList<String>();
    
    /**
     * Sets the currentPath and Clears the
     * {@code List<String> supportedLanguages}
     */
    public ILanguagePack(final String currentPath) {
        this.currentPath = currentPath;
        supportedLanguages.clear();
    }
    
    /**
     * Gets a usable version of the Language Name
     * 
     * @param name
     *            The name of the language
     * @return The usable version of that name. For our purposes that version
     *         should get added to the {@code List<String> supportedLanguages}
     */
    public String getPath(final String name) {
        return currentPath + name + ".xml";
    }
    
    /***
     * Loads in all the language files into MineCraft
     */
    public void loadLangs() {
        for (final String langFile : supportedLanguages) {
            LanguageRegistry.instance()
                    .loadLocalization(langFile,
                                      LanguageHelper.getLangFromFileName(langFile),
                                      LanguageHelper.isXMLLanguageFile(langFile));
        }
    }
}