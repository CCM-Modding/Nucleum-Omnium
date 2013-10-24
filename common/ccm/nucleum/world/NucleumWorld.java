/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.world;

import static ccm.nucleum.world.utils.lib.Archive.MOD_ID;
import static ccm.nucleum.world.utils.lib.Archive.MOD_NAME;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;
import static net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import ccm.nucleum.omnium.CCMMod;
import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.utils.handler.ModLoadingHandler;
import ccm.nucleum.world.generator.WorldGenHandler;
import ccm.nucleum.world.utils.TickHandlerWorld;
import ccm.nucleum.world.utils.lib.Properties;

@Mod(modid = MOD_ID, name = MOD_NAME, useMetadata = true, dependencies = "required-after:nucleum_omnium")
public class NucleumWorld extends CCMMod implements IMod
{

    @Instance(MOD_ID)
    public static NucleumWorld instance;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent evt)
    {
        ModLoadingHandler.loadMod(this, evt, null);

        GameRegistry.registerWorldGenerator(WorldGenHandler.instance);

        EVENT_BUS.register(WorldGenHandler.instance);

        ORE_GEN_BUS.register(WorldGenHandler.instance);
    }

    @EventHandler
    public void modsLoaded(final FMLPostInitializationEvent event)
    {
        if (Properties.retroOreGen)
        {
            TickRegistry.registerTickHandler(TickHandlerWorld.instance, Side.SERVER);
        }
    }
}