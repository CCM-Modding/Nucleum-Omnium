package ccm.nucleum.omnium.utils.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

public final class IconHandler
{
    /** Single instance of this class */
    private static final IconHandler INSTANCE = new IconHandler();

    // /////////////////////////////
    // Instance Stuff
    // /////////////////////////////
    /** All the stored resources */
    private final Map<String, Icon> icons;

    private IconHandler()
    {
        icons = new HashMap<String, Icon>();
    }

    public static IconHandler instance()
    {
        return INSTANCE;
    }

    private static Map<String, Icon> resources()
    {
        return instance().icons;
    }

    public static void addIcon(final String location)
    {
        resources().put(location, null);
    }

    public static void addIcon(final String location, final Icon icon)
    {
        resources().put(location, icon);
    }

    public static Icon getIcon(final String location)
    {
        return resources().get(location);
    }

    @ForgeSubscribe
    public void loadIcons(final TextureStitchEvent.Pre evt)
    {
        for (Entry<String, Icon> entry : resources().entrySet())
        {
            entry.setValue(evt.map.registerIcon(entry.getKey()));
        }
    }
}