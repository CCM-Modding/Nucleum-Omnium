package ccm.nucleum_omnium.handler.mods;

import ccm.nucleum_omnium.utils.lib.Properties;

public final class MystcraftHandler implements IModHandler {
    
    @Override
    public String getModName() {
        return "Mystcraft";
    }
    
    @Override
    public void init() {
        Properties.mystLoaded = true;
    }
}