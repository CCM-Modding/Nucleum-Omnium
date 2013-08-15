/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium;

import ccm.nucleum.omnium.configuration.AdvConfiguration;

/**
 * A interface used to determine which class is actually a Main Mod class.
 * <p>
 * Thus it should only be implemented in the same class as you would annotate {@code @Mod}
 * 
 * @author CaptainShadows
 */
public interface IMod
{
    /**
     * @return The Mod's ID
     */
    public String getModId();

    /**
     * @return The Mod's Name
     */
    public String getName();

    /**
     * @return The AdvConfiguration file that belongs to the mod
     */
    public AdvConfiguration getConfigFile();

    /**
     * @return The AdvConfiguration file that belongs to the mod
     */
    public void setConfigFile(AdvConfiguration conf);
}