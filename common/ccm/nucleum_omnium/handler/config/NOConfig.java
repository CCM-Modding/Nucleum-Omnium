package ccm.nucleum_omnium.handler.config;

import cpw.mods.fml.common.FMLCommonHandler;

import ccm.nucleum_omnium.configuration.AdvConfiguration;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Properties;

public final class NOConfig implements IConfig {

    public static final String UNIVERSAL   = "Universal";

    public static final String CLIENT_SIDE = "Client Only";

    public static final String SERVER_SIDE = "Server Only";

    private AdvConfiguration   config;

    @Override
    public IConfig setConfiguration(final AdvConfiguration config) {
        this.config = config;
        return this;
    }

    @Override
    public AdvConfiguration getConfiguration() {
        return config;
    }

    @Override
    public void init() {

        initConfigs(config);

        if (FMLCommonHandler.instance().getSide().isServer()) {
            initServerConfigs(config);
        } else {
            initClientConfigs(config);
        }
    }

    private void initConfigs(final AdvConfiguration config) {
        config.addCustomCategoryComment(UNIVERSAL,
                                        "This Category has options that affect both Client and Server");

        Properties.debug = config.getProp(UNIVERSAL, "Should debug information be displayed?", true);
    }

    private void initServerConfigs(final AdvConfiguration config) {
        // config.addCustomCategoryComment(SERVER_SIDE,
        // "This Category only has server side options");
    }

    private void initClientConfigs(final AdvConfiguration config) {
        config.addCustomCategoryComment(CLIENT_SIDE, "This Category only has client side options");

        Properties.rain = config.getProp(CLIENT_SIDE, "Should " + Archive.MOD_NAME
                                                      + " turn off the rain sound for your client?", false);

        Properties.capeHD = config.getProp(CLIENT_SIDE, "Should capes be High Definition?", true);
    }
}