/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.compatibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry for all mod compatibility classes inside of any of the CCM Mods
 * 
 * @author Captain_Shadows
 */
public final class CompatibilityHandler
{
    /** List of all {@link ICompatibility}s that this "Registry" needs to handle */
    private static final List<Handler> modsHandling = new ArrayList<Handler>();

    /**
     * @param name
     *            The name of the mod that you are adding compatibility for. EX: Buildcraft
     * @param handler
     *            The location of you Handler class. EX: "ccm.nucleum.omnium.utils.handler.compatibility.MystcraftHandler"
     */
    public static void addModHandler(final String name, final String handler)
    {
        modsHandling.add(new Handler(name, handler));
    }

    /**
     * THIS METHOD SHOULD NEVER BE CALLED BY ANY CLASS.
     * <p>
     * Except NucleumOmnium.java
     */
    public static void init()
    {
        for (final Handler handler : modsHandling)
        {
            handler.init();
        }
    }
}