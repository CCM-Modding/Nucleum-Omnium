package lib.cofh.util;

/**
 * Contains various helper functions to assist with colors.
 * 
 * @author King Lemming
 */
public final class ColorHelper {

    private ColorHelper() {
    }

    public static final String BLACK          = (char) 167 + "0";

    public static final String BLUE           = (char) 167 + "1";

    public static final String GREEN          = (char) 167 + "2";

    public static final String TEAL           = (char) 167 + "3";

    public static final String RED            = (char) 167 + "4";

    public static final String PURPLE         = (char) 167 + "5";

    public static final String ORANGE         = (char) 167 + "6";

    public static final String LIGHT_GRAY     = (char) 167 + "7";

    public static final String GRAY           = (char) 167 + "8";

    public static final String LIGHT_BLUE     = (char) 167 + "9";

    public static final String BRIGHT_GREEN   = (char) 167 + "a";

    public static final String BRIGHT_BLUE    = (char) 167 + "b";

    public static final String LIGHT_RED      = (char) 167 + "c";

    public static final String PINK           = (char) 167 + "d";

    public static final String YELLOW         = (char) 167 + "e";

    public static final String WHITE          = (char) 167 + "f";

    public static final String END            = (char) 167 + "r";

    public static final int    DYE_BLACK      = 0x191919;

    public static final int    DYE_RED        = 0xCC4C4C;

    public static final int    DYE_GREEN      = 0x667F33;

    public static final int    DYE_BROWN      = 0x7F664C;

    public static final int    DYE_BLUE       = 0x3366CC;

    public static final int    DYE_PURPLE     = 0xB266E5;

    public static final int    DYE_CYAN       = 0x4C99B2;

    public static final int    DYE_LIGHT_GRAY = 0x999999;

    public static final int    DYE_GRAY       = 0x4C4C4C;

    public static final int    DYE_PINK       = 0xF2B2CC;

    public static final int    DYE_LIME       = 0x7FCC19;

    public static final int    DYE_YELLOW     = 0xE5E533;

    public static final int    DYE_LIGHT_BLUE = 0x99B2F2;

    public static final int    DYE_MAGENTA    = 0xE57FD8;

    public static final int    DYE_ORANGE     = 0xF2B233;

    public static final int    DYE_WHITE      = 0xFFFFFF;

    public static final int[]  DYE_COLORS     = { ColorHelper.DYE_BLACK, ColorHelper.DYE_RED,
            ColorHelper.DYE_GREEN, ColorHelper.DYE_BROWN, ColorHelper.DYE_BLUE,
            ColorHelper.DYE_PURPLE, ColorHelper.DYE_CYAN, ColorHelper.DYE_LIGHT_GRAY,
            ColorHelper.DYE_GRAY, ColorHelper.DYE_PINK, ColorHelper.DYE_LIME,
            ColorHelper.DYE_YELLOW, ColorHelper.DYE_LIGHT_BLUE, ColorHelper.DYE_MAGENTA,
            ColorHelper.DYE_ORANGE, ColorHelper.DYE_WHITE };

    public static int getDyeColor(final int color) {

        return color < 0 || color > 15 ? 0xFFFFFF : ColorHelper.DYE_COLORS[color];
    }

}
