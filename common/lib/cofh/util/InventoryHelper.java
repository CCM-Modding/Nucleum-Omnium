package lib.cofh.util;

import net.minecraft.item.ItemStack;

public class InventoryHelper
{
    private InventoryHelper()
    {}

    /**
     * Copy an entire inventory. Best to avoid doing this often.
     */
    public static ItemStack[] cloneInventory(ItemStack[] stacks)
    {
        ItemStack[] inventoryCopy = new ItemStack[stacks.length];
        for (int i = 0; i < stacks.length; i++)
        {
            inventoryCopy[i] = stacks[i] == null ? null : stacks[i].copy();
        }
        return inventoryCopy;
    }

    /**
     * Add an ItemStack to an inventory. Return true if the entire stack was added.
     * 
     * @param inventory
     *            The inventory.
     * @param stack
     *            ItemStack to add.
     * @param startIndex
     *            First slot to attempt to add into. Does not loop around fully.
     */
    public static boolean addItemStackToInventory(ItemStack[] inventory, ItemStack stack, int startIndex)
    {
        if (stack == null)
        {
            return true;
        }
        int openSlot = -1;
        for (int i = startIndex; i < inventory.length; i++)
        {
            if (ItemHelper.areItemStacksEqualNoNBT(stack, inventory[i]) && (inventory[i].getMaxStackSize() > inventory[i].stackSize))
            {
                int hold = inventory[i].getMaxStackSize() - inventory[i].stackSize;
                if (hold >= stack.stackSize)
                {
                    inventory[i].stackSize += stack.stackSize;
                    stack = null;
                    return true;
                }
                stack.stackSize -= hold;
                inventory[i].stackSize += hold;
            } else if ((inventory[i] == null) && (openSlot == -1))
            {
                openSlot = i;
            }
        }
        if (openSlot > -1)
        {
            inventory[openSlot] = stack;
        } else
        {
            return false;
        }
        return true;
    }

    /**
     * Shortcut method for above, assumes starting slot is 0.
     */
    public static boolean addItemStackToInventory(ItemStack[] inventory, ItemStack stack)
    {
        return addItemStackToInventory(inventory, stack, 0);
    }
}