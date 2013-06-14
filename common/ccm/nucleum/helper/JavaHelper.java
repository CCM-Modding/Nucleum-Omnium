package ccm.nucleum.helper;

import java.util.List;

import ccm.nucleum.BaseNIClass;

public final class JavaHelper extends BaseNIClass {
    
    /**
     * @param str
     *            The String to test
     * @return true if it is a Number. false otherwise
     */
    @SuppressWarnings("unused")
    public static boolean isNumeric(final String str) {
        try {
            final double d = Double.parseDouble(str);
        } catch (final NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * @param list
     *            A {@code List<Integer>}
     * @return The corresponding {@code int[]}
     */
    public static int[] toIntArray(final List<Integer> list) {
        final int[] ret = new int[list.size()];
        int i = 0;
        for (final Integer e : list) {
            ret[i++] = e.intValue();
        }
        return ret;
    }
    
    /**
     * @param list
     *            A {@code List<Float>}
     * @return The corresponding {@code float[]}
     */
    public static float[] toFloatArray(final List<Float> list) {
        final float[] ret = new float[list.size()];
        int i = 0;
        for (final Float e : list) {
            ret[i++] = e.intValue();
        }
        return ret;
    }
}