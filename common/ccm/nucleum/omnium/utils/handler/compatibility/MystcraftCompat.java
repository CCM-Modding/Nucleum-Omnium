/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.compatibility;

import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.utils.lib.Properties;

/**
 * Mystcraft's Handler
 * 
 * @author Captain_Shadows
 */
public final class MystcraftCompat implements ICompatibility
{
    @Override
    public void init()
    {
        Properties.MYSTCARFT_LOADED = true;
        NucleumOmnium.instance.logger().debug("MYSTCRAFT IS LOADED");
    }
}