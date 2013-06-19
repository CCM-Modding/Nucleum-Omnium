package ccm.nucleum_omnium.helper;

import java.util.ArrayList;
import java.util.List;

import ccm.nucleum_omnium.BaseNIClass;

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
    
    /**
     * Copy's a {@code List<T>}
     * 
     * @param list
     *            The {@code List<T>} to copy
     * @return A new instance of said {@code List<T>}
     * @author Reika
     */
    public static <T> List<T> copyList(final List<T> list) {
        final List<T> newList = new ArrayList<T>(list);
        return newList;
    }
}