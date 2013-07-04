package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.utils.exeptions.DupeExeption;
import ccm.nucleum_omnium.utils.exeptions.LNFExeption;
import ccm.nucleum_omnium.utils.lib.Properties;
import cpw.mods.fml.common.FMLLog;

public final class LogHandler extends BaseNIC {

	private static final HashMap<IMod, Logger>	modsLogged	= new HashMap<IMod, Logger>();

	/**
	 * Initializes the Logger for this Mod
	 */
	public static void initLog(final IMod mod) {
		if (!modsLogged.containsKey(mod)) {
			final Logger tmp = Logger.getLogger(mod.getModId());
			tmp.setParent(FMLLog.getLogger());
			modsLogged.put(mod, tmp);
		} else {
			throw new DupeExeption(mod);
		}
	}
	
	/**
	 * Logs a Object.
	 * <p>
	 * This version should only be used in Debugging!
	 */
	public static void log(final Object msg) {
		if (Properties.debug) {
			System.err.println(msg);
		}
	}
	
	/**
	 * Logs a String and an array of Objects
	 * <p>
	 * This version should only be used in Debugging!
	 */
	public static void log(final String msg, final Object... args) {
		if (Properties.debug) {
			System.err.println(String.format(msg, args));
		}
	}

	/**
	 * Logs a Object, and a Throwable.
	 * <p>
	 * This version should only be used in Debugging!
	 */
	public static void log(final Object msg, final Throwable t) {
		log(msg + " \n" + t.toString());
	}

	/**
	 * Logs a {@link Object}, using the Mod's Logger
	 * <p>
	 * Not subject to the Debug configuration!
	 */
	public static void log(final IMod mod, final Object msg) {
		if (modsLogged.containsKey(mod)) {
			modsLogged.get(mod).log(Level.INFO, msg.toString());
		} else {
			throw new LNFExeption(mod);
		}
	}

	/**
	 * Logs a {@link Object}, and a {@link Throwable}, using the Mod's Logger
	 * <p>
	 * Not subject to the Debug configuration!
	 */
	public static void log(final IMod mod, final Object msg, final Throwable t) {
		if (modsLogged.containsKey(mod)) {
			modsLogged.get(mod).log(Level.INFO, msg.toString(), t);
		} else {
			throw new LNFExeption(mod);
		}
	}

	/**
	 * Logs a Object, with a specified {@link Level}, using the Mod's Logger
	 * <p>
	 * Not subject to the Debug configuration!
	 */
	public static void log(final IMod mod, final Level logLevel, final Object msg) {
		if (modsLogged.containsKey(mod)) {
			modsLogged.get(mod).log(logLevel, msg.toString());
		} else {
			throw new LNFExeption(mod);
		}
	}

	/**
	 * Logs a Object, and a Throwable, with a specified {@link Level}, using the Mod's Logger
	 * <p>
	 * Not subject to the Debug configuration!
	 */
	public static void log(final IMod mod, final Level logLevel, final Object msg, final Throwable t) {
		if (modsLogged.containsKey(mod)) {
			modsLogged.get(mod).log(logLevel, msg.toString(), t);
		} else {
			throw new LNFExeption(mod);
		}
	}
}
