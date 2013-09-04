/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.api.fuels;

import java.util.HashSet;
import java.util.Set;

import ccm.nucleum.omnium.utils.helper.item.WrapperStack;

/**
 * IFuelRegistry
 * <p>
 * An Abstract Class for any class that wishes to be a Fuel Registry, Most likely also a part of the mod's API
 * 
 * @author Captain_Shadows
 */
public abstract class IFuelRegistry
{
    protected final Set<WrapperStack> fuels = new HashSet<WrapperStack>();

    public boolean isFuel(final WrapperStack stack)
    {
        return fuels.contains(stack);
    }

    public void registerFuel(final WrapperStack stack)
    {
        if (stack != null)
        {
            fuels.add(stack);
        }
    }
}