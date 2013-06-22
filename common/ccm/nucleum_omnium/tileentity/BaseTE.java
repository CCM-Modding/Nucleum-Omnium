package ccm.nucleum_omnium.tileentity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum_omnium.utils.lib.TileConstant;

public abstract class BaseTE extends TileEntity {
    
    private ForgeDirection                orientation;
    
    private String                        owner;
    
    private String                        customName;
    
    /**
     * The {@link TileEntity}s Unlocalized name.
     */
    protected final String                tileUnloc;
    
    /**
     * The Source of the logic behind this TileEntity
     */
    protected Class<? extends ITileLogic> srclogic = null;
    
    /**
     * The logic behind this TileEntity
     */
    protected ITileLogic                  logic    = null;
    
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
    public String getUnlocalizedName() {
        return tileUnloc;
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
     * Gets the {@link TileEntity}'s {@link ITileLogic}
     * 
     * @return The {@link TileEntity}'s {@link ITileLogic}
     */
    public ITileLogic getTileLogic() {
        return logic;
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
    public TileEntity setCustomName(final String customName) {
        this.customName = customName;
        return this;
    }
    
    /**
     * Sets the {@link TileEntity}'s Orientation.
     */
    public TileEntity setOrientation(final ForgeDirection orientation) {
        this.orientation = orientation;
        return this;
    }
    
    /**
     * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Orientation value.
     * 
     * @param orientation
     *            The {@link ForgeDirection} Orientation value.
     */
    public TileEntity setOrientation(final int orientation) {
        this.orientation = ForgeDirection.getOrientation(orientation);
        return this;
    }
    
    /**
     * Sets the Owner of the {@link TileEntity}.
     * 
     * @param owner
     *            A {@link String} with the Owners Name.
     */
    public TileEntity setOwner(final String owner) {
        this.owner = owner;
        return this;
    }
    
    /**
     * Sets the {@link ITileLogic} of the {@link TileEntity}.
     * 
     * @param logic
     *            A {@link String} with the Owners Name.
     * @return
     */
    public TileEntity setLogic(final Class<? extends ITileLogic> logic) {
        this.srclogic = logic;
        return this;
    }
    
    @Override
    public void updateEntity() {
        if (logic != null) {
            logic.runLogic();
        } else if (srclogic != null) {
            Constructor<? extends ITileLogic> c;
            try {
                c = srclogic.getConstructor(TileEntity.class);
                logic = c.newInstance(this);
            } catch (NoSuchMethodException e) {
                Handler.log(String.format("Loading the logic for: \n %s has failed (%s)", toString(), e.toString()));
                e.printStackTrace();
            } catch (SecurityException e) {
                Handler.log(String.format("Loading the logic for: \n %s has failed (%s)", toString(), e.toString()));
                e.printStackTrace();
            } catch (InstantiationException e) {
                Handler.log(String.format("Loading the logic for: \n %s has failed (%s)", toString(), e.toString()));
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Handler.log(String.format("Loading the logic for: \n %s has failed (%s)", toString(), e.toString()));
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                Handler.log(String.format("Loading the logic for: \n %s has failed (%s)", toString(), e.toString()));
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                Handler.log(String.format("Loading the logic for: \n %s has failed (%s)", toString(), e.toString()));
                e.printStackTrace();
            }
        }
        super.updateEntity();
    }
    
    /*
     * public void sendUpdatePacket(Side side) { if (side == Side.CLIENT) { PacketUtils.sendToPlayers(getDescriptionPacket(), this); this.worldObj.updateAllLightTypes(this.xCoord,
     * this.yCoord, this.zCoord); } else if (side == Side.SERVER) { PacketUtils.sendToServer(getDescriptionPacket()); } }
     */
    
    @Override
    public final Packet getDescriptionPacket() {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, var1);
    }
    
    @Override
    public final void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
        this.readFromNBT(packet.customParam1);
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tile Entity: ");
        sb.append(tileUnloc + "\n");
        sb.append(String.format("At %s, %s, %s ", xCoord, yCoord, zCoord));
        sb.append(String.format("In %s \n", worldObj.getWorldInfo() == null ? "????" : worldObj.getWorldInfo().getWorldName()));
        return sb.toString();
    }
}