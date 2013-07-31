/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.handler.config;

import ccm.nucleum_omnium.configuration.AdvConfiguration;

public interface IConfig
{

    public AdvConfiguration getConfiguration();

    public void init();

    public IConfig setConfiguration(final AdvConfiguration config);
}
