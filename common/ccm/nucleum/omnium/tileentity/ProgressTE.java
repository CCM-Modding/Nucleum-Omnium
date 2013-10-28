package ccm.nucleum.omnium.tileentity;

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

    /**
     * @param index
     *            the index of the element
     * @return true if this element is up to date
     */
    public boolean isUpdated(int index)
    {
        return getTimedElements()[index].isUpdated();
    }

    /**
     * @param index
     *            the index of the element Updates the recorded time
     */
    public void updateRecord(int index)
    {
        getTimedElements()[index].updateRecord();
    }

    /**
     * @param index
     *            the index of the element Sets the recorded time equal to 0
     */
    public void destroyRecord(int index)
    {
        getTimedElements()[index].destroyRecord();
    }

    /**
     * @param index
     *            the index of the element
     * @param extra
     *            Will get added, thus pass a -1 to subtract
     */
    public void update(int index, int extra)
    {
        getTimedElements()[index].update(extra);
    }

    /**
     * @param index
     *            the index of the element
     * @return the timeLeft
     */
    public int getTimeLeft(int index)
    {
        return getTimedElements()[index].getTimeLeft();
    }

    /**
     * @param index
     *            the index of the element
     * @param timeLeft
     *            the amount of time left
     */
    public void setTimeLeft(int index, int timeLeft)
    {
        getTimedElements()[index].setTimeLeft(timeLeft);
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