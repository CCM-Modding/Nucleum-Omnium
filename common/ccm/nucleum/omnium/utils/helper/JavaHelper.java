/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ccm.nucleum.omnium.BaseNIC;

public final class JavaHelper extends BaseNIC
{
    private static final List<String> trues = Arrays.asList("true", "on", "active", "yes", "right", "sure");

    /**
     * @return <code>true</code> if it is a Number. <code>false</code> otherwise
     */
    public static boolean isNumeric(final String str)
    {
        try
        {// Try to parse the string as a double
            final double d = Double.parseDouble(str);
        } catch (final NumberFormatException nfe)
        {// if an exception is created the it is not a number
            return false;
        }
        // if we get here an exception was not raised thus it is a number, and we can return true
        return true;
    }

    /**
     * @return <code>true</code> if it is any common word for it. <code>false</code> otherwise {@code If it returns false on a string that shoud be true please make an issue}
     */
    public static boolean toBoolean(final String input)
    { // Make sure that the string is not null and then check common words for true
        if (input != null)
        {
            for (String s : trues)
            {// Iterate through all the strings and match ignoring casing
                return s.equalsIgnoreCase(input);
            }
        }
        // if any of the past don't work just return false
        return false;
    }

    /**
     * @return The date that results after appling the offset in the specified unit to the current date
     */
    public static String titleCase(final String input)
    {// if the input has a space the it contains more than 1 word and we have to split them
        if (input.contains(" "))
        {// Split at the spaces and then create a StringBuilder to re-create the original string
            final String[] strings = input.split(" ");
            final StringBuilder sb = new StringBuilder();
            for (final String s : strings)
            {// for each of the independent strings we perform the the "action", and then append a space
                sb.append(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
                sb.append(" ");
                CCMLogger.DEFAULT_LOGGER.debug(sb);
            }
            return sb.toString();
        } else
        {// otherwise just perform the "action"
            return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        }
    }

    /**
     * @return The date that results after appling the offset in the specified unit to the current date
     */
    public static String camelCase(final String input)
    {// if the input has a space the it contains more than 1 word and we have to split them
        if (input.contains(" "))
        {// Split at the spaces and then create a StringBuilder to re-create the original string
            final String[] strings = input.split(" ");
            final StringBuilder sb = new StringBuilder();
            for (final String s : strings)
            {// for each of the independent strings we perform the the "action", and then append a space
                sb.append(s.substring(0, 1).toLowerCase() + s.substring(1).toLowerCase());
                sb.append(" ");
                CCMLogger.DEFAULT_LOGGER.debug(sb);
            }
            return sb.toString();
        } else
        {// otherwise just perform the "action"
            return input.substring(0, 1).toLowerCase() + input.substring(1).toLowerCase();
        }
    }

    /**
     * @return The date that results after appling the offset in the specified unit to the current date
     */
    public static Date getDate(final TimeUnit unit, final int offset)
    {
        // Get the default representation of "now".
        final Calendar when = Calendar.getInstance();

        // Scale the offset according to its unit.
        // Note that this defines a day as exactly 60*60*24 seconds,
        // and ignores things like Daylight Savings Time.
        long seconds = unit.toSeconds(offset);

        // We do not support time increments longer than 24 millenia.
        if (seconds > Integer.MAX_VALUE)
        {
            seconds = Integer.MAX_VALUE;
        } else if (seconds < Integer.MIN_VALUE)
        {
            seconds = Integer.MIN_VALUE;
        }

        when.add(Calendar.SECOND, (int) seconds);

        // Convert to a Date object.
        return when.getTime();
    }
}
