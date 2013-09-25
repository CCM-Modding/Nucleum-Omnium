/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.mods;

import ccm.nucleum.omnium.IMod;

/**
 * Abstract class extended by all classes that contain code to add support for other mods.
 * <p>
 * Please override the toString method if you want a custom error message.
 * 
 * @author Captain_Shadows
 */
public abstract class IModHandler
{

    /**
     * @return The IMod that this IModHandler belongs to
     */
    public abstract IMod getMod();

    /**
     * @return The Name of the mod that it is checking if it's loaded. Ex: Buildcraft
     */
    public abstract String getAPIModName();

    /** What to do if the Mod is found to be loaded */
    public abstract void init();

    @Override
    public String toString()
    {
        return String.format("A CCM Mod has failed to load it's compatibility with %s, pleace inform the CCM Team", getAPIModName());
    }
}