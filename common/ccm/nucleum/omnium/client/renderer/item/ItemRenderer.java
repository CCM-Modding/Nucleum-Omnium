/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum.omnium.client.model.AdvancedBaseModel;

/**
 * ItemRenderer
 * <p>
 * 
 * @author Captain_Shadows
 */
@SideOnly(Side.CLIENT)
public abstract class ItemRenderer implements IItemRenderer
{

    protected AdvancedBaseModel model;

    @Override
    public boolean handleRenderType(final ItemStack item, final ItemRenderType type)
    {
        return true;
    }

    public abstract void render(final float x, final float y, final float z, final float scale, final float angle);

    @Override
    public abstract void renderItem(final ItemRenderType type, final ItemStack item, final Object... data);

    @Override
    public boolean shouldUseRenderHelper(final ItemRenderType type, final ItemStack item, final ItemRendererHelper helper)
    {
        return true;
    }
}