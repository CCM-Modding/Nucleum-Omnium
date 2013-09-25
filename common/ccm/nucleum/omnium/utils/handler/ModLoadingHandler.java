/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import ccm.nucleum.omnium.BaseNIC;
import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.utils.handler.config.ConfigurationHandler;
import ccm.nucleum.omnium.utils.handler.config.IConfig;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

public final class ModLoadingHandler extends BaseNIC
{
    /**
     * Does all the common Pre-Initialization logic
     * 
     * @param mod
     *            The mod that is Pre-Initializing
     * @param evt
     *            The {@link FMLPreInitializationEvent} that is passed to the method
     * @param config
     *            The configuration class for this mod
     */
    public static void loadMod(final IMod mod, final FMLPreInitializationEvent evt, final IConfig config)
    {
        mod.setLogger(CCMLogger.initLogger(evt));
        mod.initConfig(evt);
        if (config != null)
        {
            ConfigurationHandler.init(mod, config);
        }
    }
}