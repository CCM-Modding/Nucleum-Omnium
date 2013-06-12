package ccm.nucleum;

import java.util.Arrays;

import net.minecraft.server.MinecraftServer;
import ccm.nucleum.handler.CommandHandler;
import ccm.nucleum.handler.Handler;
import ccm.nucleum.handler.mods.ModHandler;
import ccm.nucleum.handler.mods.ModsMystcraft;
import ccm.nucleum.helper.DataHelper;
import ccm.nucleum.proxy.CommonProxy;
import ccm.nucleum.utils.language.OmniumLanguagePack;
import ccm.nucleum.utils.lib.Archive;
import ccm.nucleum.utils.lib.Locations;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

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
        final ModMetadata meta = this.getMetadata();
        meta.modId = Archive.MOD_ID;
        meta.name = Archive.MOD_NAME;
        meta.version = Archive.MOD_VERSION;
        meta.authorList = Arrays.asList("Captain_Shadows");
        meta.url = "https://github.com/CCM-Modding/Nucleum-Omnium";
        meta.updateUrl = "http://driesgames.game-server.cc:8080/view/CCM/job/Nucleum-Omnium/";
        meta.credits = "Made By Captain Shadows, ClayCorp, Morton, and The rest of the CCM Modding Team, with special help from AbrarSyed(and the rest of the FE team, including Dries007), RebelKeithy, and a bunch of other people, Also a big thanks to The COFH Team for their Library, Jadar for Developer Capes API, and last but not least Shedar for ModStats. Also to the Forge and MCP crew, who without them no Minecraft mods would be possible";
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
        
        DataHelper.init();
    }
}