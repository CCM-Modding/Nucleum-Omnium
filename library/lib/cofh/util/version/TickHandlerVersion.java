package lib.cofh.util.version;

import java.util.ArrayList;
import java.util.EnumSet;

import lib.cofh.util.DyeColors;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

/**
 * Handles chat notifications to players regarding mod updates. Please note the initialization
 * structure.
 * 
 * @author King Lemming
 */
public class TickHandlerVersion implements IScheduledTickHandler {

	public static TickHandlerVersion			instance		= new TickHandlerVersion();

	private static ArrayList<VersionHandler>	modVersionInfo	= new ArrayList<VersionHandler>();
	private static boolean						initialized;
	private static boolean						sent;
	private static int							modIndex		= 0;

	/**
	 * This should only be called when the TickHandlerVersion instance is registered as a Tick
	 * Handler.
	 */
	public static boolean initialize() {

		if (initialized) {
			return false;
		}
		initialized = true;
		return true;
	}

	/**
	 * This should be checked by all mods making use of this class. If this returns true, then the
	 * tick handler should NOT be registered again.
	 */
	public static boolean isInitialized() {

		return initialized;
	}

	public static boolean registerModVersionInfo(final VersionHandler info) {

		if (modVersionInfo.contains(info)) {
			return false;
		}
		modVersionInfo.add(info);
		return true;
	}

	@Override
	public void tickStart(final EnumSet<TickType> type, final Object... tickData) {

		if (sent) {
			return;
		}
		if (modIndex >= modVersionInfo.size()) {
			sent = true;
			return;
		}
		final VersionHandler anInfo = modVersionInfo.get(modIndex);

		if (anInfo.isNewVersionAvailable()) {
			final EntityPlayer player = (EntityPlayer) tickData[0];
			player.sendChatToPlayer(DyeColors.YELLOW + "["
									+ anInfo.modName
									+ "] "
									+ DyeColors.WHITE
									+ "A new version is available: "
									+ DyeColors.LIGHT_BLUE
									+ anInfo.getLatestVersion());
			player.sendChatToPlayer(DyeColors.LIGHT_GRAY + anInfo.getVersionDescription());
		}
		modIndex++;
	}

	@Override
	public void tickEnd(final EnumSet<TickType> type, final Object... tickData) {

	}

	@Override
	public EnumSet<TickType> ticks() {

		if (TickHandlerVersion.sent) {
			return EnumSet.noneOf(TickType.class);
		}
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {

		return "lib.cofh.version";
	}

	@Override
	public int nextTickSpacing() {

		if (!sent) {
			return 200;
		}
		return 72000;
	}
}