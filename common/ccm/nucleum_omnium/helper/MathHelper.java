package ccm.nucleum_omnium.helper;

import java.util.Random;

public class MathHelper
{

    protected static Random rand = new Random();

    /**
     * Checks if the value is within the specified parameters
     * 
     * @param value
     *            The value to check
     * @param min
     *            The minimum posible value
     * @param max
     *            The maximum posible value
     * @return The value if it is more than the min and less than the max
     */
    public static int clampInt(final int value, final int min, final int max)
    {
        if (value < min){
            return min;
        }
        if (value > max){
            return max;
        }
        return value;
    }

    /**
     * Gets a random Integer
     * 
     * @param maxValue
     *            The max value of the Integer
     * @return the random Integer
     */
    public static int getRandomInt(final int maxValue)
    {
        return rand.nextInt(maxValue);
    }

    /**
     * Gets a random Integer
     * 
     * @param maxValue
     *            The max value of the Integer
     * @return the random Integer (0 not being a option)
     */
    public static int getRandomNon0Int(final int maxValue)
    {
        return clampInt(rand.nextInt(maxValue), 1, maxValue);
    }

    private MathHelper()
    {}
}