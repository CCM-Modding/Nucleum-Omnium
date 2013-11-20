package ccm.nucleum.omnium.inventory.container.element;

import net.minecraft.nbt.NBTTagCompound;

public interface IContainerElement
{
    public void writeToNBT(NBTTagCompound nbt);
}