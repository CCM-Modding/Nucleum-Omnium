package lib.cofh.util;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;

/**
 * Contains various helper functions to assist with String manipulation.
 * 
 * @author King Lemming
 */
public final class StringHelper {

	private StringHelper() {}

	public static int getSplitStringHeight(	final FontRenderer fontRenderer,
											final String input,
											final int width) {

		final List<?> stringRows = fontRenderer.listFormattedStringToWidth(input, width);
		return stringRows.size() * fontRenderer.FONT_HEIGHT;
	}

	public static String camelCase(final String input) {

		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}

	public static String titleCase(final String input) {

		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
}