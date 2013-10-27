/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.configuration;

import lib.cofh.util.ConfigHandler;

public abstract class IConfig
{
    protected ConfigHandler config;

    /**
     * @return The configuration file
     */
    public ConfigHandler getConfiguration()
    {
        return config;
    }

    /** sets the configuration file */
    public IConfig setConfiguration(final ConfigHandler configHandler)
    {
        config = configHandler;
        return this;
    }

    /** Initializes the configuration file */
    public abstract void init();
}