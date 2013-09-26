/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.configuration;

import java.io.File;

import net.minecraftforge.common.Configuration;

/**
 * This class offers advanced configurations capabilities, to "Enhance" normal Forge Configurations.
 */
public class ConfigurationWrapper extends Configuration
{

    /**
     * Creates a configuration file for the file given in parameter.
     * 
     * @param file
     *            The File to make a Configuration File out of
     */
    public ConfigurationWrapper(final File file)
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
    public ConfigurationWrapper(final File file, final boolean caseSensitiveCustomCategories)
    {
        super(file, caseSensitiveCustomCategories);
    }

    /**
     * @return the boolean property
     */
    public boolean getProp(final String category, final String key, final boolean defaultValue)
    {

        return super.get(category, key, defaultValue).getBoolean(defaultValue);
    }

    /**
     * @return the integer property
     */
    public int getProp(final String category, final String key, final int defaultValue)
    {

        return super.get(category, key, defaultValue).getInt(defaultValue);
    }

    /**
     * @return the string property
     */
    public String getProp(final String category, final String key, final String defaultValue)
    {

        return super.get(category, key, defaultValue).getString();
    }

    /**
     * @return the double property
     */
    public double getProp(final String category, final String key, final double defaultValue)
    {

        return super.get(category, key, defaultValue).getDouble(defaultValue);
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