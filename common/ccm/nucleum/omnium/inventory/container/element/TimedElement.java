package ccm.nucleum.omnium.inventory.container.element;

import net.minecraft.nbt.NBTTagCompound;
import ccm.nucleum.omnium.utils.helper.NBTHelper;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

public class TimedElement implements IContainerElement
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
    public TimedElement updateRecord()
    {
        recordedTime = timeLeft;
        return this;
    }

    /**
     * Sets the recorded time equal to 0
     */
    public TimedElement destroyRecord()
    {
        recordedTime = 0;
        return this;
    }

    /**
     * @param extra
     *            Will get added, thus pass a -1 to subtract
     */
    public TimedElement update(int extra)
    {
        timeLeft += extra;
        return this;
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
    public TimedElement setTimeLeft(int timeLeft)
    {
        this.timeLeft = timeLeft;
        return this;
    }

    @Override
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