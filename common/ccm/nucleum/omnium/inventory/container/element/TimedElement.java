package ccm.nucleum.omnium.inventory.container.element;

import net.minecraft.nbt.NBTTagCompound;

import ccm.nucleum.omnium.utils.helper.NBTHelper;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

public class TimedElement
{
    protected int timeLeft;
    protected int recordedTime;

    /**
     * @return true if this element is up to date
     */
    public boolean isUpdated()
    {
        return recordedTime == timeLeft;
    }

    /**
     * Updates the recorded time
     */
    public void updateRecord()
    {
        recordedTime = timeLeft;
    }

    /**
     * Sets the recorded time equal to 0
     */
    public void destroyRecord()
    {
        recordedTime = 0;
    }

    /**
     * @param extra
     *            Will get added, thus pass a -1 to subtract
     */
    public void update(int extra)
    {
        timeLeft += extra;
    }

    /**
     * @return the timeLeft
     */
    public int getTimeLeft()
    {
        return timeLeft;
    }

    /**
     * @param timeLeft
     *            the amount of time left
     */
    public void setTimeLeft(int timeLeft)
    {
        this.timeLeft = timeLeft;
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger(NBTConstants.NBT_CONTAINER_ELEMENT_TIMED_LEFT, getTimeLeft());
        nbt.setInteger(NBTConstants.NBT_CONTAINER_ELEMENT_TIMED_RECORD, recordedTime);
    }

    public static TimedElement loadFromNBT(NBTTagCompound nbt)
    {
        TimedElement e = new TimedElement();
        e.setTimeLeft(NBTHelper.getInteger(nbt, NBTConstants.NBT_CONTAINER_ELEMENT_TIMED_LEFT));
        e.recordedTime = NBTHelper.getInteger(nbt, NBTConstants.NBT_CONTAINER_ELEMENT_TIMED_RECORD);
        return e;
    }
}