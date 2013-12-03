/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum.omnium.utils.helper.NBTHelper;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

/**
 * REDO BaseTE
 * <p>
 * Default Implementation for a Tile Entity
 * 
 * @author Captain_Shadows
 */
public class BaseTE extends TileEntity
{
    /**
     * The orientation of the {@link TileEntity}
     */
    private ForgeDirection orientation;

    /**
     * The owner of the {@link TileEntity}
     */
    private String owner;

    /**
     * The custom name of the {@link TileEntity}
     */
    private String customName;

    /**
     * Creates a new {@link BaseTE} Instance.
     */
    public BaseTE()
    {
        orientation = ForgeDirection.SOUTH;
        owner = "";
        customName = "";
    }

    @Override
    public final Packet getDescriptionPacket()
    {
        final NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, nbt);
    }

    /**
     * Gets the {@link TileEntity}'s Custom Name
     * 
     * @return the {@link TileEntity}'s Custom Name
     */
    public String getCustomName()
    {
        return customName;
    }

    /**
     * Gets the {@link TileEntity}'s Orientation
     * 
     * @return The {@link TileEntity}'s Orientation
     */
    public ForgeDirection getOrientation()
    {
        return orientation;
    }

    /**
     * Gets the {@link TileEntity}'s Owner
     * 
     * @return The {@link TileEntity}'s Owner
     */
    public String getOwner()
    {
        return owner;
    }

    /**
     * Checks if the {@link TileEntity} has a Custom Name
     * 
     * @return true if the {@link TileEntity} has a Custom Name
     */
    public boolean hasCustomName()
    {
        return (customName != null) && (customName.length() > 0);
    }

    /**
     * Checks if the {@link TileEntity} has a Owner
     * 
     * @return true if the {@link TileEntity} has a Owner
     */
    public boolean hasOwner()
    {
        return (owner != null) && (owner.length() > 0);
    }

    /**
     * Sets the {@link TileEntity}'s Custom Name
     * 
     * @param customName
     *            A {@link String} with the {@link TileEntity}'s Custom Name
     */
    public void setCustomName(final String customName)
    {
        this.customName = customName;
    }

    /**
     * Sets the {@link TileEntity}'s Orientation
     */
    public void setOrientation(final ForgeDirection orientation)
    {
        this.orientation = orientation;
    }

    @Override
    public final void onDataPacket(final INetworkManager netManager, final Packet132TileEntityData packet)
    {
        readFromNBT(packet.data);
    }

    /**
     * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Direction
     * 
     * @param direction
     *            The {@link ForgeDirection}.ordinal()
     */
    public void setOrientation(final int direction)
    {
        orientation = ForgeDirection.getOrientation(direction);
    }

    /**
     * Sets the Owner of the {@link TileEntity}.
     * 
     * @param owner
     *            A {@link String} with the Owner's Name
     */
    public void setOwner(final String owner)
    {
        this.owner = owner != null ? owner : "Unkown";
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setByte(NBTConstants.NBT_TE_DIRECTION, (byte) orientation.ordinal());
        if (hasOwner())
        {
            nbt.setString(NBTConstants.NBT_TE_OWNER, owner);
        }
        if (hasCustomName())
        {
            nbt.setString(NBTConstants.NBT_TE_CUSTOM_NAME, customName);
        }
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        orientation = ForgeDirection.getOrientation(NBTHelper.getByte(nbt, NBTConstants.NBT_TE_DIRECTION));
        owner = NBTHelper.getString(nbt, NBTConstants.NBT_TE_OWNER);
        customName = NBTHelper.getString(nbt, NBTConstants.NBT_TE_CUSTOM_NAME);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Tile Entity: ");
        sb.append(this.getClass() + "\n");
        return sb.toString();
    }
}