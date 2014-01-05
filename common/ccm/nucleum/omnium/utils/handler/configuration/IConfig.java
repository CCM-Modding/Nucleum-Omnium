/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.configuration;

import lib.cofh.util.ConfigHandler;
import cpw.mods.fml.common.FMLCommonHandler;

public abstract class IConfig
{
    public static final String UNIVERSAL = "Universal";
    public static final String CLIENT_SIDE = "Client";
    public static final String SERVER_SIDE = "Server";

    /** Initializes the configuration file */
    public void init(ConfigHandler config)
    {
        initCommon(config);
        if (FMLCommonHandler.instance().getSide().isServer())
        {
            initServer(config);
        } else
        {
            initClient(config);
        }
    }

    protected abstract void initCommon(ConfigHandler config);

    protected void initClient(ConfigHandler config)
    {
        config.getConfiguration().addCustomCategoryComment(CLIENT_SIDE, "This Category only contains options that affect the client");
    }

    protected void initServer(ConfigHandler config)
    {
        config.getConfiguration().addCustomCategoryComment(SERVER_SIDE, "This Category only contains options that affect the server");
    }
}