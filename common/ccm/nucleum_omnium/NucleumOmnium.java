package ccm.nucleum_omnium;

import lib.org.modstats.ModstatInfo;
import net.minecraft.server.MinecraftServer;
import ccm.nucleum_network.PacketHandler;
import ccm.nucleum_omnium.configuration.AdvConfiguration;
import ccm.nucleum_omnium.handler.CommandHandler;
import ccm.nucleum_omnium.handler.LogHandler;
import ccm.nucleum_omnium.handler.ModLoadingHandler;
import ccm.nucleum_omnium.handler.config.ConfigurationHandler;
import ccm.nucleum_omnium.handler.config.NOConfig;
import ccm.nucleum_omnium.handler.mods.ModHandler;
import ccm.nucleum_omnium.handler.mods.MystcraftHandler;
import ccm.nucleum_omnium.helper.DataHelper;
import ccm.nucleum_omnium.proxy.CommonProxy;
import ccm.nucleum_omnium.utils.language.OmniumLP;
import ccm.nucleum_omnium.utils.lib.Archive;
import ccm.nucleum_omnium.utils.lib.Locations;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(	modid = Archive.MOD_ID,
		name = Archive.MOD_NAME,
		certificateFingerprint = Archive.MOD_FIGERPRINT,
		useMetadata = true)
@NetworkMod(clientSideRequired = true,
			serverSideRequired = false,
			channels = Archive.MOD_CHANNEL,
			packetHandler = PacketHandler.class)
@ModstatInfo(prefix = Archive.MOD_PREFIX)
public class NucleumOmnium extends BaseMod implements IMod {

	@Instance(Archive.MOD_ID)
	public static NucleumOmnium		instance;

	@SidedProxy(serverSide = Locations.SERVER_PROXY,
				clientSide = Locations.CLIENT_PROXY)
	public static CommonProxy		proxy;

	public static AdvConfiguration	config;

	public static MinecraftServer	server;
	
	@Override
	public AdvConfiguration getConfigFile() {
		return config;
	}

	@FingerprintWarning
	public void invalidFingerprint(final FMLFingerprintViolationEvent event) {
		/*
		 * Report (log) to the user that the version of Nucleum Omnium they are using has been
		 * changed/tampered with
		 */
		LogHandler.log(Archive.INVALID_FINGERPRINT_MSG);
	}
	
	@PreInit
	public void preInit(final FMLPreInitializationEvent evt) {
		if (!ModLoadingHandler.isModLoaded(this)) {
			LogHandler.initLog(this);

			config = initializeConfig(evt);

			ConfigurationHandler.init(this, NOConfig.class);
		}
	}
	
	@Init
	public void init(final FMLInitializationEvent event) {

		proxy.initCapes();

		proxy.initEventHandling();

		proxy.initModelHandlers();

		ModHandler.addMod(new MystcraftHandler());

		OmniumLP.init();
	}

	@PostInit
	public void PostInit(final FMLPostInitializationEvent event) {

		ModHandler.init();

		ModLoadingHandler.loadMod(this);
	}

	@ServerStarting
	public void serverStarting(final FMLServerStartingEvent event) {
		// Initialize the custom commands
		CommandHandler.initCommands(event);

		server = event.getServer();

		DataHelper.init();
	}
}