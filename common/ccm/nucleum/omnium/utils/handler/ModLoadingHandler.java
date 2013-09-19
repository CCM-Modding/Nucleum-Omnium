/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.utils.exeptions.DupeExeption;
import ccm.nucleum.omnium.utils.handler.config.ConfigurationHandler;
import ccm.nucleum.omnium.utils.handler.config.IConfig;

public final class ModLoadingHandler extends BaseNIC
{

    private static List<IMod> modsLoaded = new ArrayList<IMod>();

    /**
     * Checks if the Mod has being loaded before and throws a exception. The default response is false. Other wise you should get a Exception.
     * 
     * @return false if the Mod has not being loaded yet.
     */
    public static boolean isModLoaded(final IMod mod)
    {
        if (modsLoaded.contains(mod))
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Does all the default Pre-Initialization logic
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
        if (!isModLoaded(mod))
        {
            LogHandler.init(mod, evt);
            mod.initConfig(evt);
            if (config != null)
            {
                ConfigurationHandler.init(mod, config.setConfiguration(mod.getConfigFile()));
            }
        } else
        {
            throw new DupeExeption(mod);
        }
    }

    /**
     * "UnLoads" the Mod
     */
    public static void unLoadMod(final IMod mod)
    {
        if (modsLoaded.contains(mod))
        {
            modsLoaded.remove(mod);
        }
    }
}