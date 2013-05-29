package ccm.nucleum_omnium;

public interface IMod
{

    /**
     * @return The Mod's ID
     */
    public String getId();

    /**
     * @return The Mod's Name
     */
    public String getName();

    /**
     * @return The Assinged Prefix
     */
    public String getPrefix();

    /**
     * @return The Mod's Version
     */
    public String getVersion();
}