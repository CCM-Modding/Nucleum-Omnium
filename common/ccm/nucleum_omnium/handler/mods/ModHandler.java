package ccm.nucleum_omnium.handler.mods;

import java.util.ArrayList;
import java.util.List;

import ccm.nucleum_omnium.BaseNIClass;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import cpw.mods.fml.common.Loader;

public final class ModHandler extends BaseNIClass {
    
    private static List<ModHandling> modsHandling = new ArrayList<ModHandling>();
    
    public static void addModToHandle(final IModHandler handler) {
        ModHandler.modsHandling.add(new ModHandling(handler, null));
    }
    
    public static void addModToHandle(final IModHandler handler, final String exeption) {
        ModHandler.modsHandling.add(new ModHandling(handler, exeption));
    }
    
    public static void init() {
        for (final ModHandling handler : ModHandler.modsHandling) {
            if (handler.getModExeption() != null) {
                ModHandler.handleMod(handler.getHandler(), handler.getModExeption());
            } else {
                ModHandler.handleMod(handler.getHandler());
            }
        }
    }
    
    public static void handleMod(final IModHandler handler) {
        if (Loader.isModLoaded(handler.getModName())) {
            try {
                handler.init();
            } catch (final Exception e) {
                
                Handler.log(String
                        .format("A CCM Mod has failed to load it's compatibility with %s, pleace inform the CCM Team",
                                handler.getModName()));
            }
        }
    }
    
    public static void handleMod(final IModHandler handler, final String exeption) {
        if (Loader.isModLoaded(handler.getModName())) {
            try {
                handler.init();
            } catch (final Exception e) {
                
                Handler.log(NucleumOmnium.instance, exeption);
            }
        }
    }
}