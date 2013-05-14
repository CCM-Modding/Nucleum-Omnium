package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.utils.exeptions.DupeExeption;
import cpw.mods.fml.common.FMLLog;

public final class Handler
{
    /*
     * *******************************************************************
     * Log Stuff
     * *******************************************************************
     */
    private static Logger                 logger;

    /**
     * Initializes the Logger for this Mod.
     */
    public static void initLog(IMod mod)
    {
        logger = Logger.getLogger(mod.getModId());
        logger.setParent(FMLLog.getLogger());
    }

    /**
     * Logs a Object.
     */
    public static void log(final Level logLevel, final Object message)
    {
        if (logger != null){
            logger.log(logLevel, message.toString());
        }else{
            Logger.getAnonymousLogger().log(logLevel, message.toString());
        }
    }

    /**
     * Logs a Object, and a Throwable.
     */
    public static void log(final Level level, final String msg, final Throwable t)
    {
        if (logger != null){
            logger.log(level, msg, t);
        }else{
            Logger.getAnonymousLogger().log(level, msg, t);
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
    public static boolean isModLoaded(IMod mod)
    {
        if (!modsLoaded.containsKey(mod)){
            modsLoaded.put(mod, false);
        }else{
            throwError(mod);
        }
        return modsLoaded.get(mod);
    }

    /**
     * "Loads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void loadMod(IMod mod)
    {
        if (!modsLoaded.get(mod)){
            modsLoaded.remove(mod);
            modsLoaded.put(mod, true);
        }else{
            throwError(mod);
        }
    }

    /**
     * "UnLoads" the Mod. In respect to {@link isModLoaded()}
     */
    public static void unLoadMod(IMod mod)
    {
        if (modsLoaded.containsKey(mod)){
            modsLoaded.remove(mod);
            modsLoaded.put(mod, false);
        }
    }

    private static void throwError(IMod mod)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Why did you install my Mod twice? Remove the second ");
        sb.append(mod.getModName());
        sb.append("-Universal-");
        sb.append(mod.getModVersion());
        sb.append(".jar out of your mods-Folder, you need only one of them. And another Question: Why the Hax did Forge not detect that before me?");
        sb.append(mod.getModName());
        throw new DupeExeption(mod, sb.toString());
    }
}