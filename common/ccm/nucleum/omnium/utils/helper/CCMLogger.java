/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import static ccm.nucleum.omnium.utils.lib.Debug.DEBUG;
import static java.lang.String.format;

import java.util.logging.Level;
import java.util.logging.Logger;

import ccm.nucleum.omnium.utils.lib.Properties;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CCMLogger
{
    public static final CCMLogger DEFAULT_LOGGER;

    static
    {
        Logger log = Logger.getLogger("CCM");
        log.setParent(FMLLog.getLogger());
        DEFAULT_LOGGER = new CCMLogger(log);
    }

    /* Class Internals */
    private final Logger logger;

    private CCMLogger(Logger log)
    {
        logger = log;
    }

    /* Public Methods */
    /**
     * Initializes the Logger for this Mod
     */
    public static CCMLogger init(FMLPreInitializationEvent event)
    {
        event.getModLog().setParent(DEFAULT_LOGGER.logger);
        return new CCMLogger(event.getModLog());
    }

    /*
     * All the log functions that are handled by this class internal logger
     */
    public void log(final Level level, final Object message, final Object... data)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n#########################\n");
        sb.append(format(message.toString(), data));
        sb.append("\n#########################\n");
        logger.log(level, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append(format(message.toString(), data));
        sb.append("\nThe Following Exeption has happend inside a catch statement:\n");
        sb.append(e);
        if (e.getCause() != null)
        {
            sb.append("\nWith the following cause:\n");
            sb.append(e.getCause().toString());
        }
        sb.append("\nAnd the Exeption has the stackTrace described below");
        severe(sb.toString());
        e.getStackTrace();
    }
}