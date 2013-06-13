package lib.cofh.util.version;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

/**
 * This class allows a mod to easily implement a version update checker. It also
 * contains some version comparison functions which may be used at any point.
 * Instances of this class should be registered with {@link TickHandlerVersion}.
 * 
 * @author King Lemming
 */
public class VersionHandler {

    public static final String MC_VERSION      = "1.5.2";

    boolean                    criticalUpdate;

    boolean                    newVersion;

    boolean                    newMinecraftVersion;

    boolean                    versionCheckComplete;

    String                     latestModVersion;

    String                     latestMCVersion = VersionHandler.MC_VERSION;

    String                     description     = "";

    String                     modName;

    String                     modVersion;

    String                     releaseURL;

    Logger                     modLogger       = FMLLog.getLogger();

    public static boolean beforeTargetVersion(final String version, final String target) {

        try {
            final String[] versionTokens = version.trim().split("\\.");
            final String[] targetTokens = target.trim().split("\\.");

            for (int i = 0; i < versionTokens.length; ++i) {
                if (versionTokens[i].startsWith("a"))
                    // alpha builds ignore updates unless behind by a lot
                    return false;
                if (versionTokens[i].startsWith("b"))
                    if (targetTokens[i].startsWith("b")) {
                        versionTokens[i] = versionTokens[i].substring(1);
                        targetTokens[i] = targetTokens[i].substring(1);
                    } else
                        // if this is a beta and target is not
                        return true;
                if (targetTokens[i].startsWith("a") || targetTokens[i].startsWith("b"))
                    // if target is alpha or beta and this is not
                    return false;
                final int v = Integer.valueOf(versionTokens[i]).intValue();
                final int t = Integer.valueOf(targetTokens[i]).intValue();

                if (v < t)
                    return true;
                else if (v > t)
                    return false;
            }
        } catch (final Throwable t) {
            // pokemon!
        }
        return false;
    }

    public static boolean afterTargetVersion(final String version, final String target) {

        return VersionHandler.beforeTargetVersion(target, version);
    }

    public VersionHandler(final String name, final String version, final String url) {

        this.modName = name;
        this.modVersion = this.latestModVersion = version;
        this.releaseURL = url;
    }

    public VersionHandler(final String name, final String version, final String url,
            final Logger logger) {

        this.modName = name;
        this.modVersion = this.latestModVersion = version;
        this.releaseURL = url;
        this.modLogger = logger;
    }

    public void checkForNewVersion() {

        final Thread versionCheckThread = new VersionCheckThread();
        versionCheckThread.start();
    }

    public String getCurrentVersion() {

        return this.modVersion;
    }

    public String getLatestVersion() {

        return this.latestModVersion;
    }

    public String getLatestMCVersion() {

        return this.latestMCVersion;
    }

    public String getVersionDescription() {

        return this.description;
    }

    public boolean isCriticalUpdate() {

        return this.criticalUpdate;
    }

    public boolean isNewVersionAvailable() {

        return this.newVersion;
    }

    public boolean isMinecraftOutdated() {

        return this.newMinecraftVersion;
    }

    public boolean isVersionCheckComplete() {

        return this.versionCheckComplete;
    }

    private class VersionCheckThread extends Thread {

        @Override
        public void run() {

            try {
                final URL versionFile = new URL(VersionHandler.this.releaseURL);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(
                        versionFile.openStream()));
                VersionHandler.this.latestModVersion = reader.readLine();
                VersionHandler.this.description = reader.readLine();
                VersionHandler.this.criticalUpdate = Boolean.parseBoolean(reader.readLine());
                VersionHandler.this.latestMCVersion = reader.readLine();
                reader.close();

                if (VersionHandler.beforeTargetVersion(VersionHandler.this.modVersion,
                        VersionHandler.this.latestModVersion)) {
                    VersionHandler.this.modLogger.log(Level.INFO, "An updated version of "
                            + VersionHandler.this.modName + " is available: "
                            + VersionHandler.this.latestModVersion + ".");
                    VersionHandler.this.newVersion = true;

                    if (VersionHandler.this.criticalUpdate)
                        VersionHandler.this.modLogger
                                .log(Level.INFO,
                                        "This update has been marked as CRITICAL and will ignore notification suppression.");
                    if (VersionHandler.beforeTargetVersion(VersionHandler.MC_VERSION,
                            VersionHandler.this.latestMCVersion)) {
                        VersionHandler.this.newMinecraftVersion = true;
                        VersionHandler.this.modLogger.log(Level.INFO,
                                "This update is for Minecraft "
                                        + VersionHandler.this.latestMCVersion + ".");
                    }
                }
            } catch (final Exception e) {
                VersionHandler.this.modLogger.log(Level.WARNING,
                        "Version Check Failed: " + e.getMessage());
            }
            VersionHandler.this.versionCheckComplete = true;
        }
    }

}
