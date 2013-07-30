package ccm.nucleum_omnium.api.fuels;

import net.minecraft.item.ItemStack;

public interface IFuelRegistry {

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