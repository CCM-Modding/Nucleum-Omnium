/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.api.fuels;

import net.minecraft.item.ItemStack;

/**
 * IFuelRegistry
 * <p>
 * An Interface for any class that wishes to be a Fuel Registry, Most likely also a part of the mod's API
 * 
 * @author Captain_Shadows
 */
public interface IFuelRegistry
{

    /**
     * Checks if a item is a Fuel
     * 
     * @param stack
     *            The stack to check if it is a Fuel
     * @return true if it is found in the Fuel List
     */
    public boolean isFuel(final ItemStack stack);

    /**
     * Registers a new Fuel
     * 
     * @param stack
     *            The Fuel to add
     */
    public void registerFuel(final ItemStack stack);
}