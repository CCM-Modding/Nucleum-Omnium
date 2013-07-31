package lib.cofh.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

/**
 * Easy way of rendering an item which should look like a block.
 * 
 * @author King Lemming
 */
public class RenderItemAsBlock implements IItemRenderer
{

    public static RenderItemAsBlock instance = new RenderItemAsBlock();

    @Override
    public boolean handleRenderType(final ItemStack item, final ItemRenderType type)
    {

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(final ItemRenderType type,
                                         final ItemStack item,
                                         final ItemRendererHelper helper)
    {

        return true;
    }

    @Override
    public void renderItem(final ItemRenderType type, final ItemStack item, final Object... data)
    {

        double offset = -0.5;
        if ((type == ItemRenderType.EQUIPPED) || (type == ItemRenderType.EQUIPPED_FIRST_PERSON))
        {
            offset = 0;
        } else if (type == ItemRenderType.ENTITY)
        {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
        }
        RenderHelper.renderItemAsBlock((RenderBlocks) data[0], item, offset, offset, offset);
    }

}
