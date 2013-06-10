package ccm.nucleum_omnium;

import java.util.Arrays;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import ccm.nucleum_omnium.handler.CommandHandler;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.handler.mods.ModHandler;
import ccm.nucleum_omnium.handler.mods.ModsMystcraft;
import ccm.nucleum_omnium.proxy.CommonProxy;
import ccm.nucleum_omnium.utils.language.OmniumLanguagePack;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Locations;

public class NucleumOmnium extends DummyModContainer implements IMod
{

    /**
     * The Instance
     */
    @Instance(Archive.MOD_ID)
    public static NucleumOmnium   instance;

    /**
     * The proxy
     */
    @SidedProxy(serverSide = Locations.SERVER_PROXY,
                clientSide = Locations.CLIENT_PROXY)
    public static CommonProxy     proxy;

    public static MinecraftServer server;

    public NucleumOmnium()
    {
        super(new ModMetadata());
        final ModMetadata meta = super.getMetadata();
        meta.modId = Archive.MOD_ID;
        meta.name = Archive.MOD_NAME;
        meta.version = Archive.MOD_VERSION;
        meta.authorList = Arrays.asList("Captain_Shadows");
        meta.url = "https://github.com/CCM-Modding/Nucleum-Omnium";
        meta.updateUrl = "https://github.com/CCM-Modding/Nucleum-Omnium/tree/master/Releases";
        meta.credits = "@THANKS@";
        meta.logoFile = "/mods/nucleum-omnium/textures/logo.png";
        meta.description = "Core functionality for all CCM Mods";
    }

    @Override
    public boolean registerBus(final EventBus bus, final LoadController controller)
    {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void preInit(final FMLPreInitializationEvent evt)
    {
        if (!Handler.isModLoaded(this)){

            Handler.initLog(this);
        }
    }

    @Subscribe
    public void init(final FMLInitializationEvent event)
    {
        proxy.initCapes();

        proxy.initEventHandling();

        proxy.initModelHandlers();

        ModHandler.addModToHandle(new ModsMystcraft());

        new OmniumLanguagePack().loadLangs();
    }

    @Subscribe
    public void PostInit(final FMLPostInitializationEvent event)
    {
        ModHandler.init();

        Handler.loadMod(this);
    }

    @Subscribe
    public void serverStarting(final FMLServerStartingEvent event)
    {
        // Initialize the custom commands
        CommandHandler.initCommands(event);

        server = event.getServer();
    }

    @Override
    public String getId()
    {
        return Archive.MOD_ID;
    }
}