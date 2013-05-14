package ccm.nucleum_omnium.stats;

import org.modstats.ModstatInfo;
import org.modstats.Modstats;

import ccm.nucleum_omnium.utils.lib.Archive;

@ModstatInfo(prefix = "",
             name = Archive.MOD_NAME,
             version = Archive.MOD_VERSION)
public class StatsNucleum
{

    public void initStats()
    {
        Modstats.instance().getReporter().registerMod(this);
    }
}
