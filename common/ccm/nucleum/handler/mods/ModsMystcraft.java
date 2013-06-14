package ccm.nucleum.handler.mods;

import ccm.nucleum.utils.lib.Properties;

public final class ModsMystcraft implements IModHandler {
    
    @Override
    public String getModName() {
        return "Mystcraft";
    }
    
    @Override
    public void init() {
        Properties.mystLoaded = true;
    }
}