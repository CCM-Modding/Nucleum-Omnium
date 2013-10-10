/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.mods;

import ccm.nucleum.omnium.IMod;

/**
 * Runnable Interface implemented by all classes that contain code to add support for other mods.
 * <p>
 * 
 * ANY INSTANCES OF THIS INTERFACE NEED A Default no arg constructor!!!
 * 
 * @author Captain_Shadows
 */
public interface IModHandler
{
    /** What to do if the Mod is found to be loaded */
    public void init();
}
