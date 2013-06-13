package lib.cofh.util;

/**
 * Contains various math-related helper functions. Often faster than
 * conventional implementations.
 * 
 * @author King Lemming
 */
public final class MathHelper {

    private MathHelper() {
    }

    public static final double   PHI       = 1.618034;

    public static final double[] SIN_TABLE = new double[65536];

    static {
        for (int i = 0; i < 65536; i++)
            MathHelper.SIN_TABLE[i] = Math.sin(i / 65536D * 2 * Math.PI);
        MathHelper.SIN_TABLE[0] = 0;
        MathHelper.SIN_TABLE[16384] = 1;
        MathHelper.SIN_TABLE[32768] = 0;
        MathHelper.SIN_TABLE[49152] = 1;
    }

    public static double sin(final double d) {

        return MathHelper.SIN_TABLE[(int) ((float) d * 10430.378F) & 65535];
    }

    public static double cos(final double d) {

        return MathHelper.SIN_TABLE[(int) ((float) d * 10430.378F + 16384.0F) & 65535];
    }

    public static float approachLinear(final float a, final float b, final float max) {

        return a > b ? a - b < max ? b : a - max : b - a < max ? b : a + max;
    }

    public static double approachLinear(final double a, final double b, final double max) {

        return a > b ? a - b < max ? b : a - max : b - a < max ? b : a + max;
    }

    public static float interpolate(final float a, final float b, final float d) {

        return a + (b - a) * d;
    }

    public static double interpolate(final double a, final double b, final double d) {

        return a + (b - a) * d;
    }

    public static double approachExp(final double a, final double b, final double ratio) {

        return a + (b - a) * ratio;
    }

    public static double approachExp(final double a, final double b, final double ratio,
            final double cap) {

        double d = (b - a) * ratio;

        if (Math.abs(d) > cap)
            d = Math.signum(d) * cap;
        return a + d;
    }

    public static double retreatExp(final double a, final double b, final double c,
            final double ratio, final double kick) {

        final double d = (Math.abs(c - a) + kick) * ratio;

        if (d > Math.abs(b - a))
            return b;
        return a + Math.signum(b - a) * d;
    }

    public static double clip(double value, final double min, final double max) {

        if (value > max)
            value = max;
        else if (value < min)
            value = min;
        return value;
    }

    public static boolean between(final double a, final double x, final double b) {

        return a <= x && x <= b;
    }

    public static int approachExpI(final int a, final int b, final double ratio) {

        final int r = (int) Math.round(MathHelper.approachExp(a, b, ratio));
        return r == a ? b : r;
    }

    public static int retreatExpI(final int a, final int b, final int c, final double ratio,
            final int kick) {

        final int r = (int) Math.round(MathHelper.retreatExp(a, b, c, ratio, kick));
        return r == a ? b : r;
    }

    public static int floor_double(final double d) {

        return net.minecraft.util.MathHelper.floor_double(d);
    }

    /**
     * Unchecked implementation to round a number. Parameter should be known to
     * be valid in advance.
     */
    public static int round(final double d) {

        return (int) (d + 0.5D);
    }

    /**
     * Unchecked implementation to round a number up. Parameter should be known
     * to be valid in advance.
     */
    public static int ceil(final double d) {

        return (int) (d + 0.9999D);
    }

    /**
     * Unchecked implementation to determine the smaller of two Floats.
     * Parameters should be known to be valid in advance.
     */
    public static float minF(final float a, final float b) {

        return a < b ? a : b;
    }

    /**
     * Unchecked implementation to determine the larger of two Floats.
     * Parameters should be known to be valid in advance.
     */
    public static float maxF(final float a, final float b) {

        return a > b ? a : b;
    }

    /**
     * Unchecked implementation to determine the smaller of two Integers.
     * Parameters should be known to be valid in advance.
     */
    public static int minI(final int a, final int b) {

        return a < b ? a : b;
    }

    /**
     * Unchecked implementation to determine the larger of two Integers.
     * Parameters should be known to be valid in advance.
     */
    public static int maxI(final int a, final int b) {

        return a > b ? a : b;
    }

}
