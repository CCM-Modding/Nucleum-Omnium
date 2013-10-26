/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.proxy;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

import ccm.nucleum.omnium.utils.handler.events.EventBoneMeal;

public class CommonProxy
{

    /**
     * Initializes the Capes Client Side Only. Original code that adds capes is found in this Github repository: {@link http://www.github.com/jadar/DeveloperCapesAPI}
     */
    public void initCapes()
    {}

    /**
     * Initializes All the events
     */
    public void initEventHandling()
    {
        registerEvent(new EventBoneMeal());
    }

    void registerEvent(final Object target)
    {
        EVENT_BUS.register(target);
    }
}