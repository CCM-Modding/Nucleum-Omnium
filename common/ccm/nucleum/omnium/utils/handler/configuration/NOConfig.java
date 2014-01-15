/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.configuration;

import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.utils.lib.Properties;

import lib.cofh.util.ConfigHandler;

public final class NOConfig extends IConfig
{
    @Override
    protected void initCommon(final ConfigHandler config)
    {
        config.getConfiguration().addCustomCategoryComment(UNIVERSAL, "This Category has options that affect both Client and Server");
        Properties.DEBUG = config.get(UNIVERSAL, "DEBUG", "Should debug information be displayed?", false);
        Properties.FLAT_BEDROCK = config.get(UNIVERSAL, "FlatBedrock", "Should Bedrock be flattened?", false);
        Properties.RETRO_FLAT_BEDROCK = config.get(UNIVERSAL, "RetroFlatBedrock", "Flatten bedrock in old chunks", false);
        Properties.RETRO_ORE_GEN = config.get(UNIVERSAL, "RetroOres", "Generate CCM Ores in old chunks", false);
        config.getCategory(UNIVERSAL).getValues().get("RetroFlatBedrock").set(false);
        config.getCategory(UNIVERSAL).getValues().get("RetroOres").set(false);
    }

    @Override
    protected void initClient(final ConfigHandler config)
    {
        super.initClient(config);
        Properties.RAIN = config.get(CLIENT_SIDE, "RainSound", "Should "
                                                               + NucleumOmnium.instance.name()
                                                               + " turn off the rain sound for your client?", false);
        Properties.CAPE_HD = config.get(CLIENT_SIDE, "HDCapes", "Should capes be High Definition?", true);
    }
}