package ccm.nucleum_omnium.handler;

import static ccm.nucleum_omnium.utils.lib.Locations.GUI;
import static ccm.nucleum_omnium.utils.lib.Locations.MODEL;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.helper.ItemHelper;

import lib.cofh.util.StringHelper;

public final class TextureHandler {

    private final Map<Integer, ResourceLocation> textures;

    private static final TextureHandler          INSTANCE = new TextureHandler();

    private TextureHandler() {
        textures = new HashMap<Integer, ResourceLocation>();
    }

    public static void addTETexture(final IMod mod, final String id) {
        INSTANCE.textures.put(hashTE(id), new ResourceLocation(mod.getModId(), fixTELocation(id)));
    }

    public static ResourceLocation getTETexture(final String ID) {
        return INSTANCE.textures.get(hashTE(ID));
    }

    /**
     * @param name
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Tile Entity's name
     */
    private static int hashTE(final String name) {
        return ("CCM.ENTITY.TILE." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    /**
     * @param id
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Tile Entity's name
     */
    private static String fixTELocation(final String id) {
        return String.format(MODEL, StringHelper.titleCase(id));
    }

    public static void addGUITexture(final IMod mod, final String id) {
        INSTANCE.textures.put(hashGUI(id), new ResourceLocation(mod.getModId(), fixGUILocation(id)));
    }

    public static ResourceLocation getGUI(final String id) {
        return INSTANCE.textures.get(hashGUI(id));
    }

    /**
     * @param name
     *            The name of the GUI
     * @return The "unique" hash code of the GUI's name
     */
    private static int hashGUI(final String name) {
        return ("CCM.GUI." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    /**
     * @param id
     *            The name of the GUI
     * @return The "fixed" location of the GUI
     */
    private static String fixGUILocation(final String id) {
        return String.format(GUI, StringHelper.titleCase(id));
    }

    /**
     * @param name
     *            Name of the thing to make a texture
     * @return The texture version of the name
     */
    public static String getTextureFromName(final String name, final String texturePath) {
        return texturePath + name;
    }

    public static String getTextureFromName(final Enum<?> enu, final String texturePath) {
        return texturePath + enu.name();
    }

    /**
     * @return The texture.
     */
    public static String getTexture(final Item item, final String texturePath) {
        return texturePath + ItemHelper.getItemName(item);
    }
}