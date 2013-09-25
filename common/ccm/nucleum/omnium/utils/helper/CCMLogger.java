/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import static ccm.nucleum.omnium.utils.lib.Debug.DEBUG;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import ccm.nucleum.omnium.utils.lib.Properties;

public class CCMLogger
{
    public static final CCMLogger DEFAULT_LOGGER = new CCMLogger(Logger.getLogger("CCM"));

    /* Class Internals */
    private Logger logger;

    private CCMLogger(Logger log)
    {
        logger = log;
    }

    /* Public Methods */
    /**
     * Initializes the Logger for this Mod
     */
    public static CCMLogger initLogger(FMLPreInitializationEvent event)
    {
        return new CCMLogger(event.getModLog());
    }

    /*
     * All the log functions that are handled by this class internal logger
     */
    public void log(final Level level, final Object message, final Object... data)
    {
        logger.log(level, String.format(message.toString(), data));
    }

    public void finest(final Object message, final Object... data)
    {
        log(Level.FINEST, message, data);
    }

    public void finer(final Object message, final Object... data)
    {
        log(Level.FINER, message, data);
    }

    public void fine(final Object message, final Object... data)
    {
        log(Level.FINE, message, data);
    }

    public void config(final Object message, final Object... data)
    {
        log(Level.CONFIG, message, data);
    }

    public void info(final Object message, final Object... data)
    {
        log(Level.INFO, message, data);
    }

    public void debug(final Object message, final Object... data)
    {
        if (Properties.DEBUG)
        {
            log(DEBUG, message, data);
        }
    }

    public void warning(final Object message, final Object... data)
    {
        log(Level.WARNING, message, data);
    }

    public void severe(final Object message, final Object... data)
    {
        log(Level.SEVERE, message, data);
    }

    public void printCatch(final Exception e, final Object message, final Object... data)
    {
        severe(message, data);
        if (e.getCause() != null)
        {
            severe("\n And Cause: %s", e.getCause());
        }
        e.printStackTrace();
    }
}