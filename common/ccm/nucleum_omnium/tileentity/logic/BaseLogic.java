package ccm.nucleum_omnium.tileentity.logic;

import net.minecraft.nbt.NBTTagCompound;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;

/**
 * Simple Abstract implementation of ITileLogic that allows the user to not have to implement read
 * or write if not needed, AKA if the logic has no invariants to save
 * 
 * @author Captain_Shadows
 */
public abstract class BaseLogic implements ITileLogic {

	@Override
	public void readFromNBT(NBTTagCompound nbt) {}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {}
}