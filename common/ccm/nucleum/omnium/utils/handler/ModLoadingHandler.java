/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import java.util.ArrayList;
import java.util.List;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.utils.exeptions.DupeExeption;

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
     * "Loads" the Mod
     */
    public static void loadMod(final IMod mod)
    {
        if (!isModLoaded(mod))
        {
            LogHandler.init(mod);
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