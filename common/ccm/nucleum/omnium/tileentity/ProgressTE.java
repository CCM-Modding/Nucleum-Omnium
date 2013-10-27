package ccm.nucleum.omnium.tileentity;

public class ProgressTE extends ActiveTE
{
    protected int timeLeft;

    /**
     * @return the timeLeft
     */
    public int getTimeLeft()
    {
        return timeLeft;
    }

    /**
     * @param timeLeft
     *            the timeLeft to set
     */
    public void setTimeLeft(int timeLeft)
    {
        this.timeLeft = timeLeft;
    }
}