package ccm.nucleum_omnium.helper;

import ccm.nucleum_omnium.base.BaseNIC;

public class MathHelper extends BaseNIC {

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
    public static int clampInt(final int min, final int max, final int value) {
        if (value < min) {
            return min;
        }
        if (value > max) {
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
    public static int getRandomInt(final int maxValue) {

        return MathHelper.clampInt(1, maxValue, BaseNIC.rand.nextInt(maxValue));
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
    public static int getRandomInt(final int minValue, final int maxValue) {

        return MathHelper.clampInt(minValue, maxValue, BaseNIC.rand.nextInt(maxValue));
    }
}