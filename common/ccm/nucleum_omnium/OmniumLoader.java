package ccm.nucleum_omnium;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@MCVersion(value = "1.5.2")
@TransformerExclusions(value =
{ "ccm.nucleum_network", "ccm.nucleum_omnium", "ccm.nucleum_world" })
public class OmniumLoader implements IFMLLoadingPlugin
{

    @Override
    public String[] getLibraryRequestClass()
    {
        return null;
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]
        { "ccm.nucleum_omnium.at.OmniumAT" };
    }

    @Override
    public String getModContainerClass()
    {
        return "ccm.nucleum_omnium.NucleumOmnium";
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(final Map<String, Object> data)
    {}
}