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

    public CustomInventoryCrafting(Container container, int width, int height, IInventory masterTile, int startingInventoryIndex)
    {
        super(container, width, height);
        invSize = width * height;
        eventHandler = container;
        inventoryWidth = width;
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
    public ItemStack getStackInRowAndColumn(int x, int y)
    {
        if ((x >= 0) && (x < inventoryWidth))
        {
            int k = x + (y * inventoryWidth);
            return getStackInSlot(k);
        } else
        {
            return null;
        }
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
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (myMaster.getStackInSlot(invOffset + par1) != null)
        {
            ItemStack itemstack = myMaster.getStackInSlot(invOffset + par1);
            myMaster.setInventorySlotContents(invOffset + par1, null);
            return itemstack;
        } else
        {
            return null;
        }
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
            } else
            {
                itemstack = myMaster.getStackInSlot(invOffset + par1).splitStack(par2);

                if (myMaster.getStackInSlot(invOffset + par1).stackSize == 0)
                {
                    myMaster.setInventorySlotContents(invOffset + par1, null);
                }

                eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
        } else
        {
            return null;
        }
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