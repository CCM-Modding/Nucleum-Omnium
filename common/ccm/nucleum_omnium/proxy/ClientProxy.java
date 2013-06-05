package ccm.nucleum_omnium.proxy;

import net.minecraftforge.common.MinecraftForge;

import lib.com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;

import ccm.nucleum_omnium.handler.events.EventRain;
import ccm.nucleum_omnium.utils.lib.Locations;

public class ClientProxy extends CommonProxy
{

    @Override
    public void initCapes()
    {
        // Link for the file that makes the capes work
        DeveloperCapesAPI.getInstance().init(Locations.CAPES);
    }

    @Override
    public void initEventHandling()
    {
        MinecraftForge.EVENT_BUS.register(new EventRain());
    }

    @Override
    public void initModelHandlers()
    {}
}