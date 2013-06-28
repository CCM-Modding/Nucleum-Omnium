package lib.cofh.util;

/**
 * Contains various helper functions to assist with colors.
 * 
 * @author King Lemming
 */
public enum DyeColors {

	BLACK((char) 167 + "0", 0x191919),
	RED((char) 167 + "1", 0xCC4C4C),
	GREEN((char) 167 + "2", 0x667F33),
	BROWN((char) 167 + "3", 0x7F664C),
	BLUE((char) 167 + "4", 0x3366CC),
	PURPLE((char) 167 + "5", 0xB266E5),
	CYAN((char) 167 + "6", 0x4C99B2),
	LIGHT_GRAY((char) 167 + "7", 0x999999),
	GRAY((char) 167 + "8", 0x4C4C4C),
	PINK((char) 167 + "9", 0xF2B2CC),
	LIME((char) 167 + "a", 0x7FCC19),
	YELLOW((char) 167 + "b", 0xE5E533),
	LIGHT_BLUE((char) 167 + "c", 0x99B2F2),
	MAGENTA((char) 167 + "d", 0xE57FD8),
	ORANGE((char) 167 + "e", 0xF2B233),
	WHITE((char) 167 + "f", 0xFFFFFF);

	private String	colorString;
	private int		colorInt;

	DyeColors(final String colorS, final int colorI) {
		colorString = colorS;
		colorInt = colorI;
	}

	public int getColor() {
		return colorInt;
	}

	@Override
	public String toString() {
		return colorString;
	}

	public static final String	END	= (char) 167 + "r";

	public static int getDyeColor(final int color) {
		return (color < 0) || (color > 15) ? 0xFFFFFF : DyeColors.values()[color].getColor();
	}
}