/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.helper;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import ccm.nucleum_omnium.base.BaseNIC;

public class InventoryHelper extends BaseNIC
{

    private static final String slot = "CCM.SLOT";

    /**
     * Decreases the {@link ItemStack} size in a slot.
     * 
     * @param slot
     *            The Slot where the {@link ItemStack} can be found.
     * @param amount
     *            The amount to subtract.
     * @param stacks
     *            The {@link ItemStack} to subtract from.
     * @param inventory
     *            The inventory (A class implementing {@link IInventory})
     * @return The new {@link ItemStack}.
     */
    public static ItemStack decrStackSize(final int slot, final int amount, final IInventory inventory)
    {
        if (inventory.getStackInSlot(slot) != null)
        {
            ItemStack itemstack;
            if (inventory.getStackInSlot(slot).stackSize <= amount)
            {
                itemstack = inventory.getStackInSlot(slot);
                setEmty(inventory, slot);
                inventory.onInventoryChanged();
                return itemstack;
            } else
            {
                itemstack = inventory.getStackInSlot(slot).splitStack(amount);
                if (inventory.getStackInSlot(slot).stackSize == 0)
                {
                    setEmty(inventory, slot);
                }
                inventory.onInventoryChanged();
                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    /**
     * Checks the Inventory Array for either a empty slot or one that contains the output Item
     * Example: {@code inventory[getBestInventory(this.inventory, 3, itemstack)]} That
     * should work in the Counter
     * 
     * @param inventory
     *            The inventory to search
     * @param startSlot
     *            The starting Slot
     * @param output
     *            The output
     * @return The Slot number
     */
    public static int
            getBestInventory(final IInventory inventory, final int startSlot, final ItemStack output)
    {

        if (inventory.getStackInSlot(startSlot) == null)
        {
            return startSlot;
        } else if (ItemHelper.equals(inventory.getStackInSlot(startSlot), output))
        {
            return startSlot;
        } else
        {
            int bestSlot = startSlot;

            for (int slot = startSlot; slot < inventory.getSizeInventory(); slot++)
            {
                if (inventory.getStackInSlot(slot) == null)
                {
                    bestSlot = slot;
                    break;
                } else if (ItemHelper.equals(inventory.getStackInSlot(slot), output))
                {
                    bestSlot = slot;
                    break;
                }
            }
            return bestSlot;
        }
    }

    public static int getBestSlot(final IInventory inventory,
                                  final int startSlot,
                                  final int endSlot,
                                  final ItemStack output)
    {

        if (inventory.getStackInSlot(startSlot) == null)
        {
            return startSlot;
        } else if (ItemHelper.equals(inventory.getStackInSlot(startSlot), output))
        {
            return startSlot;
        } else
        {
            int bestSlot = startSlot;

            for (int slot = startSlot; slot < endSlot; slot++)
            {
                if (inventory.getStackInSlot(slot) == null)
                {
                    bestSlot = slot;
                    break;
                } else if (ItemHelper.equals(inventory.getStackInSlot(slot), output))
                {
                    bestSlot = slot;
                    break;
                }
            }
            return bestSlot;
        }
    }

    /**
     * Reads a Inventory from a NBTTagList.
     * 
     * @param list
     *            The Name of the NBTTagList to read from.
     * @param size
     *            The size of the Inventory.
     * @return The Items on the inventory.
     */
    public static ItemStack[] readInventoryFromNBT(final NBTTagList list, final int size)
    {
        final ItemStack[] stacks = new ItemStack[size];
        NBTTagCompound nbt;
        for (int i = 0; i < list.tagCount(); i++)
        {
            nbt = (NBTTagCompound) list.tagAt(i);
            stacks[nbt.getInteger(slot)] = ItemStack.loadItemStackFromNBT(nbt);
        }
        return stacks;
    }

    /**
     * Sets a slot in the inventory to be empty
     * 
     * @param inventory
     * @param slot
     */
    public static void setEmty(final IInventory inventory, final int slot)
    {
        inventory.setInventorySlotContents(slot, null);
    }

    /**
     * Writes a inventory to a NBTTagList.
     * 
     * @param stacks
     *            The stacks in the current inventory to add to the NBTTagList.
     * @return The NBTTagList associated to the inventory.
     */
    public static NBTTagList writeInventoryToNBT(final ItemStack[] stacks)
    {
        NBTTagCompound nbt;
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < stacks.length; i++)
        {
            if (stacks[i] == null)
            {
                continue;
            }
            nbt = new NBTTagCompound();
            nbt.setInteger(slot, i);
            stacks[i].writeToNBT(nbt);
            list.appendTag(nbt);
        }
        return list;
    }
}