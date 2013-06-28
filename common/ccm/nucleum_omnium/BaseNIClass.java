package ccm.nucleum_omnium;

import java.util.Random;

import ccm.nucleum_omnium.utils.exeptions.InstantiationExeption;

/**
 * This class SHOULD ONLY be extended when and ONLY when you want your class not to be instantiated
 * using the default Java Constructor
 * 
 * @author Captain_Shadows
 */
public class BaseNIClass {

	/**
	 * Base constructor
	 * 
	 * @throws InstantiationExeption
	 *             if you try to instantiate it or any of it's subclasses
	 */
	protected BaseNIClass() {
		throw new InstantiationExeption(NucleumOmnium.instance);
	}

	/**
	 * A {@link Random} accessible by any subclass
	 */
	protected static Random	rand	= new Random();
}