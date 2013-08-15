/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity.logic;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import ccm.nucleum.omnium.tileentity.interfaces.ITileLogic;

/**
 * Simple Abstract implementation of ITileLogic that allows the user to not have to implement read or write if not needed, AKA if the logic has no variables to save
 * 
 * @author Captain_Shadows
 */
public abstract class BaseLogic implements ITileLogic
{

    @Override
    public boolean isStackValidForSlot(final int slot, final ItemStack itemstack)
    {
        return false;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt)
    {}

    @Override
    public void writeToNBT(final NBTTagCompound nbt)
    {}
}