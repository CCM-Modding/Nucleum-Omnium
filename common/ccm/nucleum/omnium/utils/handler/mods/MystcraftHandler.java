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
public final class MystcraftHandler implements IModHandler
{
    @Override
    public void init()
    {
        Properties.MYSTCARFT_LOADED = true;
        NucleumOmnium.instance.getLogger().debug("MYSTCRAFT IS LOADED");
    }
}