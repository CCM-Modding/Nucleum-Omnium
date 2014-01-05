/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.proxy;

import lib.com.jadarstudios.developercapes.DevCapesUtil;
import ccm.nucleum.omnium.utils.handler.events.EventRain;
import ccm.nucleum.omnium.utils.lib.Locations;
import ccm.nucleum.omnium.utils.lib.Properties;

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
    public void preInit()
    {
        super.preInit();
    }
    
    @Override
    public void init()
    {
        super.init();
        registerEvent(new EventRain());
    }

    @Override
    public void postInit()
    {
        super.postInit();
    }
}