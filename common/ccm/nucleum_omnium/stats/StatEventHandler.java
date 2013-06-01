package ccm.nucleum_omnium.stats;

import java.util.LinkedList;
import java.util.List;

import org.modstats.ModVersionData;
import org.modstats.ModsUpdateEvent;

import net.minecraftforge.event.ForgeSubscribe;

import ccm.nucleum_omnium.IMod;

public class StatEventHandler
{

    private static List<ModVersionData> updates = new LinkedList<ModVersionData>();

    @ForgeSubscribe
    public void onModsUpdate(final ModsUpdateEvent event)
    {
        for (final ModVersionData data : updates){
            event.add(data);
        }
    }

    public static void addModToList(final IMod mod)
    {
        updates.add(new ModVersionData(mod.getPrefix(), mod.getName(), mod.getVersion()));
    }
}