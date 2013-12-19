/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import ccm.nucleum.omnium.CCMMod;
import ccm.nucleum.omnium.utils.helper.JavaHelper;

public final class ResourceHandler
{
    /** Single instance of this class */
    private static final ResourceHandler INSTANCE = new ResourceHandler();

    // /////////////////////////////
    // Instance Stuff
    // /////////////////////////////
    /** All the stored resources */
    private final Map<Integer, ResourceLocation> resources;

    private ResourceHandler()
    {
        resources = new HashMap<Integer, ResourceLocation>();
    }

    public static ResourceHandler instance()
    {
        return INSTANCE;
    }

    static Map<Integer, ResourceLocation> resources()
    {
        return instance().resources;
    }

    // /////////////////////////////
    // Getters and "Setters"
    // /////////////////////////////
    /** Adds a GUI texture */
    public static void addGUI(final CCMMod mod, final String name)
    {
        resources().put(hashGUI(name), new ResourceLocation(mod.id(), fixGUILocation(name)));
    }

    /** Returns a GUI texture */
    public static ResourceLocation getGUI(final String name)
    {
        return resources().get(hashGUI(name));
    }

    /** Adds a Element texture */
    public static void addElement(final CCMMod mod, final String elementName, final String name)
    {
        resources().put(hashElement(elementName, name), new ResourceLocation(mod.id(), fixElementLocation(elementName, name)));
    }

    /** Returns a Element texture */
    public static ResourceLocation getElement(final String elementName, final String name)
    {
        return resources().get(hashElement(elementName, name));
    }

    public static void addModel(final CCMMod mod, final String name)
    {
        resources().put(hashModel(name), new ResourceLocation(mod.id(), fixModelLocation(name)));
        resources().put(hashModelTexture(name), new ResourceLocation(mod.id(), fixModelTextureLocation(name)));
    }

    /** Gets a Model */
    public static ResourceLocation getModel(final String name)
    {
        return resources().get(hashModel(name));
    }

    /** Gets a Model's Texture */
    public static ResourceLocation getModelTexture(final String name)
    {
        return resources().get(hashModelTexture(name));
    }

    // /////////////////////////////
    // Exterior Helpers
    // /////////////////////////////

    /** Binds a GUI texture to it's GUI */
    public static void bindGUI(final String name)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(getGUI(name));
    }

    /** Returns the file path to the Resource */
    public static String getModelLocation(final String name)
    {
        // "/assets/Mod's Name/Location"
        return ("/assets/" + getModel(name).getResourceDomain() + "/" + getModel(name).getResourcePath());
    }

    /** Binds a GUI texture to it's GUI */
    public static void bindModel(final Minecraft mc, final String name)
    {
        mc.getTextureManager().bindTexture(ResourceHandler.getModelTexture(name));
    }

    /** Loads a Model */
    public static IModelCustom loadModel(final String name)
    {
        return AdvancedModelLoader.loadModel(ResourceHandler.getModelLocation(name));
    }

    // /////////////////////////////
    // Internal Helpers
    // /////////////////////////////

    // Hash Fixes
    /**
     * @param name
     *            The name of the GUI
     * @return The "unique" hash code of the GUI's name
     */
    static int hashGUI(final String name)
    {
        return ("CCM.GUI." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    /**
     * @param name
     *            The name of the GUI
     * @return The "unique" hash code of the GUI's name
     */
    static int hashElement(final String elementName, final String name)
    {
        return ("CCM.GUI.ELEMENT." + elementName.toUpperCase() + "." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    /**
     * @param name
     *            The name of the Model
     * @return The "unique" hash code of the Model's name
     */
    static int hashModel(final String name)
    {
        return ("CCM.MODEL." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    /**
     * @param name
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Tile Entity's name
     */
    static int hashModelTexture(final String name)
    {
        return ("CCM.MODEL.TEXTURE." + name.toUpperCase() + "." + name.hashCode()).hashCode();
    }

    // String Fixes
    /**
     * @param name
     *            The name of the GUI
     * @return The "fixed" location of the GUI
     */
    static String fixGUILocation(final String name)
    {
        return "textures/guis/gui" + JavaHelper.titleCase(name) + ".png";
    }

    /**
     * @param name
     *            The name of the GUI
     * @return The "fixed" location of the Element
     */
    static String fixElementLocation(final String elementName, final String name)
    {
        return "textures/guis/elements/" + elementName.toLowerCase() + JavaHelper.titleCase(name) + ".png";
    }

    /**
     * @param name
     *            The name of the Model
     * @return The "unique" hash code of the Model's name
     */
    static String fixModelLocation(final String name)
    {
        return "models/" + JavaHelper.titleCase(name) + ".obj";
    }

    /**
     * @param name
     *            The name of the Tile Entity
     * @return The "unique" hash code of the Tile Entity's name
     */
    static String fixModelTextureLocation(final String name)
    {
        return "textures/models/model" + JavaHelper.titleCase(name) + ".png";
    }
}