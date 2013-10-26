package ccm.nucleum.omnium.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import ccm.nucleum.omnium.utils.helper.item.WrappedStack;

public abstract class Inventory implements IInventory
{
    WrappedStack[] inventory;
    int inventorySize;
    int inventoryStackLimit;

    /**
     * @param inventorySize
     *            The size of the inventory
     * @param inventoryStackLimit
     *            The stack limit of the inventory
     */
    public Inventory(int inventorySize, int inventoryStackLimit)
    {
        inventory = new WrappedStack[inventorySize];
        this.inventorySize = inventorySize;
        this.inventoryStackLimit = inventoryStackLimit;
    }

    @Override
    public int getSizeInventory()
    {
        return inventorySize;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot].getItemStack();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack item = inventory[slot].getItemStack();
        inventory[slot] = null;

        return item;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack item = inventory[slot].getItemStack();
        inventory[slot] = null;
        return item;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item)
    {
        inventory[slot] = new WrappedStack(item);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inventoryStackLimit;
    }

    @Override
    public void onInventoryChanged()
    {}

    @Override
    public void openChest()
    {}

    @Override
    public void closeChest()
    {}
}