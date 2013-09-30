/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.mods;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;

/**
 * Registry for all mod compatibility classes inside of any of the CCM Mods
 * 
 * @author Captain_Shadows
 */
public final class ModHandler {
	/** List of all {@link IModHandler}s that this "Registry" needs to handle */
	private static final List<APIHandler> modsHandling = new ArrayList<APIHandler>();

	/**
	 * @param name
	 *            The name of the mod that you are adding compatibility for. EX:
	 *            Buildcraft
	 * @param handler
	 *            The {@link IModHandler} to add to the List
	 */
	public static void addModHandler(final String name,
			final IModHandler handler) {
		modsHandling.add(new APIHandler(name, handler));
	}

	/**
	 * THIS METHOD SHOULD NEVER BE CALLED BY ANY CLASS.
	 * <p>
	 * Except NucleumOmnium.java
	 */
	public static void init() {
		for (final APIHandler handler : modsHandling) {
			handler.init();
		}
	}
}