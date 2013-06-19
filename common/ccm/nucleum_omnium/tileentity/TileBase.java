package ccm.nucleum_omnium.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_network.PacketTypeHandler;
import ccm.nucleum_network.packet.PacketTileUpdate;
import ccm.nucleum_omnium.helper.InventoryHelper;
import ccm.nucleum_omnium.utils.lib.TileConstant;

public abstract class TileBase extends TileEntity implements IInventory {
    
    private ForgeDirection orientation;
    
    private short          state;
    
    private String         owner;
    
    private String         customName;
    
    /**
     * The ItemStacks that hold the items currently being used in the Tile Entity.
     */
    protected ItemStack[]  inventory;
    
    /**
     * The {@link TileEntity}s Unlocalized name.
     */
    private final String   tileUnloc;
    
    /**
     * Creates a new {@link TileBase} Instance.
     */
    public TileBase(final int invSize, final String name) {
        tileUnloc = name;
        inventory = new ItemStack[invSize];
        orientation = ForgeDirection.SOUTH;
        state = 0;
        owner = "";
        customName = "";
    }
    
    @Override
    public void closeChest() {}// Useless
    
    @Override
    public ItemStack decrStackSize(final int slot, final int amount) {
        return InventoryHelper.decrStackSize(slot, amount, inventory, this);
    }
    
    /**
     * Gets the {@link TileEntity}'s Custom Name.
     * 
     * @return the {@link TileEntity}'s Custom Name.
     */
    public String getCustomName() {
        return customName;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        return PacketTypeHandler.populatePacket(new PacketTileUpdate(xCoord, yCoord, zCoord, orientation, state, owner, customName));
    }
    
    /**
     * Getter Method for the {@link TileEntity}'s Inventory.
     * 
     * @return a Inventory ItemStack[].
     */
    public ItemStack[] getInventory() {
        return inventory;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public String getInvName() {
        return hasCustomName() ? getCustomName() : tileUnloc;
    }
    
    /**
     * Gets the {@link TileEntity}'s Orientation.
     * 
     * @return The {@link TileEntity}'s Orientation.
     */
    public ForgeDirection getOrientation() {
        return orientation;
    }
    
    /**
     * Gets the {@link TileEntity}'s Owner.
     * 
     * @return The {@link TileEntity}'s Owner.
     */
    public String getOwner() {
        return owner;
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
    public ItemStack getStackInSlotOnClosing(final int slot) {
        if (inventory[slot] != null) {
            final ItemStack itemStack = inventory[slot];
            inventory[slot] = null;
            return itemStack;
        } else {
            return null;
        }
    }
    
    /**
     * Gets the {@link TileEntity}'s State.
     * 
     * @return The {@link TileEntity}'s State.
     */
    public short getState() {
        return state;
    }
    
    /**
     * Checks if the {@link TileEntity} has a Custom Name.
     * 
     * @return true if the {@link TileEntity} has a Custom Name.
     */
    public boolean hasCustomName() {
        return (customName != null) && (customName.length() > 0);
    }
    
    /**
     * Checks if the {@link TileEntity} has a Owner.
     * 
     * @return true if the {@link TileEntity} has a Owner.
     */
    public boolean hasOwner() {
        return (owner != null) && (owner.length() > 0);
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
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_Direction)) {
            orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte(TileConstant.NBT_TE_Direction));
        }
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_State)) {
            state = nbtTagCompound.getShort(TileConstant.NBT_TE_State);
        }
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_Owner)) {
            owner = nbtTagCompound.getString(TileConstant.NBT_TE_Owner);
        }
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_Custom_Name)) {
            customName = nbtTagCompound.getString(TileConstant.NBT_TE_Custom_Name);
        }
    }
    
    /**
     * Sets the {@link TileEntity}'s Custom Name.
     * 
     * @param customName
     *            A {@link String} with the {@link TileEntity}'s Custom Name.
     */
    public void setCustomName(final String customName) {
        this.customName = customName;
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
    public void setInventorySlotContents(final int slot, final ItemStack itemstack) {
        inventory[slot] = itemstack;
    }
    
    /**
     * Sets the {@link TileEntity}'s Orientation.
     */
    public void setOrientation(final ForgeDirection orientation) {
        this.orientation = orientation;
    }
    
    /**
     * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Orientation value.
     * 
     * @param orientation
     *            The {@link ForgeDirection} Orientation value.
     */
    public void setOrientation(final int orientation) {
        this.orientation = ForgeDirection.getOrientation(orientation);
    }
    
    /**
     * Sets the Owner of the {@link TileEntity}.
     * 
     * @param owner
     *            A {@link String} with the Owners Name.
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }
    
    /**
     * Sets the {@link TileEntity}'s State to a {@link short} value.
     * 
     * @param state
     *            The {@link short} State value, that you want the {@link TileEntity} to have.
     * @return state The {@link short} State value.
     */
    public void setState(final short state) {
        this.state = state;
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte(TileConstant.NBT_TE_Direction, (byte) orientation.ordinal());
        nbtTagCompound.setShort(TileConstant.NBT_TE_State, state);
        if (hasOwner()) {
            nbtTagCompound.setString(TileConstant.NBT_TE_Owner, owner);
        }
        if (hasCustomName()) {
            nbtTagCompound.setString(TileConstant.NBT_TE_Custom_Name, customName);
        }
    }
}