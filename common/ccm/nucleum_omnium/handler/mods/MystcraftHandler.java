package ccm.nucleum_omnium.handler.mods;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.utils.lib.Properties;

/**
 * Mystcraft's Handler
 * 
 * @author Captain_Shadows
 */
public final class MystcraftHandler extends IModHandler {

	@Override
	public IMod getMod() {
		return NucleumOmnium.instance;
	}

	@Override
	public String getModName() {
		return "Mystcraft";
	}

	@Override
	public void init() {
		Properties.mystLoaded = true;
	}
}