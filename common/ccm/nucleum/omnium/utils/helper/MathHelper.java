/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import java.util.Random;

import ccm.nucleum.omnium.base.BaseNIC;

public class MathHelper extends BaseNIC
{

    /**
     * @param value
     *            The value to check
     * @param min
     *            The minimum posible value
     * @param max
     *            The maximum posible value
     * @return The value if it is more than the min and less than the max
     */
    public static int clampInt(final int min, final int max, final int value)
    {
        if ((value < min) || (value > max))
        {
            if (value < min)
            {
                return min;
            } else if (value > max)
            {
                return max;
            }
        }
        return value;
    }

    /**
     * @param maxValue
     *            The max value of the Integer
     * @return A random Integer
     */
    public static int getRandInt(final int maxValue)
    {
        return MathHelper.clampInt(1, maxValue, rand.nextInt(maxValue));
    }

    /**
     * @param minValue
     *            The min value of the Integer
     * @param maxValue
     *            The max value of the Integer
     * @return A random Integer
     */
    public static int getRandInt(final int minValue, final int maxValue)
    {
        return MathHelper.clampInt(minValue, maxValue, rand.nextInt(maxValue));
    }

    public static Random rand()
    {
        return rand;
    }
}