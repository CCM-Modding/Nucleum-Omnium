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

import ccm.nucleum.omnium.tileentity.helpers.BaseTEData;

/**
 * BaseTE
 * <p>
 * Default Implementation for a Tile Entity
 * 
 * @author Captain_Shadows
 */
public class BaseTE extends TileEntity
{

    private final BaseTEData data;

    /**
     * Creates a new {@link BaseTE} Instance.
     */
    public BaseTE()
    {
        data = new BaseTEData(this);
    }

    /**
     * @return A copy of the current data stored inside of the Tile
     */
    public BaseTEData getData()
    {
        return data.clone();
    }

    @Override
    public final Packet getDescriptionPacket()
    {
        final NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, nbt);
    }

    /**
     * @return The ordinal of the Tile's orientation
     */
    public int getOrientationOrdinal()
    {
        return data.getOrientation().ordinal();
    }

    @Override
    public final void onDataPacket(final INetworkManager netManager, final Packet132TileEntityData packet)
    {
        readFromNBT(packet.data);
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        data.readFromNBT(nbt);
    }

    /**
     * Sets the {@link TileEntity}'s Custom Name.
     * 
     * @param name
     *            A {@link String} with the {@link TileEntity}'s Custom Name.
     */
    public void setCustomName(final String name)
    {
        data.setCustomName(name);
    }

    /**
     * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Direction
     * 
     * @param direction
     *            The {@link ForgeDirection}.ordinal()
     */
    public void setOrientation(final int direction)
    {
        data.setOrientation(direction);
    }

    /**
     * Sets the Owner of the {@link TileEntity}.
     * 
     * @param owner
     *            A {@link String} with the Owner's Name.
     */
    public void setOwner(final String owner)
    {
        data.setOwner(owner);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Tile Entity: ");
        sb.append(this.getClass() + "\n");
        return sb.toString();
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        data.writeToNBT(nbt);
    }
}