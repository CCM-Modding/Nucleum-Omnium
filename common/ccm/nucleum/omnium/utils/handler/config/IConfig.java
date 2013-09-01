/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.config;

import ccm.nucleum.omnium.configuration.AdvConfiguration;

public abstract class IConfig
{
    protected AdvConfiguration config;

    /**
     * @return The configuration file
     */
    public AdvConfiguration getConfiguration()
    {
        return config;
    }

    /** sets the configuration file */
    public IConfig setConfiguration(final AdvConfiguration config)
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