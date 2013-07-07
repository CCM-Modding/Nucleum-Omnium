package ccm.nucleum_omnium.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_omnium.tileentity.helpers.BaseTEData;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;

public class BaseTE extends TileEntity {

	private BaseTEData	data;

	/**
	 * Creates a new {@link BaseTE} Instance.
	 */
	public BaseTE() {
		data = new BaseTEData(this);
	}

	/**
	 * @return The TileEntity's Logic
	 */
	public ITileLogic getTileLogic() {
		return data.getTileLogic();
	}

	/**
	 * Sets the {@link ITileLogic} of the {@link TileEntity}.
	 * 
	 * @param logic
	 *            A {@link String} with the Owners Name.
	 * @return
	 */
	public BaseTE setLogic(final Class<? extends ITileLogic> logic) {
		data.setLogic(logic);
		return this;
	}

	/**
	 * @return A copy of the current data stored inside of the Tile
	 */
	public BaseTEData getData() {
		return data.clone();
	}

	/**
	 * @return The ordinal of the Tile's orientation
	 */
	public int getOrientationOrdinal() {
		return data.getOrientation().ordinal();
	}

	/**
	 * Sets the {@link TileEntity}'s Custom Name.
	 * 
	 * @param name
	 *            A {@link String} with the {@link TileEntity}'s Custom Name.
	 */
	public void setCustomName(final String name) {
		data.setCustomName(name);
	}

	/**
	 * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Direction
	 * 
	 * @param direction
	 *            The {@link ForgeDirection}.ordinal()
	 */
	public void setOrientation(final int direction) {
		data.setOrientation(direction);
	}

	/**
	 * @return true if the Tile has logic
	 */
	public boolean hasLogic() {
		return data.hasLogic();
	}

	/**
	 * Gets the {@link TileEntity}'s {@code Class<? extends ITileLogic>}
	 * 
	 * @return The {@link TileEntity}'s {@code Class<? extends ITileLogic>}
	 */
	public Class<? extends ITileLogic> getSrcLogic() {
		return data.getSrcLogic();
	}

	@Override
	public void updateEntity() {
		if (hasLogic()) {
			data.getTileLogic().runLogic();
			// LogHandler.log("Logic was ran ... \n");
		}
		super.updateEntity();
	}

	@Override
	public final Packet getDescriptionPacket() {
		final NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, nbt);
	}

	@Override
	public final void onDataPacket(final INetworkManager netManager, final Packet132TileEntityData packet) {
		readFromNBT(packet.customParam1);
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		data.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		data.readFromNBT(nbt);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Tile Entity: ");
		sb.append(this.getClass() + "\n");
		return sb.toString();
	}
}