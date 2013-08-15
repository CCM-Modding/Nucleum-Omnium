/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler;

import java.util.ArrayList;
import java.util.List;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.utils.exeptions.DupeExeption;

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
     * "Loads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void loadMod(final IMod mod)
    {
        if (!isModLoaded(mod))
        {
            LogHandler.initLog(mod);

        } else
        {
            throw new DupeExeption(mod);
        }
    }

    /**
     * "UnLoads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void unLoadMod(final IMod mod)
    {
        if (modsLoaded.contains(mod))
        {
            modsLoaded.remove(mod);
        }
    }
}