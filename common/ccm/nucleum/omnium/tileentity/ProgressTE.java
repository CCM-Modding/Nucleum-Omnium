package ccm.nucleum.omnium.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import ccm.nucleum.omnium.inventory.container.element.TimedElement;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

public class ProgressTE extends ActiveTE
{
    protected TimedElement[] progresses;

    public TimedElement[] getTimedElements()
    {
        return progresses;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList(NBTConstants.NBT_CONTAINER_ELEMENT + "S");
        NBTTagCompound tmpNBT;
        for (int i = 0; i < list.tagCount(); i++)
        {
            tmpNBT = (NBTTagCompound) list.tagAt(i);
            progresses[tmpNBT.getInteger(NBTConstants.NBT_CONTAINER_ELEMENT_TIMED)] = TimedElement.loadFromNBT(tmpNBT);
        }
    }

    @Override
    public void writeSubsNBT(NBTTagCompound nbt)
    {
        super.writeSubsNBT(nbt);
        NBTTagCompound tmpNBT;
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < progresses.length; i++)
        {
            if (progresses[i] == null)
            {
                continue;
            }
            tmpNBT = new NBTTagCompound();
            tmpNBT.setInteger(NBTConstants.NBT_CONTAINER_ELEMENT_TIMED, i);
            progresses[i].writeToNBT(tmpNBT);
            list.appendTag(tmpNBT);
        }
        nbt.setTag(NBTConstants.NBT_CONTAINER_ELEMENT + "S", list);
    }
}