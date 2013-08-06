/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.helper;

import ccm.nucleum_omnium.base.BaseNIC;

public final class JavaHelper extends BaseNIC
{

    /**
     * @param str
     *            The String to test
     * @return true if it is a Number. false otherwise
     */
    public static boolean isNumeric(final String str)
    {
        try
        {
            @SuppressWarnings("unused")
            final double d = Double.parseDouble(str);
        } catch (final NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    /**
     * @param input
     *            String to Title Case
     * @return the same string but Titled
     */
    public static String tileCase(final String input)
    {
        if (input.contains(" "))
        {
            final String[] strings = input.split(" ");
            final StringBuilder sb = new StringBuilder();
            for (final String s : strings)
            {
                sb.append(s.substring(0, 1).toUpperCase() + s.substring(1));
                sb.append(" ");
                System.out.println(sb.toString());
            }
            return sb.toString();
        } else
        {
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }
}