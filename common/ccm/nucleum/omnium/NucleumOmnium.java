/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium;

import static ccm.nucleum.omnium.utils.lib.Archive.MOD_CHANNEL;
import static ccm.nucleum.omnium.utils.lib.Archive.MOD_ID;
import static ccm.nucleum.omnium.utils.lib.Archive.MOD_NAME;
import static ccm.nucleum.omnium.utils.lib.Locations.CLIENT_PROXY;
import static ccm.nucleum.omnium.utils.lib.Locations.SERVER_PROXY;
import net.minecraft.server.MinecraftServer;
import ccm.nucleum.network.PacketHandler;
import ccm.nucleum.omnium.proxy.CommonProxy;
import ccm.nucleum.omnium.utils.handler.compatibility.CompatibilityHandler;
import ccm.nucleum.omnium.utils.handler.configuration.NOConfig;
import ccm.nucleum.omnium.utils.handler.gui.GuiHandler;
import ccm.nucleum.omnium.utils.helper.DataHelper;
import ccm.nucleum.omnium.utils.registry.CommandRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = MOD_ID, name = MOD_NAME, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = MOD_CHANNEL, packetHandler = PacketHandler.class)
public class NucleumOmnium extends CCMMod
{
    @Instance(MOD_ID)
    public static NucleumOmnium instance;

    @SidedProxy(serverSide = SERVER_PROXY, clientSide = CLIENT_PROXY)
    public static CommonProxy proxy;

    /** The current MC Server Instance */
    public static MinecraftServer server;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event)
    {
        loadMod(this, event, new NOConfig());
    }

    @EventHandler
    public void init(final FMLInitializationEvent event)
    {
        proxy.initCapes();
        proxy.initEventHandling();

        CompatibilityHandler.addModHandler("Mystcraft", "ccm.nucleum.omnium.utils.handler.compatibility.MystcraftCompat");

        // Registers the GUI Handler
        NetworkRegistry.instance().registerGuiHandler(NucleumOmnium.instance, GuiHandler.INSTANCE);
    }

    @EventHandler
    public void PostInit(final FMLPostInitializationEvent event)
    {
        CompatibilityHandler.init();
    }

    @EventHandler
    public void serverStarting(final FMLServerStartingEvent event)
    { // Initialize the custom commands
        CommandRegistry.init(event);

        server = event.getServer();

        DataHelper.init();
    }
}