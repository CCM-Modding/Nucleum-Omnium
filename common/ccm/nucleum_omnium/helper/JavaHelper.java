/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.helper;

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
}