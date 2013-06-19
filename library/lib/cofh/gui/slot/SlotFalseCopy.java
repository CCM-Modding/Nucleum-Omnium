package lib.cofh.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFalseCopy extends Slot {
    
    public int slotIndex = 0;
    
    public SlotFalseCopy(final IInventory inventory, final int x, final int y, final int z) {
        
        super(inventory, x, y, z);
        slotIndex = x;
    }
    
    @Override
    public boolean canTakeStack(final EntityPlayer player) {
        
        inventory.setInventorySlotContents(slotIndex, null);
        return false;
    }
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        
        if (inventory.getStackInSlot(slotIndex) == null) {
            final ItemStack phantomStack = stack.copy();
            phantomStack.stackSize = 1;
            inventory.setInventorySlotContents(slotIndex, phantomStack);
        }
        return false;
    }
    
}
