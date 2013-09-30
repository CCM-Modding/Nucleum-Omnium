package ccm.nucleum.omnium.utils.handler.mods;

import cpw.mods.fml.common.Loader;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

/**
 * Internal Helper used to keep data values and run the init method
 * 
 * @author Captain_Shadows
 */
final class APIHandler {

	private final String name;
	private final Class<? extends IModHandler> handler;

	public APIHandler(String name, Class<? extends IModHandler> handler) {
		super();
		this.name = name;
		this.handler = handler;
	}

	public void init() {
		if (Loader.isModLoaded(name)) {

			IModHandler tmp = null;

			try {
				tmp = handler.newInstance();
			} catch (Exception e) {
				CCMLogger.DEFAULT_LOGGER.printCatch(e, "A CCM Mod has failed to create a new instance of it's compatibility with %s, pleace inform the CCM Team", name);
			}
			try {
				tmp.init();
			} catch (final Exception e) {
				CCMLogger.DEFAULT_LOGGER.printCatch(e, "A CCM Mod has failed to load it's compatibility with %s, pleace inform the CCM Team", name);
			}
		}
	}
}