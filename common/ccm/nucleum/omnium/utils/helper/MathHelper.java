/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import java.util.Random;

public class MathHelper
{
    private static Random rand = new Random();

    /**
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
     * @return A random Integer
     */
    public static int getRandInt(final int maxValue)
    {
        return MathHelper.clampInt(1, maxValue, rand.nextInt(maxValue));
    }

    /**
     * @return A random Integer
     */
    public static int getRandInt(final int minValue, final int maxValue)
    {
        return MathHelper.clampInt(minValue, maxValue, rand.nextInt(maxValue));
    }

    public static Random random()
    {
        return rand;
    }
}