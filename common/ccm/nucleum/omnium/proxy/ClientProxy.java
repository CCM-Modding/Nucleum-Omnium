/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.proxy;

import net.minecraftforge.common.MinecraftForge;

import ccm.nucleum.omnium.utils.handler.events.EventRain;
import ccm.nucleum.omnium.utils.lib.Locations;
import ccm.nucleum.omnium.utils.lib.Properties;

import lib.com.jadarstudios.developercapes.DevCapesUtil;

public class ClientProxy extends CommonProxy
{

    @Override
    public void initCapes()
    {
        if (Properties.capeHD)
        {
            // Link for the file that makes the capes work (High Def Version)
            DevCapesUtil.getInstance().addFileUrl(Locations.HD_CAPES);
        } else
        {
            // Link for the file that makes the capes work (Normal Version)
            DevCapesUtil.getInstance().addFileUrl(Locations.CAPES);
        }
    }

    @Override
    public void initEventHandling()
    {
        MinecraftForge.EVENT_BUS.register(new EventRain());
    }
}