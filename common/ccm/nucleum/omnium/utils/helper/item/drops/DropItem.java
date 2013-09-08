package ccm.nucleum.omnium.utils.helper.item.drops;

import ccm.nucleum.omnium.utils.helper.item.WrapperStack;

public class DropItem
{
    private final WrapperStack item;
    private final int max;
    private final int min;

    public DropItem(WrapperStack item, int min, int max)
    {
        this.item = item;
        this.max = max;
        this.min = min;
    }

    /**
     * @return the item
     */
    public final WrapperStack getItem()
    {
        return item;
    }

    /**
     * @return the max
     */
    public final int getMax()
    {
        return max;
    }

    /**
     * @return the min
     */
    public final int getMin()
    {
        return min;
    }
}