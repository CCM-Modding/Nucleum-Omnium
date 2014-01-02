/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.configuration;

import lib.cofh.util.ConfigHandler;
import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.utils.lib.Properties;

public final class NOConfig extends IConfig
{
    @Override
    protected void initCommon(final ConfigHandler config)
    {
        config.getConfiguration().addCustomCategoryComment(UNIVERSAL, "This Category has options that affect both Client and Server");

        Properties.DEBUG = config.get(UNIVERSAL, "Should debug information be displayed?", false);
    }

    @Override
    protected void initClient(final ConfigHandler config)
    {
        super.initClient(config);

        Properties.RAIN = config.get(CLIENT_SIDE, "Should " + NucleumOmnium.instance.name() + " turn off the rain sound for your client?", false);

        Properties.CAPE_HD = config.get(CLIENT_SIDE, "Should capes be High Definition?", true);
    }
}