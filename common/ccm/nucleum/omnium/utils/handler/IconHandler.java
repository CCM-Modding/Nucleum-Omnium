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
    /** All the stored Icons */
    private final Map<ResourceLocation, Icon> icons;

    private IconHandler()
    {
        icons = new HashMap<ResourceLocation, Icon>();
    }

    public static IconHandler instance()
    {
        return INSTANCE;
    }

    public static Map<ResourceLocation, Icon> icons()
    {
        return instance().icons;
    }

    public static void addIcon(final ResourceLocation resource)
    {
        icons().put(resource, null);
    }

    public static void addIcon(final IMod mod, final String location)
    {
        addIcon(new ResourceLocation(mod.getModID(), location));
    }

    public static Icon getIcon(final IMod mod, final String location)
    {
        return icons().get(new ResourceLocation(mod.getModID(), location));
    }

    @ForgeSubscribe
    public void loadIcons(final TextureStitchEvent.Pre evt)
    {
        for (Entry<ResourceLocation, Icon> entry : icons().entrySet())
        {
            entry.setValue(evt.map.registerIcon(entry.getKey().toString()));
        }
    }
}