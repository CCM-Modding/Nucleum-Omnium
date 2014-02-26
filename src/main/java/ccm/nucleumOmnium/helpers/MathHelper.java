/*
 * Copyright (c) 2014 CCM modding crew.
 * View members of the CCM modding crew on https://github.com/orgs/CCM-Modding/members
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleumOmnium.helpers;

import java.util.Random;

/**
 * Static helper methods
 */
public class MathHelper
{
    private static final Random rand = new Random();

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
            }
            else if (value > max)
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