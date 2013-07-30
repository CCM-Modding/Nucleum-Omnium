package ccm.nucleum_omnium.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import ccm.nucleum_omnium.api.fuels.IFuelRegistry;

public class UseSlot extends Slot {

    private IFuelRegistry fuel;

    public UseSlot(final IInventory inventory, final int index, final int x, final int y) {
        super(inventory, index, x, y);
    }

    public UseSlot(final IInventory inventory,
                   final int index,
                   final int x,
                   final int y,
                   final IFuelRegistry fuel) {
        super(inventory, index, x, y);
        setFuel(fuel);
    }

    public void setFuel(final IFuelRegistry fuel) {
        this.fuel = fuel;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(final ItemStack item) {
        return fuel.isFuel(item);
    }
}