package ccm.nucleum_omnium.proxy;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;
import ccm.nucleum_omnium.client.model.tcn.TechneModelLoader;
import ccm.nucleum_omnium.handler.events.EventRain;
import ccm.nucleum_omnium.utils.lib.Locations;

import com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;

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
    {
        AdvancedModelLoader.registerModelHandler(new TechneModelLoader());
    }
}