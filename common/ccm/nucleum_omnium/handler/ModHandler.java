package ccm.nucleum_omnium.handler;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.Loader;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.mods.IModHandler;
import ccm.nucleum_omnium.handler.mods.ModHandling;

public class ModHandler
{

    private static List<ModHandling> modsHandling = new LinkedList<ModHandling>();

    public static void addModToHandle(final IModHandler handler, final String modName)
    {
        modsHandling.add(new ModHandling(handler, modName, null));
    }

    public static void addModToHandle(final IModHandler handler, final String modName, final String exeption)
    {
        modsHandling.add(new ModHandling(handler, modName, exeption));
    }

    public static void init()
    {
        for (final ModHandling handler : modsHandling){
            if (handler.getModExeption() != null){
                handleMod(handler.getHandler(), handler.getModName(), handler.getModExeption());
            }else{
                handleMod(handler.getHandler(), handler.getModName());
            }
        }
    }

    public static void handleMod(final IModHandler handler, final String modName)
    {
        if (Loader.isModLoaded(modName)){
            handler.init();
        }
    }

    public static void handleMod(final IModHandler handler, final String modName, final String exeption)
    {
        if (Loader.isModLoaded(modName)){
            try{
                handler.init();
            }catch(final Exception e){

                Handler.log(NucleumOmnium.instance, exeption);
            }
        }
    }
}