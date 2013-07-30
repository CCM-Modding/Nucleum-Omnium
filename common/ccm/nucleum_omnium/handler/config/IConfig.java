package ccm.nucleum_omnium.handler.config;

import ccm.nucleum_omnium.configuration.AdvConfiguration;

public interface IConfig {

    public IConfig setConfiguration(final AdvConfiguration config);

    public AdvConfiguration getConfiguration();

    public void init();
}
