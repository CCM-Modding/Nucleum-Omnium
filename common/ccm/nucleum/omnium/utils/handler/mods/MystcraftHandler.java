/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.mods;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.utils.lib.Properties;

/**
 * Mystcraft's Handler
 * 
 * @author Captain_Shadows
 */
public final class MystcraftHandler extends IModHandler
{

    @Override
    public IMod getMod()
    {
        return NucleumOmnium.instance;
    }

    @Override
    public String getModName()
    {
        return "Mystcraft";
    }

    @Override
    public void init()
    {
        Properties.mystLoaded = true;
    }
}