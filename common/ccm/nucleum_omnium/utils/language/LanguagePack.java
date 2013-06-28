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
public final class LanguagePack {

	/**
	 * The current Path to the Language File
	 */
	private String			currentPath;

	/**
	 * Private {@code List<String>} that contains the languages
	 */
	private List<String>	supportedLanguages	= new ArrayList<String>();

	/**
	 * Sets the current Path
	 * 
	 * @param path
	 *            The path
	 */
	public void setPath(final String path) {
		currentPath = path;
	}

	/**
	 * Adds a language to the supported list, Ex: en_US
	 * 
	 * @param lang
	 */
	public void addSuport(final String lang) {
		supportedLanguages.add(getPath(lang));
	}

	/**
	 * Sets the List of supported languages to the one being passed in
	 * 
	 * @param langs
	 */
	public void setSupportedLangs(final List<String> langs) {
		supportedLanguages = langs;
	}

	/**
	 * Gets a usable version of the Language Name
	 * 
	 * @param name
	 *            The name of the language
	 * @return The usable version of that name. For our purposes that version
	 *         should get added to the {@code List<String> supportedLanguages}
	 */
	private String getPath(final String name) {
		return currentPath + name + ".xml";
	}

	/***
	 * Loads in all the language files into MineCraft
	 */
	public void loadLangs() {
		for (final String langFile : supportedLanguages) {
			LanguageRegistry.instance().loadLocalization(	langFile,
															LanguageHelper.getLangFromFileName(langFile),
															LanguageHelper.isXMLLanguageFile(langFile));
		}
	}
}