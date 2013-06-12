package ccm.nucleum.handler.mods;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;

import ccm.nucleum.NucleumOmnium;
import ccm.nucleum.handler.Handler;


public class ModHandler
{

    private static List<ModHandling> modsHandling = new ArrayList<ModHandling>();

    public static void addModToHandle(final IModHandler handler)
    {
        modsHandling.add(new ModHandling(handler, null));
    }

    public static void addModToHandle(final IModHandler handler, final String exeption)
    {
        modsHandling.add(new ModHandling(handler, exeption));
    }

    public static void init()
    {
        for (final ModHandling handler : modsHandling){
            if (handler.getModExeption() != null){
                handleMod(handler.getHandler(), handler.getModExeption());
            }else{
                handleMod(handler.getHandler());
            }
        }
    }

    public static void handleMod(final IModHandler handler)
    {
        if (Loader.isModLoaded(handler.getModName())){
            try{
                handler.init();
            }catch(final Exception e){

                Handler.log(String.format("A CCM Mods has failed to load %s, pleace inform the CCM Team", handler.getModName()));
            }
        }
    }

    public static void handleMod(final IModHandler handler, final String exeption)
    {
        if (Loader.isModLoaded(handler.getModName())){
            try{
                handler.init();
            }catch(final Exception e){

                Handler.log(NucleumOmnium.instance, exeption);
            }
        }
    }
}