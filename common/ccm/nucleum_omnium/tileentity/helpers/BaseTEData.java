package ccm.nucleum_omnium.tileentity.helpers;

import java.lang.reflect.Constructor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_omnium.handler.LogHandler;
import ccm.nucleum_omnium.tileentity.BaseTE;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum_omnium.utils.lib.TileConstant;

public final class BaseTEData {

	/**
	 * The orientation of the TileEntity
	 */
	private ForgeDirection				orientation;

	/**
	 * The owner of the TileEntity
	 */
	private String						owner;

	/**
	 * The custom name of the TileEntity
	 */
	private String						customName;

	/**
	 * Weather the Tile has logic or not
	 */
	private boolean						hasLogic;

	/**
	 * The Source of the logic behind the TileEntity
	 */
	private Class<? extends ITileLogic>	srclogic;

	/**
	 * The logic behind the TileEntity
	 */
	private ITileLogic					logic;

	private final BaseTE				tile;

	public BaseTEData(BaseTE tile) {
		orientation = ForgeDirection.SOUTH;
		owner = "";
		customName = "";
		hasLogic = false;
		srclogic = null;
		logic = null;
		this.tile = tile;
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
	private void loadLogic() {
		if (srclogic != null) {
			Constructor<? extends ITileLogic> c = null;

			final String failConst = "Logic loader has failed to get the Constructor for: \n %s With error: \n %s";

			try {
				c = srclogic.getConstructor(TileEntity.class);
			} catch (Exception e) {
				LogHandler.log(failConst, toString(), e.toString());
				if (e.getCause() != null) {
					LogHandler.log("\nAnd Cause: %s", e.getCause());
				}
				e.printStackTrace();
			}

			final String failInst = "Logic loader has failed to create a new Instance of: \n %s With error: \n %s";

			try {
				logic = c.newInstance(tile);
			} catch (Exception e) {
				LogHandler.log(failInst, toString(), e.toString());
				if (e.getCause() != null) {
					LogHandler.log("\nAnd Cause: %s", e.getCause());
				}
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("\nsrclogic WAS NULL!!!! \n");
		}
	}

	public ITileLogic getTileLogic() {
		if (logic == null) {
			loadLogic();
		}
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

	/**
	 * @return true if the Tile has logic
	 */
	public boolean hasLogic() {
		return hasLogic;
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
	 * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Direction
	 * 
	 * @param direction
	 *            The {@link ForgeDirection}.ordinal()
	 */
	public void setOrientation(final int direction) {
		this.orientation = ForgeDirection.getOrientation(direction);
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
	 * Sets the {@link ITileLogic} of the {@link TileEntity}.
	 * 
	 * @param logic
	 *            A {@link String} with the Owners Name.
	 * @return
	 */
	public void setLogic(final Class<? extends ITileLogic> logic) {
		hasLogic = true;
		srclogic = logic;
	}

	public void writeToNBT(final NBTTagCompound nbt) {
		LogHandler.log("Writing: %s to NBT", tile);
		nbt.setByte(TileConstant.NBT_TE_DIRECTION, (byte) orientation.ordinal());
		if (hasOwner()) {
			nbt.setString(TileConstant.NBT_TE_OWNER, owner);
		}
		if (hasCustomName()) {
			nbt.setString(TileConstant.NBT_TE_CUSTOM_NAME, customName);
		}
		if (hasLogic()) {
			if (srclogic != null) {
				nbt.setString(TileConstant.NBT_TE_SRC_LOGIC, srclogic.getName());
				// LogHandler.log(srclogic.getName());
			} else {
				LogHandler.log("%sSource Logic was null when tring to save to NBT!", tile);
			}
			if (logic != null) {
				logic.writeToNBT(nbt);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void readFromNBT(final NBTTagCompound nbt) {
		LogHandler.log("Reading: %s from NBT", tile);
		if (nbt.hasKey(TileConstant.NBT_TE_DIRECTION)) {
			orientation = ForgeDirection.getOrientation(nbt.getByte(TileConstant.NBT_TE_DIRECTION));
		}
		if (nbt.hasKey(TileConstant.NBT_TE_OWNER)) {
			owner = nbt.getString(TileConstant.NBT_TE_OWNER);
		}
		if (nbt.hasKey(TileConstant.NBT_TE_CUSTOM_NAME)) {
			customName = nbt.getString(TileConstant.NBT_TE_CUSTOM_NAME);
		}
		if (nbt.hasKey(TileConstant.NBT_TE_SRC_LOGIC)) {
			String tmp = nbt.getString(TileConstant.NBT_TE_SRC_LOGIC);
			LogHandler.log(tmp);
			try {
				srclogic = (Class<? extends ITileLogic>) Class.forName(tmp);
			} catch (ClassNotFoundException e) {
				final String failNBT = "Logic loader has failed to find a class named: \n %s With error: \n %s";
				LogHandler.log(failNBT, tmp, e.toString());
				e.printStackTrace();
			}
		}
		if (hasLogic() && logic != null) {
			logic.readFromNBT(nbt);
		}
	}

	@Override
	public BaseTEData clone() {
		BaseTEData tmp = new BaseTEData(tile);
		tmp.setCustomName(getCustomName());
		tmp.setLogic(getSrcLogic());
		tmp.setOrientation(getOrientation());
		tmp.setOwner(getOwner());
		return tmp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customName == null) ? 0 : customName.hashCode());
		result = prime * result + (hasLogic ? 1231 : 1237);
		result = prime * result + ((logic == null) ? 0 : logic.hashCode());
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((srclogic == null) ? 0 : srclogic.hashCode());
		result = prime * result + ((tile == null) ? 0 : tile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseTEData)) {
			return false;
		}
		BaseTEData other = (BaseTEData) obj;
		if (customName == null) {
			if (other.customName != null) {
				return false;
			}
		} else if (!customName.equals(other.customName)) {
			return false;
		}
		if (hasLogic != other.hasLogic) {
			return false;
		}
		if (logic == null) {
			if (other.logic != null) {
				return false;
			}
		} else if (!logic.equals(other.logic)) {
			return false;
		}
		if (orientation != other.orientation) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (srclogic == null) {
			if (other.srclogic != null) {
				return false;
			}
		} else if (!srclogic.equals(other.srclogic)) {
			return false;
		}
		if (tile == null) {
			if (other.tile != null) {
				return false;
			}
		} else if (!tile.equals(other.tile)) {
			return false;
		}
		return true;
	}
}