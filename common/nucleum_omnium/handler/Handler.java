package nucleum_omnium.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import nucleum_omnium.utils.exeptions.DupeExeption;
import nucleum_omnium.utils.lib.Archive;
import cpw.mods.fml.common.FMLLog;

public final class Handler
{

    private boolean       IsLoaded = false;

    /*
     * *******************************************************************
     * Log Stuff
     * *******************************************************************
     */
    private static Logger logger;

    /**
     * Initializes the Logger for this Mod.
     */
    public void initLog(String modID)
    {
        logger = Logger.getLogger(modID);
        logger.setParent(FMLLog.getLogger());
    }

    /*
     * *******************************************************************
     * Mod State Check
     * *******************************************************************
     */
    /**
     * Checks if the Mod has being loaded before and throws a exception. The
     * default response is false. Other wise you should get a Exception.
     * 
     * @return false if the Mod has not being loaded yet.
     */
    public boolean isModLoaded()
    {
        if (IsLoaded){
            throw new DupeExeption("Why did you install my Mod twice? Remove the second "
                                           + Archive.MOD_NAME
                                           + "-Universal-"
                                           + Archive.MOD_VERSION
                                           + ".jar out of your mods-Folder, you need only one of them. And another Question: Why the Hax did Forge not detect that before me?",
                                   Archive.MOD_NAME);
        }else{
            return false;
        }
    }

    /**
     * "Loads" the Mod. In respect to {@link isModLoaded()}
     */
    public void LoadMod()
    {
        IsLoaded = true;
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

    /**
     * "UnLoads" the Mod. In respect to {@link isModLoaded()}
     */
    public void UnLoadMod()
    {
        IsLoaded = false;
    }
}