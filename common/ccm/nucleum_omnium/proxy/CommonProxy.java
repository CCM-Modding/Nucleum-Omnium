/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.proxy;

import net.minecraftforge.common.MinecraftForge;

import ccm.nucleum_omnium.handler.events.EventBoneMeal;

public class CommonProxy
{

    /**
     * Initializes the Capes Client Side Only. Original code that adds capes is found in this Github
     * repository: {@link http://www.github.com/jadar/DeveloperCapesAPI}
     */
    public void initCapes()
    {}

    /**
     * Initializes All the events
     */
    public void initEventHandling()
    {
        MinecraftForge.EVENT_BUS.register(new EventBoneMeal());
    }
}