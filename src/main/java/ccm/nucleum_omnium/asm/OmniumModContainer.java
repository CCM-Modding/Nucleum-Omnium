/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.asm;

import java.io.File;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;

public class OmniumModContainer implements ModContainer
{

    private ModMetadata     md    = new ModMetadata();
    private ArtifactVersion processedVersion;
    private final String    label = "OmniumCore";

    public OmniumModContainer()
    {
        md.modId = "OmniumCore";
        md.name = "CCM Core";
        md.version = "${version}";
        md.authorList = Arrays.asList("captain_shadows");
        md.url = "https://github.com/CCM-Modding/Nucleum-Omnium";
        md.updateUrl = "http://driesgames.game-server.cc:8080/view/CCM/job/Nucleum-Omnium/";
        md.credits = "Made By Captain Shadows, ClayCorp, Morton, and The rest of the CCM Modding Team, with special help from AbrarSyed(and the rest of the FE team, including Dries007), RebelKeithy, and a bunch of other people, Also a big thanks to The COFH Team for their Library, Jadar for Developer Capes API, and last but not least Shedar for ModStats. Also to the Forge and MCP crew, who without them no Minecraft mods would be possible";
        md.logoFile = "/assets/nucleum-omnium/textures/logo.png";
        md.description = "Core functionality for all CCM Mods";
    }

    @Override
    public VersionRange acceptableMinecraftVersionRange()
    {
        return Loader.instance().getMinecraftModContainer().getStaticVersionRange();
    }

    @Override
    public void bindMetadata(final MetadataCollection mc)
    {
        md = mc.getMetadataForId(getModId(), null);
    }

    @Override
    public Map<String, String> getCustomModProperties()
    {
        return EMPTY_PROPERTIES;
    }

    @Override
    public Class<?> getCustomResourcePackClass()
    {
        return null;
    }

    @Override
    public List<ArtifactVersion> getDependants()
    {
        return Collections.emptyList();
    }

    @Override
    public List<ArtifactVersion> getDependencies()
    {
        return Collections.emptyList();
    }

    @Override
    public String getDisplayVersion()
    {
        return md.version;
    }

    @Override
    public ModMetadata getMetadata()
    {
        return md;
    }

    @Override
    public Object getMod()
    {
        return this;
    }

    @Override
    public String getModId()
    {
        return md.modId;
    }

    @Override
    public String getName()
    {
        return md.name;
    }

    @Override
    public ArtifactVersion getProcessedVersion()
    {
        if (processedVersion == null)
        {
            processedVersion = new DefaultArtifactVersion(getModId(), getVersion());
        }
        return processedVersion;
    }

    @Override
    public Set<ArtifactVersion> getRequirements()
    {
        return Collections.emptySet();
    }

    @Override
    public Certificate getSigningCertificate()
    {
        return null;
    }

    @Override
    public String getSortingRules()
    {
        return "";
    }

    @Override
    public File getSource()
    {
        return null;
    }

    @Override
    public String getVersion()
    {
        return md.version;
    }

    @Override
    public boolean isImmutable()
    {
        return true;
    }

    @Override
    public boolean isNetworkMod()
    {
        return false;
    }

    @Override
    public boolean matches(final Object mod)
    {
        return false;
    }

    @Override
    public boolean registerBus(final EventBus bus, final LoadController controller)
    {
        return false;
    }

    @Override
    public void setEnabledState(final boolean enabled)
    {}

    @Override
    public String toString()
    {
        return md != null ? getModId() : "Dummy Container (" + label + ") @" + System.identityHashCode(this);
    }
}