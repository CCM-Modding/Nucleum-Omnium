package ccm.nucleum.helper;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryHelper extends BaseHelper
{

    private static final String slot = "slot";

    /**
     * Writes a inventory to a NBTTagList.
     * 
     * @param stacks
     *            The stacks in the current inventory to add to the NBTTagList.
     * @return The NBTTagList associated to the inventory.
     */
    public static NBTTagList writeInventoryToNBT(final ItemStack[] stacks)
    {
        NBTTagCompound stackNBT;
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < stacks.length; i++){
            if (stacks[i] == null){
                continue;
            }
            stackNBT = new NBTTagCompound();
            stackNBT.setInteger(slot, i);
            stacks[i].writeToNBT(stackNBT);
            list.appendTag(stackNBT);
        }
        return list;
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
        NBTTagCompound compound;
        int index;
        for (int i = 0; i < list.tagCount(); i++){
            compound = (NBTTagCompound) list.tagAt(i);
            index = compound.getInteger(slot);
            stacks[index] = ItemStack.loadItemStackFromNBT(compound);
        }
        return stacks;
    }

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
    public static ItemStack decrStackSize(final int slot, final int amount, final ItemStack[] stacks, final IInventory inventory)
    {
        if (stacks[slot] != null){
            ItemStack itemstack;
            if (stacks[slot].stackSize <= amount){
                itemstack = stacks[slot];
                stacks[slot] = null;
                inventory.onInventoryChanged();
                return itemstack;
            }else{
                itemstack = stacks[slot].splitStack(amount);
                if (stacks[slot].stackSize == 0){
                    stacks[slot] = null;
                }
                inventory.onInventoryChanged();
                return itemstack;
            }
        }else{
            return null;
        }
    }

    /**
     * Checks the Inventory Array for either a empty slot or one that contains the output Item
     * Example: {@code this.inventory[getBestInventory(this.inventory, 3, itemstack)]} That should
     * work in
     * the Counter
     * 
     * @param inventory
     *            The inventory to search
     * @param startSlot
     *            The starting Slot
     * @param output
     *            The output
     * @return The Slot number
     */
    public static int getBestInventory(final ItemStack[] inventory, final int startSlot, final ItemStack output)
    {
        for (int j = startSlot; j < inventory.length; j++){
            if (inventory[j] == output){
                return j;
            }else if (inventory[j] == null){
                return j;
            }
        }
        return startSlot;
    }
}