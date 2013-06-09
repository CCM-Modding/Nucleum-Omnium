package ccm.nucleum_world;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import ccm.nucleum_omnium.BaseMod;
import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.configuration.AdvConfiguration;

import ccm.nucleum_world.generator.WorldGenHandler;
import ccm.nucleum_world.utils.TickHandlerWorld;
import ccm.nucleum_world.utils.lib.Archive;
import ccm.nucleum_world.utils.lib.Properties;

@Mod(modid = Archive.MOD_ID,
     name = Archive.MOD_NAME,
     version = Archive.MOD_VERSION,
     dependencies = Archive.MOD_DEPENDANCIES,
     certificateFingerprint = Archive.MOD_FIGERPRINT)
@NetworkMod(clientSideRequired = true,
            serverSideRequired = false)
public class NucleumWorld extends BaseMod implements IMod
{

    public static AdvConfiguration config;

    @PreInit
    public void preInit(final FMLPreInitializationEvent evt)
    {
        config = this.initializeConfig(evt);

        GameRegistry.registerWorldGenerator(WorldGenHandler.instance);

        MinecraftForge.EVENT_BUS.register(WorldGenHandler.instance);

        MinecraftForge.ORE_GEN_BUS.register(WorldGenHandler.instance);
    }

    @Init
    public void initialize(final FMLInitializationEvent event)
    {}

    @PostInit
    public void modsLoaded(final FMLPostInitializationEvent event)
    {
        if (Properties.retroOreGen){
            TickRegistry.registerTickHandler(TickHandlerWorld.instance, Side.SERVER);
        }
    }
}