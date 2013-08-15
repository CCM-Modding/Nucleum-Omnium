/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@Name("OmniumCore")
@TransformerExclusions(value = "ccm.nucleum_omnium.asm")
public class OmniumLoader implements IFMLLoadingPlugin
{

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { "ccm.nucleum_omnium.asm.OmniumAT" };
    }

    @Override
    public String[] getLibraryRequestClass()
    {
        return null;
    }

    @Override
    public String getModContainerClass()
    {
        return "ccm.nucleum_omnium.asm.OmniumModContainer";
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