/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.handler;

import static ccm.nucleum_omnium.utils.lib.Locations.GUI;
import static ccm.nucleum_omnium.utils.lib.Locations.MODEL;
import static ccm.nucleum_omnium.utils.lib.Locations.MODEL_TEXTURE;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.helper.ItemHelper;

import lib.cofh.util.StringHelper;

public final class TextureHandler
{

    private final Map<Integer, ResourceLocation> textures;

    private static final TextureHandler          INSTANCE = new TextureHandler();

    /**
     * @param id
     *            The name of the GUI
     * @return The "fixed" location of the GUI
     */
    private static String fixGUILocation(final String id)
    {
        return String.format(GUI, StringHelper.titleCase(id));
    }

    /**
     * @param id
     *            The name of the Model
     * @return The "unique" hash code of the Model's name
     */
    private static String fixModelLocation(final String id)
    {
        return String.format(MODEL, StringHelper.titleCase(id));
    }

    /**
     * @param id
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Tile Entity's name
     */
    private static String fixModelTextureLocation(final String id)
    {
        return String.format(MODEL_TEXTURE, StringHelper.titleCase(id));
    }

    /**
     * @param name
     *            The name of the GUI
     * @return The "unique" hash code of the GUI's name
     */
    private static int hashGUI(final String name)
    {
        return ("CCM.GUI." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    /**
     * @param id
     *            The name of the Model
     * @return The "unique" hash code of the Model's name
     */
    private static int hashModel(final String id)
    {
        return ("CCM.MODEL." + id.toUpperCase() + "." + id.hashCode()).hashCode();
    }

    /**
     * @param name
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Tile Entity's name
     */
    private static int hashModelTexture(final String name)
    {
        return ("CCM.MODEL.TEXTURE." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    public static void addGUITexture(final IMod mod, final String id)
    {
        INSTANCE.textures.put(hashGUI(id), new ResourceLocation(mod.getModId(), fixGUILocation(id)));
    }

    public static void addModel(final IMod mod, final Enum<?> id)
    {
        addModel(mod, id.name());
    }

    public static void addModel(final IMod mod, final String id)
    {
        INSTANCE.textures.put(hashModel(id), new ResourceLocation(mod.getModId(), fixModelLocation(id)));
    }

    public static void addModelTexture(final IMod mod, final Enum<?> id)
    {
        addModelTexture(mod, id.name());
    }

    public static void addModelTexture(final IMod mod, final String id)
    {
        INSTANCE.textures.put(hashModelTexture(id), new ResourceLocation(mod.getModId(),
                                                                         fixModelTextureLocation(id)));
    }

    public static ResourceLocation getGUI(final String id)
    {
        return INSTANCE.textures.get(hashGUI(id));
    }

    public static ResourceLocation getModel(final Enum<?> id)
    {
        return getModelTexture(id.name());
    }

    public static ResourceLocation getModel(final String id)
    {
        return INSTANCE.textures.get(hashModel(id));
    }

    public static String getModelLoc(final Enum<?> id)
    {
        return getModelLoc(id.name());
    }

    public static String getModelLoc(final String id)
    {
        final String modName = getModel(id).func_110624_b();
        final String location = getModel(id).func_110623_a();
        return ("/assets/" + modName + "/" + location);
    }

    public static ResourceLocation getModelTexture(final Enum<?> id)
    {
        return getModelTexture(id.name());
    }

    public static ResourceLocation getModelTexture(final String id)
    {
        return INSTANCE.textures.get(hashModelTexture(id));
    }

    /**
     * @return The texture.
     */
    public static String getTexture(final Item item, final String texturePath)
    {
        return texturePath + ItemHelper.getItemName(item);
    }

    public static String getTextureFromName(final Enum<?> enu, final String texturePath)
    {
        return texturePath + enu.name();
    }

    /**
     * @param name
     *            Name of the thing to make a texture
     * @return The texture version of the name
     */
    public static String getTextureFromName(final String name, final String texturePath)
    {
        return texturePath + name;
    }

    private TextureHandler()
    {
        textures = new HashMap<Integer, ResourceLocation>();
    }
}