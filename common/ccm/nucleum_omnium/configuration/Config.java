package ccm.nucleum_omnium.configuration;

import net.minecraftforge.common.Configuration;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Properties;
import cpw.mods.fml.common.FMLCommonHandler;

public class Config
{

    public static void init(final Configuration config)
    {
        if (FMLCommonHandler.instance().getSide().isServer()){
            initServerConfigs(config);
        }else{
            initClientConfigs(config);
        }
    }

    private static void initServerConfigs(final Configuration config)
    {}

    private static void initClientConfigs(final Configuration config)
    {
        config.addCustomCategoryComment(Properties.CLIENT_SIDE, "This Category only has client side options");

        Properties.rain = config.get(Properties.CLIENT_SIDE, "Should " + Archive.MOD_NAME + " turn off the rain sound for your client?", false).getBoolean(false);
    }
}