package ccm.nucleum.world;

import net.minecraftforge.common.MinecraftForge;
import ccm.nucleum.BaseMod;
import ccm.nucleum.IMod;
import ccm.nucleum.configuration.AdvConfiguration;
import ccm.nucleum.world.generator.WorldGenHandler;
import ccm.nucleum.world.utils.TickHandlerWorld;
import ccm.nucleum.world.utils.lib.Archive;
import ccm.nucleum.world.utils.lib.Properties;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Archive.MOD_ID,
     name = Archive.MOD_NAME,
     version = Archive.MOD_VERSION,
     dependencies = Archive.MOD_DEPENDANCIES)
public class NucleumWorld extends BaseMod implements IMod {

    /**
     * The Instance
     */
    @Instance(Archive.MOD_ID)
    public static NucleumWorld     instance;

    public static AdvConfiguration config;

    @PreInit
    public void preInit(final FMLPreInitializationEvent evt) {
        NucleumWorld.config = this.initializeConfig(evt);

        GameRegistry.registerWorldGenerator(WorldGenHandler.instance);

        MinecraftForge.EVENT_BUS.register(WorldGenHandler.instance);

        MinecraftForge.ORE_GEN_BUS.register(WorldGenHandler.instance);
    }

    @Init
    public void initialize(final FMLInitializationEvent event) {
    }

    @PostInit
    public void modsLoaded(final FMLPostInitializationEvent event) {
        if (Properties.retroOreGen)
            TickRegistry.registerTickHandler(TickHandlerWorld.instance, Side.SERVER);
    }
}