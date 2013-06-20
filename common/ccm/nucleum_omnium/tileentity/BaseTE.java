package ccm.nucleum_omnium.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_network.PacketHandler;
import ccm.nucleum_network.packet.PacketTile;
import ccm.nucleum_network.packet.Payload;
import ccm.nucleum_network.packet.helpers.PacketUtils;
import ccm.nucleum_network.packet.interfaces.ITilePacketHandler;
import ccm.nucleum_omnium.utils.lib.TileConstant;
import cpw.mods.fml.relauncher.Side;

public abstract class BaseTE extends TileEntity implements ITilePacketHandler {
    
    private ForgeDirection orientation;
    
    private String         owner;
    
    private String         customName;
    
    private static int     descPacketId = PacketHandler.getAvailablePacketId();
    
    /**
     * The {@link TileEntity}s Unlocalized name.
     */
    protected final String tileUnloc;
    
    /**
     * Creates a new {@link BaseTE} Instance.
     */
    public BaseTE(final String name) {
        tileUnloc = name;
        orientation = ForgeDirection.SOUTH;
        owner = "";
        customName = "";
    }
    
    /**
     * Gets the {@link TileEntity}'s Custom Name.
     * 
     * @return the {@link TileEntity}'s Custom Name.
     */
    public String getCustomName() {
        return customName;
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
    
    @Override
    public void updateEntity() {
        super.updateEntity();
    }
    
    public void sendUpdatePacket(Side side) {
        if (side == Side.CLIENT) {
            PacketUtils.sendToPlayers(getDescriptionPacket(), this);
            
            this.worldObj.updateAllLightTypes(this.xCoord, this.yCoord, this.zCoord);
        } else if (side == Side.SERVER) {
            PacketUtils.sendToServer(getDescriptionPacket());
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        Payload payload = new Payload(0, 1, 0, 0, 2);
        
        payload.bytePayload[0] = (byte) this.orientation.ordinal();
        
        payload.stringPayload[0] = this.owner;
        payload.stringPayload[1] = this.customName;
        
        PacketTile packet = new PacketTile(descPacketId, this.xCoord, this.yCoord, this.zCoord, payload);
        return packet.getPacket();
    }
    
    @Override
    public void handleTilePacket(PacketTile packet) {
        
        this.orientation = ForgeDirection.values()[packet.payload.bytePayload[0]];
        
        this.owner = packet.payload.stringPayload[0];
        this.customName = packet.payload.stringPayload[1];
        
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.worldObj.updateAllLightTypes(this.xCoord, this.yCoord, this.zCoord);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getWorldObj().getBlockId(this.xCoord, this.yCoord, this.zCoord));
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte(TileConstant.NBT_TE_Direction, (byte) orientation.ordinal());
        if (hasOwner()) {
            nbtTagCompound.setString(TileConstant.NBT_TE_Owner, owner);
        }
        if (hasCustomName()) {
            nbtTagCompound.setString(TileConstant.NBT_TE_Custom_Name, customName);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_Direction)) {
            orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte(TileConstant.NBT_TE_Direction));
        }
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_Owner)) {
            owner = nbtTagCompound.getString(TileConstant.NBT_TE_Owner);
        }
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_Custom_Name)) {
            customName = nbtTagCompound.getString(TileConstant.NBT_TE_Custom_Name);
        }
    }
}