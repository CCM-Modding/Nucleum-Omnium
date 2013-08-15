/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler;

import java.util.HashMap;
import java.util.Map;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.utils.exeptions.DupeExeption;

public final class ModLoadingHandler extends BaseNIC
{

    private static Map<IMod, Boolean> modsLoaded = new HashMap<IMod, Boolean>();

    /**
     * Checks if the Mod has being loaded before and throws a exception. The default response is false. Other wise you should get a Exception.
     * 
     * @return false if the Mod has not being loaded yet.
     */
    public static boolean isModLoaded(final IMod mod)
    {
        if (!modsLoaded.containsKey(mod))
        {
            modsLoaded.put(mod, false);
        } else
        {
            throw new DupeExeption(mod);
        }
        return modsLoaded.get(mod);
    }

    /**
     * "Loads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void loadMod(final IMod mod)
    {
        if (!modsLoaded.get(mod))
        {
            modsLoaded.remove(mod);
            modsLoaded.put(mod, true);
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
        if (modsLoaded.containsKey(mod))
        {
            modsLoaded.remove(mod);
            modsLoaded.put(mod, false);
        }
    }
}