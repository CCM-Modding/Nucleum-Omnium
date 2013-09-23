/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import static ccm.nucleum.omnium.utils.lib.Debug.DEBUG;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.utils.exeptions.DupeExeption;
import ccm.nucleum.omnium.utils.exeptions.LNFExeption;
import ccm.nucleum.omnium.utils.lib.Properties;

public final class LogHandler
{
    /** Class Internals */
    private Logger logger;
    private final Map<IMod, Logger> modsLogged;

    private static final LogHandler instance = new LogHandler();

    private LogHandler()
    {
        modsLogged = new HashMap<IMod, Logger>();
        logger = Logger.getLogger("CCM");
        logger.setParent(FMLLog.getLogger());
    }

    public static LogHandler instance()
    {
        return instance;
    }

    private static Map<IMod, Logger> getLoggers()
    {
        return instance().modsLogged;
    }

    /** Public Methods */

    /**
     * Initializes the Logger for this Mod
     */
    public static void init(final IMod mod)
    {
        if (!getLoggers().containsKey(mod))
        {
            Logger log = Logger.getLogger(mod.getModId());
            log.setParent(instance().logger);
            getLoggers().put(mod, log);
        } else
        {
            throw new DupeExeption(mod);
        }
    }

    /** Returns the logger for that mod */
    public static Logger getLog(final IMod mod)
    {
        if (getLoggers().containsKey(mod))
        {
            return getLoggers().get(mod);
        } else
        {
            throw new LNFExeption(mod);
        }
    }

    /* 
     *  All the log functions that are handled by the Mod's Logger
     */

    public static void log(final IMod mod, final Level level, final Object message, final Object... data)
    {
        getLog(mod).log(level, String.format(message.toString(), data));
    }

    public static void finest(final IMod mod, final Object message, final Object... data)
    {
        log(mod, Level.FINEST, message, data);
    }

    public static void finer(final IMod mod, final Object message, final Object... data)
    {
        log(mod, Level.FINER, message, data);
    }

    public static void fine(final IMod mod, final Object message, final Object... data)
    {
        log(mod, Level.FINE, message, data);
    }

    public static void config(final IMod mod, final Object message, final Object... data)
    {
        log(mod, Level.CONFIG, message, data);
    }

    public static void info(final IMod mod, final Object message, final Object... data)
    {
        log(mod, Level.INFO, message, data);
    }

    public static void debug(final IMod mod, final Object message, final Object... data)
    {
        if (Properties.DEBUG)
        {
            log(mod, DEBUG, message, data);
        }
    }

    public static void warning(final IMod mod, final Object message, final Object... data)
    {
        log(mod, Level.WARNING, message, data);
    }

    public static void severe(final IMod mod, final Object message, final Object... data)
    {
        log(mod, Level.SEVERE, message, data);
    }

    public static void printCatch(final IMod mod, final Exception e, final Object message, final Object... data)
    {
        LogHandler.severe(mod, message, data);
        if (e.getCause() != null)
        {
            LogHandler.severe(mod, "\n And Cause: %s", e.getCause());
        }
        e.printStackTrace();
    }

    /* 
     *  All the log functions that are handled by this class internal logger
     */
    
    public static void log(final Level level, final Object message, final Object... data)
    {
        instance().logger.log(level, String.format(message.toString(), data));
    }

    public static void finest(final Object message, final Object... data)
    {
        log(Level.FINEST, message, data);
    }

    public static void finer(final Object message, final Object... data)
    {
        log(Level.FINER, message, data);
    }

    public static void fine(final Object message, final Object... data)
    {
        log(Level.FINE, message, data);
    }

    public static void config(final Object message, final Object... data)
    {
        log(Level.CONFIG, message, data);
    }

    public static void info(final Object message, final Object... data)
    {
        log(Level.INFO, message, data);
    }

    public static void debug(final Object message, final Object... data)
    {
        if (Properties.DEBUG)
        {
            log(DEBUG, message, data);
        }
    }

    public static void warning(final Object message, final Object... data)
    {
        log(Level.WARNING, message, data);
    }

    public static void severe(final Object message, final Object... data)
    {
        log(Level.SEVERE, message, data);
    }

    public static void printCatch(final Exception e, final Object message, final Object... data)
    {
        LogHandler.severe(message, data);
        if (e.getCause() != null)
        {
            LogHandler.severe("\n And Cause: %s", e.getCause());
        }
        e.printStackTrace();
    }
}