package lib.cofh.api.core;

/**
 * Access to the Cape and Skin Registries of lib.cofh Core.
 * 
 * @author Zeldo Kavira
 * 
 */
public class RegistryAccess {

	public static ISimpleRegistry capeRegistry = new NullSimpleRegistry();
	public static ISimpleRegistry skinRegistry = new NullSimpleRegistry();
}