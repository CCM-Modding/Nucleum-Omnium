/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.configuration;

import lib.cofh.util.ConfigHandler;
import ccm.nucleum.omnium.utils.lib.Archive;
import ccm.nucleum.omnium.utils.lib.Properties;
import cpw.mods.fml.common.FMLCommonHandler;

public final class NOConfig extends IConfig
{
    public static final String UNIVERSAL = "Universal";

    public static final String CLIENT_SIDE = "Client Only";

    public static final String SERVER_SIDE = "Server Only";

    @Override
    public void init(ConfigHandler config)
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

    private void initConfigs(final ConfigHandler config)
    {
        config.getConfiguration().addCustomCategoryComment(UNIVERSAL, "This Category has options that affect both Client and Server");

        Properties.DEBUG = config.get(UNIVERSAL, "Should debug information be displayed?", false);
    }

    private void initServerConfigs(final ConfigHandler config)
    {
        config.getConfiguration().addCustomCategoryComment(SERVER_SIDE, "This Category only has server side options");
    }

    private void initClientConfigs(final ConfigHandler config)
    {
        config.getConfiguration().addCustomCategoryComment(CLIENT_SIDE, "This Category only has client side options");

        Properties.RAIN = config.get(CLIENT_SIDE, "Should " + Archive.MOD_NAME + " turn off the rain sound for your client?", false);

        Properties.CAPE_HD = config.get(CLIENT_SIDE, "Should capes be High Definition?", true);
    }
}