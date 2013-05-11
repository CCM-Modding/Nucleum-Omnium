package nucleum_omnium.proxy;

import org.modstats.ModstatInfo;
import org.modstats.Modstats;

@ModstatInfo(prefix = "")
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