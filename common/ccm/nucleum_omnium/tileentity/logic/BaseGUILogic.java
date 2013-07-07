package ccm.nucleum_omnium.tileentity.logic;

import net.minecraft.nbt.NBTTagCompound;
import ccm.nucleum_omnium.tileentity.interfaces.IGUITileLogic;
import ccm.nucleum_omnium.utils.lib.TileConstant;

public abstract class BaseGUILogic extends BaseLogic implements IGUITileLogic {

	/** The number of ticks that the current item has been Grilling for */
	public int	progress	= 0;

	@Override
	public int getProgressScaled(final int scale) {
		return (progress * scale) / 200;
	}

	@Override
	public int getTimeLeft() {
		return progress;
	}

	@Override
	public void setTimeLeft(final int time) {
		progress = time;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey(TileConstant.NBT_TE_PROGRESS)) {
			progress = nbt.getInteger(TileConstant.NBT_TE_PROGRESS);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(TileConstant.NBT_TE_PROGRESS, progress);
	}
}