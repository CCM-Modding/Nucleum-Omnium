/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.proxy;

import ccm.nucleum.omnium.utils.handler.IconHandler;
import ccm.nucleum.omnium.utils.handler.events.EventRain;
import ccm.nucleum.omnium.utils.lib.Locations;
import ccm.nucleum.omnium.utils.lib.Properties;

import lib.com.jadarstudios.developercapes.DevCapesUtil;

public class ClientProxy extends CommonProxy
{

    @Override
    public void initCapes()
    {
        if (Properties.CAPE_HD)
        {
            // Link for the file that makes the capes work (High Definition Version)
            DevCapesUtil.addFileUrl(Locations.HD_CAPES);
        } else
        {
            // Link for the file that makes the capes work (Normal Version)
            DevCapesUtil.addFileUrl(Locations.CAPES);
        }
    }

    @Override
    public void initEventHandling()
    {
        registerEvent(new EventRain());
        registerEvent(new IconHandler());
    }
}