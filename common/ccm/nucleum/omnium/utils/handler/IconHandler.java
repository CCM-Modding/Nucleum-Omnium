package ccm.nucleum.omnium.utils.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

public final class IconHandler
{
    /** All the stored Icons */
    private static final Map<ResourceLocation, Icon> icons = new HashMap<ResourceLocation, Icon>();

    public static void addIcon(final ResourceLocation resource)
    {
        icons.put(resource, null);
    }

    public static void addIcon(final IMod mod, final String location)
    {
        addIcon(new ResourceLocation(mod.getModID(), location));
    }

    public static Icon getIcon(final ResourceLocation resource)
    {
        return icons.get(resource);
    }

    public static Icon getIcon(final IMod mod, final String location)
    {
        return getIcon(new ResourceLocation(mod.getModID(), location));
    }

    @ForgeSubscribe
    public void loadIcons(final TextureStitchEvent.Pre evt)
    {
        if (evt.map.getTextureType() != 1)
        {
            for (Entry<ResourceLocation, Icon> entry : icons.entrySet())
            {
                entry.setValue(evt.map.registerIcon(entry.getKey().toString()));
            }
        }
    }
}