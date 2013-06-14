package ccm.nucleum.handler;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import ccm.nucleum.BaseNIClass;
import ccm.nucleum.IMod;
import ccm.nucleum.utils.exeptions.DupeExeption;
import cpw.mods.fml.common.FMLLog;

public final class Handler extends BaseNIClass {
    
    /*
     * *******************************************************************
     * Log Stuff
     * *******************************************************************
     */
    private static HashMap<IMod, Logger> modsLogged = new HashMap<IMod, Logger>();
    
    /**
     * Initializes the Logger for this Mod.
     */
    public static void initLog(final IMod mod) {
        Logger tmp;
        tmp = Logger.getLogger(mod.getModId());
        tmp.setParent(FMLLog.getLogger());
        if (!Handler.modsLogged.containsKey(mod)) {
            Handler.modsLogged.put(mod, tmp);
        } else {
            throw new DupeExeption(mod);
        }
    }
    
    /**
     * Logs a Object. This version is only tto be used in cases where the parent
     * mod is unkown
     */
    public static void log(final Object msg) {
        
        Logger.getAnonymousLogger().log(Level.INFO, msg.toString());
    }
    
    /**
     * Logs a Object, and a Throwable. This version is only tto be used in cases
     * where the parent mod is unkown
     */
    public static void log(final Object msg, final Throwable t) {
        
        Logger.getAnonymousLogger().log(Level.INFO, msg.toString(), t);
    }
    
    /**
     * Logs a Object.
     */
    public static void log(final IMod mod, final Object msg) {
        if (Handler.modsLogged.containsKey(mod)) {
            Handler.modsLogged.get(mod).log(Level.INFO, msg.toString());
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, msg.toString());
        }
    }
    
    /**
     * Logs a Object, and a Throwable.
     */
    public static void log(final IMod mod, final Object msg, final Throwable t) {
        if (Handler.modsLogged.containsKey(mod)) {
            Handler.modsLogged.get(mod).log(Level.INFO, msg.toString(), t);
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, msg.toString(), t);
        }
    }
    
    /**
     * Logs a Object, with a specified {@link Level}
     */
    public static void log(final IMod mod, final Level logLevel, final Object msg) {
        if (Handler.modsLogged.containsKey(mod)) {
            Handler.modsLogged.get(mod).log(logLevel, msg.toString());
        } else {
            Logger.getAnonymousLogger().log(logLevel, msg.toString());
        }
    }
    
    /**
     * Logs a Object, and a Throwable, with a specified {@link Level}
     */
    public static void log(final IMod mod, final Level logLevel, final Object msg, final Throwable t) {
        if (Handler.modsLogged.containsKey(mod)) {
            Handler.modsLogged.get(mod).log(logLevel, msg.toString(), t);
        } else {
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
    public static boolean isModLoaded(final IMod mod) {
        if (!Handler.modsLoaded.containsKey(mod)) {
            Handler.modsLoaded.put(mod, false);
        } else {
            throw new DupeExeption(mod);
        }
        return Handler.modsLoaded.get(mod);
    }
    
    /**
     * "Loads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void loadMod(final IMod mod) {
        if (!Handler.modsLoaded.get(mod)) {
            Handler.modsLoaded.remove(mod);
            Handler.modsLoaded.put(mod, true);
        } else {
            throw new DupeExeption(mod);
        }
    }
    
    /**
     * "UnLoads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void unLoadMod(final IMod mod) {
        if (Handler.modsLoaded.containsKey(mod)) {
            Handler.modsLoaded.remove(mod);
            Handler.modsLoaded.put(mod, false);
        }
    }
}