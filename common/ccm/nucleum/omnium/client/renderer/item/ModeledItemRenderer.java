/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.client.renderer.item;

import net.minecraft.item.ItemStack;

import ccm.nucleum.omnium.client.model.SimpleModel;

/**
 * ModeledItemRenderer
 * <p>
 * 
 * @author Captain_Shadows
 */
public abstract class ModeledItemRenderer extends BaseItemRenderer
{

    protected SimpleModel model;

    public abstract void render(final float x, final float y, final float z, final float scale, final float angle);

    @Override
    public abstract void renderItem(final ItemRenderType type, final ItemStack item, final Object... data);
}