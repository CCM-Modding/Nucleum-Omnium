package lib.cofh.api.energy;

import net.minecraft.item.ItemStack;

/**
 * Implement this interface on Item classes that support external manipulation
 * of their internal energy storages. A reference implementation is provided
 * {@link ItemEnergyContainer}.
 * 
 * @author King Lemming
 */
public interface IEnergyContainerItem {
    
    /**
     * Adds energy to an item. Returns the quantity of energy that was accepted.
     * This should always return 0 if the item cannot be externally charged.
     * 
     * @param theItem
     *            ItemStack to be charged.
     * @param maxReceive
     *            Maximum amount of energy to be sent into the item.
     * @param doReceive
     *            If false, the charge will only be simulated.
     * @return Amount of energy that was accepted by the item.
     */
    int receiveEnergy(ItemStack theItem, int maxReceive, boolean doReceive);
    
    /**
     * Removes energy from an item. Returns the quantity of energy that was
     * removed. This should always return 0 if the item cannot be externally
     * discharged.
     * 
     * @param theItem
     *            ItemStack to be discharged.
     * @param maxExtract
     *            Maximum amount of energy to be removed from the item.
     * @param doExtract
     *            If false, the discharge will only be simulated.
     * @return Amount of energy that was removed from the item.
     */
    int extractEnergy(ItemStack theItem, int maxExtract, boolean doExtract);
    
    /**
     * Get the amount of energy currently stored in the item.
     */
    int getEnergyStored(ItemStack theItem);
    
    /**
     * Get the max amount of energy that can be stored in the item.
     */
    int getMaxEnergyStored(ItemStack theItem);
    
}
