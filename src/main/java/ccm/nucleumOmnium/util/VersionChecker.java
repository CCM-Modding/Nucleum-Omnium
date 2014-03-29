package ccm.nucleumOmnium.util;

import ccm.nucleumOmnium.NucleumOmnium;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.versioning.ComparableVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import ccm.libs.org.apache.maven.artifact.repository.metadata.Metadata;
import ccm.libs.org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Reader;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class VersionChecker implements Runnable
{
    private static final String MCVERSION = new CallableMinecraftVersion(null).minecraftVersion();
    private static      VersionChecker INSTANCE = new VersionChecker();
    private HashSet<ModContainer> mods = new HashSet<ModContainer>();
    private HashMap<ModContainer, String> modsToUpdate = new HashMap<ModContainer, String>();

    private VersionChecker() {}

    public static void addMod(ModContainer modContainer)
    {
        if (modContainer == null) return; // Just a precaution...
        INSTANCE.mods.add(modContainer);
    }

    public static void doVersionCheck()
    {
        new Thread(INSTANCE, NOConstants.NO_MODID + "-VersionCheckerThread").start();
    }

    @Override
    public void run()
    {
        for (ModContainer modContainer : mods) check(modContainer);
    }

    private void check(ModContainer modContainer)
    {
        try
        {
            ComparableVersion current = new ComparableVersion(modContainer.getVersion());
            URL url = new URL(NOConstants.URL_MAVEN + modContainer.getMod().getClass().getCanonicalName().replace('.', '/') + "/maven-metadata.xml");
            MetadataXpp3Reader metadataXpp3Reader = new MetadataXpp3Reader();
            Metadata metadata = metadataXpp3Reader.read(url.openStream());

            ComparableVersion latest = null;
            for (String versionString : metadata.getVersioning().getVersions())
            {
                if (versionString.contains(MCVERSION + "-"))
                {
                    versionString = versionString.replace(MCVERSION + "-", "");
                    ComparableVersion version = new ComparableVersion(versionString);
                    if (latest == null || version.compareTo(latest) > 0) latest = version;
                }
            }

            if (latest != null && latest.compareTo(current) > 0) modsToUpdate.put(modContainer, latest.toString());
        }
        catch (Exception ignored)
        {
            // We don't really care, a lot of things can go wrong...
            NucleumOmnium.getLogger().info("Something went wrong when version checking " + modContainer.getModId() + ", check manually.");
        }
    }

    public static void sendMessage()
    {
        if (INSTANCE.modsToUpdate.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        sb.append("NucleumOmnium found out of date mods:\n");

        for (Map.Entry<ModContainer, String> entry : INSTANCE.modsToUpdate.entrySet())
        {
            sb.append(entry.getKey().getModId()).append(": ").append(entry.getKey().getVersion()).append(" => ").append(entry.getValue()).append("\n");
        }
        sb.append("Please update!");

        Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText(sb.toString()).setColor(EnumChatFormatting.AQUA));
        INSTANCE.modsToUpdate.clear(); // Only send the message once.
    }
}
