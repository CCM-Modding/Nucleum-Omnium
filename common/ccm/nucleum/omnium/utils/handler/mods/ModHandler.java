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
public final class ModHandler
{
    /** List of all {@link IModHandler}s that this "Registry" needs to handle */
    private static final List<IModHandler> modsHandling = new ArrayList<IModHandler>();

    /**
     * @param handler
     *            The {@link IModHandler} to add to the List
     */
    public static void addModHandler(final IModHandler handler)
    {
        modsHandling.add(handler);
    }

    /**
     * THIS METHOD SHOULD NEVER BE CALLED BY ANY CLASS.
     * <p>
     * Except NucleumOmnium.java
     */
    public static void init()
    {
        for (final IModHandler handler : modsHandling)
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
        if (Loader.isModLoaded(handler.getAPIModName()))
        {
            try
            {
                handler.init();
            } catch (final Exception e)
            {
                handler.getMod().getLogger().printCatch(e, handler.toString());
            }
        }
    }
}