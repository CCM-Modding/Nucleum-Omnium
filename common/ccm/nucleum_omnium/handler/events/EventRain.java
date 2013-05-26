package ccm.nucleum_omnium.handler.events;

import ccm.nucleum_omnium.utils.lib.Properties;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class EventRain
{

    @ForgeSubscribe
    public void rainStopper(final PlaySoundEvent event)
    {
        if (Properties.rain){
            if ((event != null) && (event.source != null) && (event.source.soundName.startsWith("ambient/weather/rain"))){
                event.result = null;
            }
        }
    }
}