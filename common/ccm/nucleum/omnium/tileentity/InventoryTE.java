/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ccm.nucleum.omnium.utils.helper.NBTHelper;
import ccm.nucleum.omnium.utils.helper.item.InventoryHelper;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

/**
 * REDO InventoryTE
 * <p>
 * Default Implementation for a Tile Entity with an Inventory
 * 
 * @author Captain_Shadows
 */
public class InventoryTE extends BaseTE implements IInventory
{

    /**
     * The ItemStacks that hold the items currently being used in the Tile Entity
     */
    protected ItemStack[] inventory;

    /**
     * Size of the Inventory
     */
    private int size = 0;

    @Override
    public ItemStack decrStackSize(final int slot, final int amount)
    {
        return InventoryHelper.decrStackSize(this, slot, amount);
    }

    /**
     * Getter Method for the {@link TileEntity}'s Inventory
     * 
     * @return a Inventory ItemStack[]
     */
    public ItemStack[] getInventory()
    {
        return inventory;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInvName()
    {
        return hasCustomName() ? getCustomName() : "";// FunctionHelper.getTEName(worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public int getSizeInventory()
    {
        return size;
    }

    @Override
    public ItemStack getStackInSlot(final int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(final int slot)
    {
        if (getStackInSlot(slot) != null)
        {
            final ItemStack itemStack = getStackInSlot(slot);
            inventory[slot] = null;
            return itemStack;
        }
        return null;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return hasCustomName();
    }

    @Override
    public boolean isItemValidForSlot(final int slot, final ItemStack itemstack)
    {
        return false;
    }

    /**
     * Checks if the {@link TileEntity} is Usable By a Player
     * 
     * @param player
     *            The player that is using the {@link TileEntity}
     * @return true if the player is within 10 blocks
     */
    @Override
    public boolean isUseableByPlayer(final EntityPlayer player)
    {
        return player.getDistance(xCoord, yCoord, zCoord) <= 10;
    }

    /**
     * Setter Method for the {@link TileEntity}'s Inventory
     * 
     * @param inventory
     *            The ItemStack[] for the Inventory
     */
    public void setInventory(final ItemStack[] inventory)
    {
        this.inventory = inventory;
    }

    /**
     * Sets the size of the itemstack at the specified slot
     * 
     * @param slot
     *            The slot
     * @param size
     *            The new size of the itemstack
     */
    public void setSize(final int slot, final int size)
    {
        inventory[slot].stackSize = size;
    }

    /**
     * Changes the size of the itemstack at the specified slot, by adding it to the existing size
     * 
     * @param slot
     *            The slot
     * @param size
     *            The new size of the itemstack
     */
    public void changeSize(final int slot, final int size)
    {
        inventory[slot].stackSize += size;
    }

    /**
     * Set's the size of the Inventory
     * 
     * @return the tile
     */
    public InventoryTE setInventorySize(final int size)
    {
        setInventory(new ItemStack[size]);
        this.size = size;
        return this;
    }

    @Override
    public void setInventorySlotContents(final int slot, final ItemStack itemstack)
    {
        inventory[slot] = itemstack;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger(NBTConstants.NBT_INVENTORY_SIZE, getSizeInventory());
        nbt.setTag(NBTConstants.NBT_INVENTORY, InventoryHelper.writeInventoryToNBT(getInventory()));
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        setInventorySize(NBTHelper.getInteger(nbt, NBTConstants.NBT_INVENTORY_SIZE));

        if (nbt.hasKey(NBTConstants.NBT_INVENTORY))
        {
            setInventory(InventoryHelper.readInventoryFromNBT(nbt.getTagList(NBTConstants.NBT_INVENTORY), getSizeInventory()));
        }
    }

    @Override
    public void closeChest()
    {}// Useless

    @Override
    public void openChest()
    {}// Useless
}