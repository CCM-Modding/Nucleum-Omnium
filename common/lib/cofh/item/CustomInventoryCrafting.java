package lib.cofh.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class CustomInventoryCrafting extends InventoryCrafting
{
    public IInventory myMaster;
    public int invOffset = 0;
    public int invSize = 0;
    /** the width of the crafting inventory */
    public final int inventoryWidth;
    /**
     * Class containing the callbacks for the events on_GUIClosed and on_CraftMaxtrixChanged.
     */
    public final Container eventHandler;

    public CustomInventoryCrafting(Container par1Container, int par2, int par3, IInventory masterTile, int startingInventoryIndex)
    {
        super(par1Container, par2, par3);
        invSize = par2 * par3;
        eventHandler = par1Container;
        inventoryWidth = par2;
        invOffset = startingInventoryIndex;
        myMaster = masterTile;
    }

    @Override
    public int getSizeInventory()
    {
        return invSize;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return slot >= getSizeInventory() ? null : myMaster.getStackInSlot(invOffset + slot);
    }

    @Override
    public ItemStack getStackInRowAndColumn(int par1, int par2)
    {
        if ((par1 >= 0) && (par1 < inventoryWidth))
        {
            int k = par1 + (par2 * inventoryWidth);
            return getStackInSlot(k);
        }
        return null;
    }

    @Override
    public String getInvName()
    {
        return "container.crafting";
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (myMaster.getStackInSlot(invOffset + slot) != null)
        {
            ItemStack itemstack = myMaster.getStackInSlot(invOffset + slot);
            myMaster.setInventorySlotContents(invOffset + slot, null);
            return itemstack;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (myMaster.getStackInSlot(invOffset + par1) != null)
        {
            ItemStack itemstack;
            if (myMaster.getStackInSlot(invOffset + par1).stackSize <= par2)
            {
                itemstack = myMaster.getStackInSlot(invOffset + par1);
                myMaster.setInventorySlotContents(invOffset + par1, null);
                eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
            itemstack = myMaster.getStackInSlot(invOffset + par1).splitStack(par2);
            if (myMaster.getStackInSlot(invOffset + par1).stackSize == 0)
            {
                myMaster.setInventorySlotContents(invOffset + par1, null);
            }
            eventHandler.onCraftMatrixChanged(this);
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        myMaster.setInventorySlotContents(invOffset + par1, par2ItemStack);
        eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void onInventoryChanged()
    {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    @Override
    public void openChest()
    {}

    @Override
    public void closeChest()
    {}

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return true;
    }
}