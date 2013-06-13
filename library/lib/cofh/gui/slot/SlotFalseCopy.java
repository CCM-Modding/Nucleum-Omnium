package lib.cofh.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFalseCopy extends Slot {

    public int slotIndex = 0;

    public SlotFalseCopy(final IInventory inventory, final int x, final int y, final int z) {

        super(inventory, x, y, z);
        this.slotIndex = x;
    }

    @Override
    public boolean canTakeStack(final EntityPlayer player) {

        this.inventory.setInventorySlotContents(this.slotIndex, null);
        return false;
    }

    @Override
    public boolean isItemValid(final ItemStack stack) {

        if (this.inventory.getStackInSlot(this.slotIndex) == null) {
            final ItemStack phantomStack = stack.copy();
            phantomStack.stackSize = 1;
            this.inventory.setInventorySlotContents(this.slotIndex, phantomStack);
        }
        return false;
    }

}
