/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.client.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import cpw.mods.fml.client.FMLClientHandler;

import ccm.nucleum_omnium.handler.LogHandler;
import ccm.nucleum_omnium.handler.TextureHandler;

/**
 * Advanced Base Model, to use with the Advanced Model Loader, Pending internal minor fix...
 * 
 * @author Captain_Shadows
 */
public class AdvancedBaseModel
{

    protected IModelCustom model;
    protected String       name;

    public AdvancedBaseModel(final Enum<?> resourceName)
    {
        this(resourceName.name());
    }

    public AdvancedBaseModel(final String resourceName)
    {
        name = resourceName;
        LogHandler.debug(name);
        LogHandler.debug(TextureHandler.getModelLoc(name));
        model = AdvancedModelLoader.loadModel(TextureHandler.getModelLoc(name));
    }

    public void bindTexture()
    {
        FMLClientHandler.instance().getClient().renderEngine.func_110577_a(TextureHandler.getModelTexture(name));
    }

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