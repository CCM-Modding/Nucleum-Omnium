/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.proxy;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;
import static net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS;
import ccm.nucleum.omnium.world.WorldGenHandler;

public class CommonProxy
{
    /**
     * Initializes the Capes Client Side Only. Original code that adds capes is found in this Github repository:
     * http://www.github.com/jadar/DeveloperCapesAPI
     */
    public void initCapes()
    {}

    public void preInit()
    {
        registerEvent(WorldGenHandler.instance);
        ORE_GEN_BUS.register(WorldGenHandler.instance);
    }

    public void init()
    {}

    public void postInit()
    {}

    protected void registerEvent(final Object target)
    {
        EVENT_BUS.register(target);
    }
}