package ccm.nucleum;

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
     * @return The Mod's Version
     */
    public String getVersion();
}