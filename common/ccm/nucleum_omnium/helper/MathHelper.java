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
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor_double(final double argDouble)
    {
        final int argDoubleInt = (int) argDouble;
        return argDouble < argDoubleInt ? argDoubleInt - 1 : argDoubleInt;
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

    /**
     * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI /
     * 65536.
     */
    private static float[] SIN_TABLE = new float[65536];

    /**
     * sin looked up in a table
     */
    public static final float sin(final float par0)
    {
        return SIN_TABLE[(int) (par0 * 10430.378F) & 65535];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static final float cos(final float par0)
    {
        return SIN_TABLE[(int) ((par0 * 10430.378F) + 16384.0F) & 65535];
    }

    private MathHelper()
    {}
}