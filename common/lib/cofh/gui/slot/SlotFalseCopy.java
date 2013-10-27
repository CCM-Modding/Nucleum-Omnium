package lib.cofh.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Slot which copies an ItemStack when clicked on, does not decrement the ItemStack on the cursor.
 * 
 * @author King Lemming
 */
public class SlotFalseCopy extends Slot
{
    public int slotIndex = 0;

    public SlotFalseCopy(IInventory inventory, int x, int y, int z)
    {
        super(inventory, x, y, z);
        slotIndex = x;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        inventory.setInventorySlotContents(slotIndex, null);
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if (inventory.getStackInSlot(slotIndex) == null)
        {
            ItemStack phantomStack = stack.copy();
            phantomStack.stackSize = 1;
            inventory.setInventorySlotContents(slotIndex, phantomStack);
        }
        return false;
    }
}