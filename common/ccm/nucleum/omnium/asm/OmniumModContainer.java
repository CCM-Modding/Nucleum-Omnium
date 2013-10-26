/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.asm;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.versioning.ArtifactVersion;

public class OmniumModContainer extends DummyModContainer
{
    private ModMetadata md = new ModMetadata();
    private ArtifactVersion processedVersion;
    private final String label = "OmniumCore";

    public OmniumModContainer()
    {
        md.modId = label;
        md.name = "CCM Core";
        md.version = "TEST";
    }

    @Override
    public void bindMetadata(final MetadataCollection mc)
    {
        md = mc.getMetadataForId(getModId(), null);
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
    public String getVersion()
    {
        return md.version;
    }

    @Override
    public boolean isImmutable()
    {
        return true;
    }
}