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
    /** Private single instance */
    private static final ModHandler INSTANCE = new ModHandler();

    /** List of all {@link IModHandler}s that this "Registry" needs to handle */
    private final List<IModHandler> modsHandling;

    /**
     * Private constructor
     */
    private ModHandler()
    {
        modsHandling = new ArrayList<IModHandler>();
    }

    public static ModHandler instance()
    {
        return INSTANCE;
    }

    public static List<IModHandler> mods()
    {
        return instance().modsHandling;
    }

    /**
     * @param handler
     *            The {@link IModHandler} to add to the List
     */
    public static void addMod(final IModHandler handler)
    {
        mods().add(handler);
    }

    /**
     * ONLY USE IF IT HAS A NO PARAMETER CONSTRUCTOR
     * 
     * @param handler
     *            The {@link IModHandler} to add to the List
     */
    public static void addMod(final Class<? extends IModHandler> handler)
    {
        try
        {
            addMod(handler.newInstance());
        } catch (final InstantiationException e)
        {
            e.printStackTrace();
        } catch (final IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * THIS METHOD SHOULD NEVER BE CALLED BY ANY CLASS.
     * <p>
     * Except NucleumOmnium.java
     */
    public static void init()
    {
        for (final IModHandler handler : mods())
        {
            handleMod(handler);
        }
    }

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
}