/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.base;

import java.util.Random;

import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.utils.exeptions.InstantiationExeption;

/**
 * Base Non Instance able Class
 * <p>
 * This class SHOULD ONLY be extended when and ONLY when you want your class not to be instantiated using the default Java Constructor
 * 
 * @author Captain_Shadows
 */
public class BaseNIC
{

    /**
     * A {@link Random} accessible by any subclass
     */
    protected static final Random rand = new Random();

    /**
     * Base constructor
     * 
     * @throws InstantiationExeption
     *             if you try to instantiate it or any of it's subclasses
     */
    protected BaseNIC()
    {
        throw new InstantiationExeption(NucleumOmnium.instance);
    }
}