package ccm.nucleum.helper;

public class MathHelper extends BaseHelper
{

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
     * @param zero
     *            If the number returned is allowed to be 0 the pass in true, otherwise pass in
     *            false. If you pass in false then the minimum will be 1. For higher minimums check
     *            {@code getRandomInt(int minValue, int maxValue)}
     * @return the random Integer
     */
    public static int getRandomInt(final int maxValue, final boolean zero)
    {
        if (zero){
            return rand.nextInt(maxValue);
        }else{
            return clampInt(rand.nextInt(maxValue), 1, maxValue);
        }
    }

    /**
     * Gets a random Integer
     * 
     * @param minValue
     *            The min value of the Integer
     * @param maxValue
     *            The max value of the Integer
     * @return the random Integer
     */
    public static int getRandomInt(final int minValue, final int maxValue)
    {

        return clampInt(rand.nextInt(maxValue), minValue, maxValue);
    }
}