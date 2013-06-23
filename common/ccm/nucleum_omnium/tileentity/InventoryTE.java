package ccm.nucleum_omnium.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ccm.nucleum_omnium.helper.FunctionHelper;
import ccm.nucleum_omnium.helper.InventoryHelper;
import ccm.nucleum_omnium.utils.lib.TileConstant;

public class InventoryTE extends BaseTE implements IInventory {
    
    /**
     * The ItemStacks that hold the items currently being used in the Tile Entity.
     */
    protected ItemStack[] inventory;
    
    /**
     * Set's the size of the Inventory
     * 
     * @return
     */
    public InventoryTE setInventorySize(final int size) {
        inventory = new ItemStack[size];
        return this;
    }
    
    /**
     * Getter Method for the {@link TileEntity}'s Inventory.
     * 
     * @return a Inventory ItemStack[].
     */
    public ItemStack[] getInventory() {
        return inventory;
    }
    
    /**
     * Setter Method for the {@link TileEntity}'s Inventory.
     * 
     * @param inventory
     *            The ItemStack[] for the Inventory.
     */
    public void setInventory(final ItemStack[] inventory) {
        this.inventory = inventory;
    }
    
    @Override
    public int getSizeInventory() {
        return inventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int slot) {
        return inventory[slot];
    }
    
    @Override
    public void setInventorySlotContents(final int slot, final ItemStack itemstack) {
        inventory[slot] = itemstack;
    }
    
    @Override
    public ItemStack decrStackSize(final int slot, final int amount) {
        return InventoryHelper.decrStackSize(slot, amount, inventory, this);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int slot) {
        if (inventory[slot] != null) {
            final ItemStack itemStack = inventory[slot];
            inventory[slot] = null;
            return itemStack;
        } else {
            return null;
        }
    }
    
    @Override
    public String getInvName() {
        return hasCustomName() ? getCustomName() : FunctionHelper.getTEName(worldObj, xCoord, yCoord, zCoord);
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return hasCustomName();
    }
    
    @Override
    public boolean isStackValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }
    
    /**
     * Checks if the {@link TileEntity} is Usable By a Player.
     * 
     * @param player
     *            The player that is using the {@link TileEntity}.
     * @return true if the player is within 10 blocks.
     */
    @Override
    public boolean isUseableByPlayer(final EntityPlayer player) {
        return player.getDistance(xCoord, yCoord, zCoord) <= 10;
    }
    
    @Override
    public void openChest() {}// Useless
    
    @Override
    public void closeChest() {}// Useless
    
    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        setInventory(InventoryHelper.readInventoryFromNBT(nbt.getTagList(TileConstant.INVENTORY), getSizeInventory()));
    }
    
    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag(TileConstant.INVENTORY, InventoryHelper.writeInventoryToNBT(getInventory()));
    }
}