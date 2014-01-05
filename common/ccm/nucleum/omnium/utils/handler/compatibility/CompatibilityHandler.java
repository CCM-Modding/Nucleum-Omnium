/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.compatibility;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;

/**
 * Registry for all mod compatibility classes inside of any of the CCM Mods
 * 
 * @author Captain_Shadows
 */
public final class CompatibilityHandler
{
    /** List of all {@link InteractCompatibility}s that this "Registry" needs to handle */
    private static final List<InteractHandler> interactHandling = new ArrayList<InteractHandler>();

    /**
     * @param compat
     *            The {@link NonInteractCompatibility} that you wish to be run if its mod is loaded
     */
    public static void checkLoaded(final NonInteractCompatibility compat)
    {
        if (Loader.isModLoaded(compat.id()))
        {
            compat.init();
        }
    }

    /**
     * @param id
     *            The id of the mod that you are interacting compatibility for.
     *            <p>
     *            For Example: "compression"
     * @param handler
     *            The location of your {@link InteractCompatibility} class.
     *            <p>
     *            For Example: "ccm.nucleum.omnium.utils.handler.compatibility.MystcraftHandler"
     */
    public static void addInteractCompatibility(final String id, final String handler)
    {
        interactHandling.add(new InteractHandler(id, handler));
    }

    /**
     * <b>THIS METHOD SHOULD NEVER BE CALLED BY ANY CLASS.</b>
     * <p>
     * Except for NucleumOmnium.java
     */
    public static void init()
    {
        for (final InteractHandler handler : interactHandling)
        {
            handler.init();
        }
    }
}