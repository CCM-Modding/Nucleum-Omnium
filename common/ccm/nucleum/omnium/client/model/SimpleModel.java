/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.client.model;

import net.minecraftforge.client.model.IModelCustom;
import ccm.nucleum.omnium.utils.handler.ResourceHandler;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * SimpleModel, to use with the Advanced Model Loader. It loads and allows the user to render the loaded model
 * 
 * @author Captain_Shadows
 */
public class SimpleModel
{
    protected IModelCustom model;
    protected String name;

    public SimpleModel(final String resourceName)
    {
        name = resourceName;
        model = ResourceHandler.loadModel(name);
    }

    public void bindTexture()
    {
        ResourceHandler.bindModel(FMLClientHandler.instance().getClient(), name);
    }

    /** When calling this method the texture doesn't need to be binded */
    public void render()
    {
        bindTexture();

        model.renderAll();
    }

    public void renderPart(final String partName)
    {
        model.renderPart(partName);
    }
}