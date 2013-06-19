package ccm.nucleum_omnium;

import ccm.nucleum_omnium.configuration.AdvConfiguration;

/**
 * A inteface used to determine which class is actually a Main mod Class. Thus
 * it should only be implemented in the same class as you would extend
 * {@link BaseMod}
 * 
 * @author CaptainShadows
 */
public interface IMod {
    
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
}