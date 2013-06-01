package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.utils.exeptions.DupeExeption;

public final class Handler
{

    /*
     * *******************************************************************
     * Log Stuff
     * *******************************************************************
     */
    private static HashMap<IMod, Logger> modsLogged = new HashMap<IMod, Logger>();

    /**
     * Initializes the Logger for this Mod.
     */
    public static void initLog(final IMod mod)
    {
        Logger tmp;
        tmp = Logger.getLogger(mod.getId());
        tmp.setParent(FMLLog.getLogger());
        if (!modsLogged.containsKey(mod)){
            modsLogged.put(mod, tmp);
        }else{
            throwDupe(mod);
        }
    }

    /**
     * Logs a Object.
     */
    public static void log(final IMod mod, final Object msg)
    {
        if (modsLogged.containsKey(mod)){
            modsLogged.get(mod).log(Level.INFO, msg.toString());
        }else{
            Logger.getAnonymousLogger().log(Level.INFO, msg.toString());
        }
    }

    /**
     * Logs a Object, and a Throwable.
     */
    public static void log(final IMod mod, final Object msg, final Throwable t)
    {
        if (modsLogged.containsKey(mod)){
            modsLogged.get(mod).log(Level.INFO, msg.toString(), t);
        }else{
            Logger.getAnonymousLogger().log(Level.INFO, msg.toString(), t);
        }
    }

    /**
     * Logs a Object.
     */
    public static void log(final IMod mod, final Level logLevel, final Object msg)
    {
        if (modsLogged.containsKey(mod)){
            modsLogged.get(mod).log(logLevel, msg.toString());
        }else{
            Logger.getAnonymousLogger().log(logLevel, msg.toString());
        }
    }

    /**
     * Logs a Object, and a Throwable.
     */
    public static void log(final IMod mod, final Level logLevel, final Object msg, final Throwable t)
    {
        if (modsLogged.containsKey(mod)){
            modsLogged.get(mod).log(logLevel, msg.toString(), t);
        }else{
            Logger.getAnonymousLogger().log(logLevel, msg.toString(), t);
        }
    }

    /*
     * *******************************************************************
     * Mod State Check
     * *******************************************************************
     */

    private static HashMap<IMod, Boolean> modsLoaded = new HashMap<IMod, Boolean>();

    /**
     * Checks if the Mod has being loaded before and throws a exception. The
     * default response is false. Other wise you should get a Exception.
     * 
     * @return false if the Mod has not being loaded yet.
     */
    public static boolean isModLoaded(final IMod mod)
    {
        if (!modsLoaded.containsKey(mod)){
            modsLoaded.put(mod, false);
        }else{
            throwDupe(mod);
        }
        return modsLoaded.get(mod);
    }

    /**
     * "Loads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void loadMod(final IMod mod)
    {
        if (!modsLoaded.get(mod)){
            modsLoaded.remove(mod);
            modsLoaded.put(mod, true);
        }else{
            throwDupe(mod);
        }
    }

    /**
     * "UnLoads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void unLoadMod(final IMod mod)
    {
        if (modsLoaded.containsKey(mod)){
            modsLoaded.remove(mod);
            modsLoaded.put(mod, false);
        }
    }

    private static void throwDupe(final IMod mod)
    {
        throw new DupeExeption(mod);
    }
}