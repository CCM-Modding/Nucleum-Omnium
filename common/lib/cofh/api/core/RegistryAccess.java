package lib.cofh.api.core;

/**
 * Access to the Cape and Skin Registers of CoFH Core.
 * 
 * @author Zeldo Kavira
 */
public class RegistryAccess
{
    /** Capes */
    public static ISimpleRegistry capeRegistry = new NullSimpleRegistry();

    /** Skins */
    public static ISimpleRegistry skinRegistry = new NullSimpleRegistry();
}