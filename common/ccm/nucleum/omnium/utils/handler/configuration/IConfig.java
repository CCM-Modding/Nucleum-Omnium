/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.configuration;

import ccm.nucleum.omnium.configuration.ConfigurationWrapper;

public abstract class IConfig
{
    protected ConfigurationWrapper config;

    /**
     * @return The configuration file
     */
    public ConfigurationWrapper getConfiguration()
    {
        return config;
    }

    /** sets the configuration file */
    public IConfig setConfiguration(final ConfigurationWrapper config)
    {
        this.config = config;
        return this;
    }

    /** loads the configuration file */
    public void load()
    {
        if (config != null)
        {
            config.load();
        }
    }

    /** saves the configuration file if changes have been made */
    public void save()
    {
        if (config != null)
        {
            if (config.hasChanged())
            {
                config.load();
            }
        }
    }

    /** Initializes the configuration file */
    public abstract void init();
}