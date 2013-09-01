/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.config;

import cpw.mods.fml.common.FMLCommonHandler;

import ccm.nucleum.omnium.configuration.AdvConfiguration;
import ccm.nucleum.omnium.utils.lib.Archive;
import ccm.nucleum.omnium.utils.lib.Properties;

public final class NOConfig extends IConfig
{
    public static final String UNIVERSAL = "Universal";

    public static final String CLIENT_SIDE = "Client Only";

    public static final String SERVER_SIDE = "Server Only";

    @Override
    public void init()
    {
        initConfigs(config);

        if (FMLCommonHandler.instance().getSide().isServer())
        {
            initServerConfigs(config);
        } else
        {
            initClientConfigs(config);
        }
    }

    private void initConfigs(final AdvConfiguration config)
    {
        config.addCustomCategoryComment(UNIVERSAL, "This Category has options that affect both Client and Server");

        Properties.DEBUG = config.getProp(UNIVERSAL, "Should debug information be displayed?", true);
    }

    private void initServerConfigs(final AdvConfiguration config)
    {
        // config.addCustomCategoryComment(SERVER_SIDE, "This Category only has server side options");
    }

    private void initClientConfigs(final AdvConfiguration config)
    {
        config.addCustomCategoryComment(CLIENT_SIDE, "This Category only has client side options");

        Properties.RAIN = config.getProp(CLIENT_SIDE, "Should " + Archive.MOD_NAME + " turn off the rain sound for your client?", false);

        Properties.CAPE_HD = config.getProp(CLIENT_SIDE, "Should capes be High Definition?", true);
    }
}