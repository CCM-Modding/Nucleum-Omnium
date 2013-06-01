package ccm.nucleum_omnium.helper;

import java.util.Random;

public class MathHelper
{

    protected static Random      rand      = new Random();

    public static final double   PHI       = 1.618034;

    /**
     * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI /
     * 65536.
     */
    public static final double[] SIN_TABLE = new double[65536];

    static{
        for (int i = 0; i < 65536; i++){
            SIN_TABLE[i] = Math.sin((i / 65536D) * 2 * Math.PI);
        }
        SIN_TABLE[0] = 0;
        SIN_TABLE[16384] = 1;
        SIN_TABLE[32768] = 0;
        SIN_TABLE[49152] = 1;
    }

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
    public static int floor_double(final double d)
    {

        return net.minecraft.util.MathHelper.floor_double(d);
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

    public static double sin(final double d)
    {

        return SIN_TABLE[(int) ((float) d * 10430.378F) & 65535];
    }

    public static double cos(final double d)
    {

        return SIN_TABLE[(int) (((float) d * 10430.378F) + 16384.0F) & 65535];
    }

    private MathHelper()
    {}
}