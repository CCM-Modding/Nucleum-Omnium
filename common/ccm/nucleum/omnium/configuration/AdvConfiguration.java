/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.configuration;

import java.io.File;

import net.minecraftforge.common.Configuration;

/**
 * This class offers advanced configurations capabilities, to "Enhance" normal Forge Configurations.
 */
public class AdvConfiguration extends Configuration
{

    /**
     * Creates a configuration file for the file given in parameter.
     * 
     * @param file
     *            The File to make a Configuration File out of
     */
    public AdvConfiguration(final File file)
    {
        super(file);
    }

    /**
     * Creates a configuration file for the file given in parameter. And makes sure that the categories are case sensitive
     * 
     * @param file
     *            The File to make a Configuration File out of
     * @param caseSensitiveCustomCategories
     *            True if you want your custom categories to be case sensitive, False otherwise
     */
    public AdvConfiguration(final File file, final boolean caseSensitiveCustomCategories)
    {
        super(file, caseSensitiveCustomCategories);
    }

    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getProp(final String category, final String key, final boolean defaultValue)
    {

        return super.get(category, key, defaultValue).getBoolean(defaultValue);
    }

    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public int getProp(final String category, final String key, final int defaultValue)
    {

        return super.get(category, key, defaultValue).getInt();
    }

    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProp(final String category, final String key, final String defaultValue)
    {

        return super.get(category, key, defaultValue).getString();
    }

    /**
     * If a pre-existing Configuration file didn't exist it creates a new one. If there were changes to the existing Configuration file, It saves them. Otherwise it doesn't do
     * anything
     */
    @Override
    public void save()
    {
        if (hasChanged())
        {
            super.save();
        }
    }
}