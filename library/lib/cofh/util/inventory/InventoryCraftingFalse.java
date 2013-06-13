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

        if (this.stackList[slot] != null) {
            ItemStack stack;

            if (this.stackList[slot].stackSize <= amount) {
                stack = this.stackList[slot];
                this.stackList[slot] = null;
                return stack;
            } else {
                stack = this.stackList[slot].splitStack(amount);

                if (this.stackList[slot].stackSize == 0)
                    this.stackList[slot] = null;
                return stack;
            }
        } else
            return null;
    }

    @Override
    public void setInventorySlotContents(final int slot, final ItemStack stack) {

        this.stackList[slot] = stack;
    }

}
