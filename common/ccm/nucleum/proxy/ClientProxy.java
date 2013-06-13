package ccm.nucleum.proxy;

import lib.com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;
import net.minecraftforge.common.MinecraftForge;
import ccm.nucleum.handler.events.EventRain;
import ccm.nucleum.utils.lib.Locations;

public class ClientProxy extends CommonProxy {

    @Override
    public void initCapes() {
        // Link for the file that makes the capes work
        DeveloperCapesAPI.getInstance().init(Locations.CAPES);
    }

    @Override
    public void initEventHandling() {
        MinecraftForge.EVENT_BUS.register(new EventRain());
    }

    @Override
    public void initModelHandlers() {
    }
}