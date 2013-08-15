/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.mods;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;

import ccm.nucleum.omnium.utils.handler.LogHandler;

/**
 * Registry for all mod compatibility classes inside of any of the CCM Mods
 * 
 * @author Captain_Shadows
 */
public final class ModHandler
{

    /**
     * List of all {@link IModHandler}s that this "Registry" needs to handle
     */
    private final List<IModHandler> modsHandling;

    /**
     * Private single instance
     */
    private static final ModHandler INSTANCE = new ModHandler();

    /**
     * Private method that initializes the IModHandler
     * 
     * @param handler
     *            The handler to init
     */
    private static void handleMod(final IModHandler handler)
    {
        if (Loader.isModLoaded(handler.getModName()))
        {
            try
            {
                handler.init();
            } catch (final Exception e)
            {
                LogHandler.severe(handler.getMod(), handler.toString());
                e.printStackTrace();
            }
        }
    }

    /**
     * @param handler
     *            The {@link IModHandler} to add to the List
     */
    public static void addMod(final IModHandler handler)
    {
        INSTANCE.modsHandling.add(handler);
    }

    /**
     * THIS METHOD SHOULD NEVER BE CALLED BY ANY CLASS.
     * <p>
     * Except NucleumOmnium.java
     */
    public static void init()
    {
        for (final IModHandler handler : INSTANCE.modsHandling)
        {
            handleMod(handler);
        }
    }

    /**
     * Private constructor
     */
    private ModHandler()
    {
        modsHandling = new ArrayList<IModHandler>();
    }
}