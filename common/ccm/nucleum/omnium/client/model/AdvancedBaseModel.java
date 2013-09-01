/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.client.model;

import net.minecraftforge.client.model.IModelCustom;

import cpw.mods.fml.client.FMLClientHandler;

import ccm.nucleum.omnium.utils.handler.LogHandler;
import ccm.nucleum.omnium.utils.handler.ResourceHandler;

/**
 * Advanced Base Model, to use with the Advanced Model Loader, Pending internal minor fix...
 * 
 * @author Captain_Shadows
 */
public class AdvancedBaseModel
{

    protected IModelCustom model;
    protected String name;

    public AdvancedBaseModel(final Enum<?> resourceName)
    {
        this(resourceName.name());
    }

    public AdvancedBaseModel(final String resourceName)
    {
        name = resourceName;
        LogHandler.debug(name);
        LogHandler.debug(ResourceHandler.getModelLocation(name));
        model = ResourceHandler.loadModel(name);
    }

    public void bindTexture()
    {
        FMLClientHandler.instance().getClient().renderEngine.func_110577_a(ResourceHandler.getModelTexture(name));
    }

    /** When calling this method the texture doesn't need to be binded */
    public void render()
    {
        // Bind texture
        bindTexture();
        // Render
        model.renderAll();
    }

    public void renderPart(final String partName)
    {
        model.renderPart(partName);
    }
}