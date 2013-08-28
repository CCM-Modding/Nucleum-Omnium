/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.config;

import ccm.nucleum.omnium.configuration.AdvConfiguration;

public interface IConfig
{
    public AdvConfiguration getConfiguration();

    public void init();

    public IConfig setConfiguration(final AdvConfiguration config);
}
