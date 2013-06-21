package ccm.nucleum_omnium.proxy;

import lib.com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;
import net.minecraftforge.common.MinecraftForge;
import ccm.nucleum_omnium.handler.events.EventRain;
import ccm.nucleum_omnium.utils.lib.Locations;
import ccm.nucleum_omnium.utils.lib.Properties;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void initCapes() {
        // Link for the file that makes the capes work
        if (Properties.capeHD) {
            DeveloperCapesAPI.getInstance().init(Locations.HD_CAPES);
        } else {
            DeveloperCapesAPI.getInstance().init(Locations.CAPES);
        }
    }
    
    @Override
    public void initEventHandling() {
        MinecraftForge.EVENT_BUS.register(new EventRain());
    }
    
    @Override
    public void initModelHandlers() {}
}