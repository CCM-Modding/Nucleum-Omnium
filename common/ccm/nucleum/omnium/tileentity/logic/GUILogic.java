/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity.logic;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import ccm.nucleum.omnium.tileentity.interfaces.IGUITileLogic;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

/**
 * GUILogic
 * <p>
 * Default implementation of IGUITileLogic
 * 
 * @author Captain_Shadows
 */
public abstract class GUILogic extends BaseLogic implements IGUITileLogic
{

    /** The number of ticks that the current item Has been "cooked" for */
    public int progress = 0;

    @Override
    public int getMaxTime(final ItemStack item)
    {
        return 200;
    }

    @Override
    public int getProgressScaled(final int scale)
    {
        return (progress * scale) / getMaxTime(null);
    }

    @Override
    public int getTimeLeft()
    {
        return progress;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt)
    {
        if (nbt.hasKey(NBTConstants.NBT_TE_PROGRESS))
        {
            progress = nbt.getInteger(NBTConstants.NBT_TE_PROGRESS);
        }
    }

    @Override
    public void setTimeLeft(final int time)
    {
        progress = time;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt)
    {
        nbt.setInteger(NBTConstants.NBT_TE_PROGRESS, progress);
    }
}