package ccm.nucleum.omnium.utils.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

import ccm.nucleum.omnium.IMod;

public final class IconHandler
{
    /** Single instance of this class */
    private static final IconHandler INSTANCE = new IconHandler();

    // /////////////////////////////
    // Instance Stuff
    // /////////////////////////////
    /** All the stored resources */
    private final Map<ResourceLocation, Icon> icons;

    private IconHandler()
    {
        icons = new HashMap<ResourceLocation, Icon>();
    }

    public static IconHandler instance()
    {
        return INSTANCE;
    }

    private static Map<ResourceLocation, Icon> resources()
    {
        return instance().icons;
    }

    public static void addIcon(final ResourceLocation resource, final Icon icon)
    {
        resources().put(resource, icon);
    }

    public static void addIcon(final IMod mod, final String location, final Icon icon)
    {
        addIcon(new ResourceLocation(mod.getModId(), location), icon);
    }

    public static void addIcon(final IMod mod, final String location)
    {
        addIcon(mod, location, (Icon) null);
    }

    public static Icon getIcon(final IMod mod, final String location)
    {
        return resources().get(location);
    }

    @ForgeSubscribe
    public void loadIcons(final TextureStitchEvent.Pre evt)
    {
        for (Entry<ResourceLocation, Icon> entry : resources().entrySet())
        {
            entry.setValue(evt.map.registerIcon(entry.getKey().toString()));
        }
    }
}