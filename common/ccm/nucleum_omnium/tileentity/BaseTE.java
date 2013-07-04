package ccm.nucleum_omnium.tileentity;

import java.lang.reflect.Constructor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_omnium.handler.LogHandler;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum_omnium.utils.lib.TileConstant;

public class BaseTE extends TileEntity {

	private ForgeDirection					orientation;

	private String							owner;

	private String							customName;

	private boolean							hasLogic	= false;

	/**
	 * The Source of the logic behind this TileEntity
	 */
	protected Class<? extends ITileLogic>	srclogic	= null;

	/**
	 * The logic behind this TileEntity
	 */
	protected ITileLogic					logic		= null;

	/**
	 * Creates a new {@link BaseTE} Instance.
	 */
	public BaseTE() {
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
	 * Gets the {@link TileEntity}'s {@link ITileLogic}
	 * 
	 * @return The {@link TileEntity}'s {@link ITileLogic}
	 */
	public ITileLogic getTileLogic() {
		return logic;
	}

	/**
	 * Gets the {@link TileEntity}'s {@code Class<? extends ITileLogic>}
	 * 
	 * @return The {@link TileEntity}'s {@code Class<? extends ITileLogic>}
	 */
	public Class<? extends ITileLogic> getSrcLogic() {
		return srclogic;
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

	public boolean hasLogic() {
		return hasLogic;
	}

	/**
	 * Sets the {@link TileEntity}'s Custom Name.
	 * 
	 * @param customName
	 *            A {@link String} with the {@link TileEntity}'s Custom Name.
	 */
	public BaseTE setCustomName(final String customName) {
		this.customName = customName;
		return this;
	}

	/**
	 * Sets the {@link TileEntity}'s Orientation.
	 */
	public BaseTE setOrientation(final ForgeDirection orientation) {
		this.orientation = orientation;
		return this;
	}

	/**
	 * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Orientation value.
	 * 
	 * @param orientation
	 *            The {@link ForgeDirection} Orientation value.
	 */
	public BaseTE setOrientation(final int orientation) {
		this.orientation = ForgeDirection.getOrientation(orientation);
		return this;
	}

	/**
	 * Sets the Owner of the {@link TileEntity}.
	 * 
	 * @param owner
	 *            A {@link String} with the Owners Name.
	 */
	public BaseTE setOwner(final String owner) {
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
	public BaseTE setLogic(final Class<? extends ITileLogic> logic) {
		hasLogic = true;
		srclogic = logic;
		return this;
	}

	@Override
	public void updateEntity() {
		if (logic != null) {
			logic.runLogic();
			LogHandler.log("Logic was ran ... \n");
		} else if (srclogic != null) {
			LogHandler.log("Logic was NULL Instanciating ... \n");
			Constructor<? extends ITileLogic> c = null;

			try {
				c = srclogic.getConstructor(TileEntity.class);
			} catch (final Exception e) {
				e.printStackTrace();
				LogHandler.log(String.format(	"Loading the logic for: \n %s has failed to get the Constructor \n %s",
												toString(),
												e.toString()));
			}
			try {
				logic = c.newInstance(this);
			} catch (final Exception e) {
				LogHandler.log(String.format(	"Loading the logic for: \n %s has failed to create a new Instance \n %s",
												toString(),
												e.toString()));
				e.printStackTrace();
			}

		}
		super.updateEntity();
	}

	/*
	 * public void sendUpdatePacket(Side side) { if (side == Side.CLIENT) {
	 * PacketUtils.sendToPlayers(getDescriptionPacket(), this);
	 * this.worldObj.updateAllLightTypes(this.xCoord,
	 * this.yCoord, this.zCoord); } else if (side == Side.SERVER) {
	 * PacketUtils.sendToServer(getDescriptionPacket()); } }
	 */

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
		final StringBuilder sb = new StringBuilder();
		sb.append("Tile Entity: ");
		sb.append(this.getClass() + "\n");
		// sb.append(String.format("At %s, %s, %s ", xCoord, yCoord, zCoord));
		// sb.append(String.format("In %s \n", worldObj.getWorldInfo() == null ? "????" :
		// worldObj.getWorldInfo().getWorldName()));
		return sb.toString();
	}
}