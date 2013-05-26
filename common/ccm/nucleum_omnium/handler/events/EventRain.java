package ccm.nucleum_omnium.handler.events;

import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class EventRain
{

    @ForgeSubscribe
    public void rainStopper(final PlaySoundEvent event)
    {
        if ((event != null) && (event.source != null) && (event.source.soundName.startsWith("ambient/weather/rain"))){
            event.result = null;
        }
    }
}