package ccm.nucleum.omnium.inventory.container.element;

import net.minecraft.nbt.NBTTagCompound;

import ccm.nucleum.omnium.utils.helper.NBTHelper;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

public interface IContainerElement
{
    public void writeToNBT(NBTTagCompound nbt);
}