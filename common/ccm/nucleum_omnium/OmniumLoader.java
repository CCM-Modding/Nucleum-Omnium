package ccm.nucleum_omnium;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.5.2")
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