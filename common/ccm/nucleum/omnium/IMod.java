/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import ccm.nucleum.omnium.configuration.AdvConfiguration;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

/**
 * A interface used to determine which class is actually a Main Mod class.
 * <p>
 * Thus it should only be implemented in the same class as you would annotate {@code @Mod}
 */
public interface IMod
{
    /* MOD RELATED METHODS */
    /**
     * @return The Mod's ID
     */
    public String getModId();

    /**
     * @return The Mod's Name
     */
    public String getName();

    /* CONFIGURATION RELATED METHODS */
    /**
     * @return The AdvConfiguration file that belongs to the Mod
     */
    public AdvConfiguration getConfiguration();

    /**
     * @param conf
     *            The AdvConfiguration file that belongs to the Mod
     */
    public void setConfiguration(AdvConfiguration conf);

    /** Initializes the AdvConfiguration file that belongs to the Mod */
    public void initConfig(final FMLPreInitializationEvent evt);

    /* LOGGER RELATED METHODS */
    /**
     * @return The CCMLogger that belongs to the Mod
     */
    public CCMLogger getLogger();

    /**
     * @param log
     *            The CCMLogger that belongs to the Mod
     */
    public void setLogger(CCMLogger log);
}