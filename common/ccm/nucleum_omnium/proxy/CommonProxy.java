package ccm.nucleum_omnium.proxy;

import org.modstats.Modstats;

public class CommonProxy
{

    /**
     * Initializes the Capes Client Side Only
     */
    public void initCapes()
    {}

    /**
     * Initializes the Stats
     */
    public void initStats()
    {
        Modstats.instance().getReporter().registerMod(this);
    }
}