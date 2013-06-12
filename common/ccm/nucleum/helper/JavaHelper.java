package ccm.nucleum.helper;

import java.util.List;

public final class JavaHelper extends BaseHelper
{

    /**
     * Checks if a string is a number
     * 
     * @param str
     *            The String to test
     * @return true if it is a Number; false otherwise
     */
    @SuppressWarnings("unused")
    public static boolean isNumeric(final String str)
    {
        try{
            final double d = Double.parseDouble(str);
        }catch(final NumberFormatException nfe){
            return false;
        }
        return true;
    }

    /**
     * Converts a {@code List<Integer>} to a {@code int[]}
     * 
     * @param list
     *            The {@code List<Integer>} to convert into a {@code int[]}
     * @return The corresponding {@code int[]}
     */
    public static int[] toIntArray(final List<Integer> list)
    {
        final int[] ret = new int[list.size()];
        int i = 0;
        for (final Integer e : list){
            ret[i++] = e.intValue();
        }
        return ret;
    }

    /**
     * Converts a {@code List<Float>} to a {@code float[]}
     * 
     * @param list
     *            The {@code List<Float>} to convert into a {@code float[]}
     * @return The corresponding {@code float[]}
     */
    public static float[] toFloatArray(final List<Float> list)
    {
        final float[] ret = new float[list.size()];
        int i = 0;
        for (final Float e : list){
            ret[i++] = e.intValue();
        }
        return ret;
    }
}