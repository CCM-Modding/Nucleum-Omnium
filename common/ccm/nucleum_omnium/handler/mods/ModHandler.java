package ccm.nucleum_omnium.handler.mods;

import java.util.ArrayList;
import java.util.List;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import cpw.mods.fml.common.Loader;

public final class ModHandler {
    
    private static ModHandler       instance;
    
    private final List<ModHandling> modsHandling = new ArrayList<ModHandling>();
    
    public static ModHandler instance() {
        if (instance == null) {
            instance = new ModHandler();
        }
        return instance;
    }
    
    public void addModToHandle(final IModHandler handler) {
        modsHandling.add(new ModHandling(handler, null));
    }
    
    public void addModToHandle(final IModHandler handler, final String exeption) {
        modsHandling.add(new ModHandling(handler, exeption));
    }
    
    public void init() {
        for (final ModHandling handler : modsHandling) {
            if (handler.getModExeption() != null) {
                handleMod(handler.getHandler(), handler.getModExeption());
            } else {
                handleMod(handler.getHandler());
            }
        }
    }
    
    private void handleMod(final IModHandler handler) {
        if (Loader.isModLoaded(handler.getModName())) {
            try {
                handler.init();
            } catch (final Exception e) {
                
                Handler.log(String.format("A CCM Mod has failed to load it's compatibility with %s, pleace inform the CCM Team", handler.getModName()));
            }
        }
    }
    
    private void handleMod(final IModHandler handler, final String exeption) {
        if (Loader.isModLoaded(handler.getModName())) {
            try {
                handler.init();
            } catch (final Exception e) {
                
                Handler.log(NucleumOmnium.instance, exeption);
            }
        }
    }
}