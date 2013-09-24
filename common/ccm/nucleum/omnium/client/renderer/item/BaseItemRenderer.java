package ccm.nucleum.omnium.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public abstract class BaseItemRenderer implements IItemRenderer
{
    @Override
    public boolean handleRenderType(final ItemStack item, final ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(final ItemRenderType type, final ItemStack item, final ItemRendererHelper helper)
    {
        return true;
    }
}