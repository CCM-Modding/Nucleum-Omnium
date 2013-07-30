package lib.cofh.util.inventory;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

/**
 * This class is used to get recipes (IRecipe requires it...) with a Container.
 * 
 * @author Zeldo Kavira
 */
public final class InventoryCraftingFalse extends InventoryCrafting {

    public InventoryCraftingFalse(final int width, final int height) {

        super(null, width, height);
    }

    @Override
    public ItemStack decrStackSize(final int slot, final int amount) {

        if (getStackInSlot(slot) != null) {
            ItemStack stack;

            if (getStackInSlot(slot).stackSize <= amount) {
                stack = getStackInSlot(slot);
                setInventorySlotContents(slot, null);
                return stack;
            } else {
                stack = getStackInSlot(slot).splitStack(amount);

                if (getStackInSlot(slot).stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
                return stack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(final int slot, final ItemStack stack) {

        super.setInventorySlotContents(slot, stack);
    }

}
