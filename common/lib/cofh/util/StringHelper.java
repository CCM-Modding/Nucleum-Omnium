package lib.cofh.util;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

/**
 * Contains various helper functions to assist with String manipulation.
 * 
 * @author King Lemming
 */
public final class StringHelper
{

    private StringHelper()
    {}

    public static int getSplitStringHeight(FontRenderer fontRenderer, String input, int width)
    {

        List stringRows = fontRenderer.listFormattedStringToWidth(input, width);
        return stringRows.size() * fontRenderer.FONT_HEIGHT;
    }

    public static String camelCase(String input)
    {

        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }

    public static String titleCase(String input)
    {

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String localize(String key)
    {

        return StatCollector.translateToLocal(key);
    }
}