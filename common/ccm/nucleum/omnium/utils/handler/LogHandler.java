/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.utils.exeptions.DupeExeption;
import ccm.nucleum.omnium.utils.exeptions.LNFExeption;
import ccm.nucleum.omnium.utils.lib.Debug;
import ccm.nucleum.omnium.utils.lib.Properties;

public final class LogHandler extends BaseNIC
{

    private static final HashMap<IMod, Logger> modsLogged = new HashMap<IMod, Logger>();

    public final static Debug DEBUG = new Debug();

    public static void config(final IMod mod, final Object message, final Object... data)
    {

        log(mod, Level.CONFIG, message, data);
    }

    public static void debug(final Object message, final Object... data)
    {
        if (Properties.DEBUG)
        {
            System.out.println("[CCM] [" + DEBUG + "] " + String.format(message.toString(), data));
        }
    }

    public static void fine(final IMod mod, final Object message, final Object... data)
    {

        log(mod, Level.FINE, message, data);
    }

    public static void finer(final IMod mod, final Object message, final Object... data)
    {

        log(mod, Level.FINER, message, data);
    }

    public static void finest(final IMod mod, final Object message, final Object... data)
    {

        log(mod, Level.FINEST, message, data);
    }

    /** Returns the logger for that mod */
    public static Logger getLog(final IMod mod)
    {
        if (modsLogged.containsKey(mod))
        {
            return modsLogged.get(mod);
        } else
        {
            throw new LNFExeption(mod);
        }
    }

    public static void info(final IMod mod, final Object message, final Object... data)
    {

        log(mod, Level.INFO, message, data);
    }

    /**
     * Initializes the Logger for this Mod
     */
    public static void init(final IMod mod)
    {
        if (!modsLogged.containsKey(mod))
        {
            final Logger tmp = Logger.getLogger(mod.getModId());
            tmp.setParent(FMLLog.getLogger());
            modsLogged.put(mod, tmp);
        } else
        {
            throw new DupeExeption(mod);
        }
    }

    public static void log(final IMod mod, final Level level, final Object message, final Object... data)
    {

        getLog(mod).log(level, String.format(message.toString(), data));
    }

    public static void severe(final IMod mod, final Object message, final Object... data)
    {

        log(mod, Level.SEVERE, message, data);
    }

    public static void warning(final IMod mod, final Object message, final Object... data)
    {

        log(mod, Level.WARNING, message, data);
    }
}