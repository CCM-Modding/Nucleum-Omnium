package ccm.nucleum_omnium.tileentity.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This Interface is used to create custom logic for TileEntitys without having to create a Custom
 * TileEntity
 * 
 * @author Captain_Shadows
 */
public interface ITileLogic {

	/**
	 * This method gets called by updateEntity() inside of the TileEntity
	 */
	public void runLogic();

	/**
	 * Checks if the the TE Logic is allowed to process the Item
	 * 
	 * @return true if it can
	 */
	public boolean canRun();

	/**
	 * Processes the Item
	 */
	public void run();

	/**
	 * Called when the tile is reading it's state from NBT
	 * <p>
	 * Time to restore the Invariants
	 * 
	 * @param nbt
	 */
	public void readFromNBT(final NBTTagCompound nbt);

	/**
	 * Called when the tile is writing it's state to NBT
	 * <p>
	 * Time to store the Invariants
	 * 
	 * @param nbt
	 */
	public void writeToNBT(final NBTTagCompound nbt);

	/**
	 * @param slot
	 *            The slot trying to insert into
	 * @param itemstack
	 *            The stack trying to put
	 * @return true if automation is allowed to insert the given stack into
	 *         the given slot
	 */
	public boolean isStackValidForSlot(final int slot, final ItemStack itemstack);
}
