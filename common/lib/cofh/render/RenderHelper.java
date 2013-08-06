package lib.cofh.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Contains various helper functions to assist with rendering.
 * 
 * @author King Lemming
 */
public final class RenderHelper
{

    public static final double RENDER_OFFSET  = 1.0D / 1024.0D;
    public static final String MC_BLOCK_SHEET = "/terrain.png";
    public static final String MC_ITEM_SHEET  = "/gui/items.png";

    private RenderHelper()
    {

    }

    // public static final RenderEngine engine() {

    // return Minecraft.getMinecraft().renderEngine;
    // }

    public static final Tessellator tessellator()
    {

        return Tessellator.instance;
    }

    public static void renderItemAsBlock(final RenderBlocks renderer,
                                         final ItemStack item,
                                         final double translateX,
                                         final double translateY,
                                         final double translateZ)
    {

        final Tessellator tessellator = tessellator();
        final Block block = Block.stone;
        final Icon texture = item.getIconIndex();

        if (texture == null)
        {
            return;
        }
        renderer.setRenderBoundsFromBlock(block);
        GL11.glTranslated(translateX, translateY, translateZ);
        tessellator.startDrawingQuads();

        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.draw();
    }

    public static final Icon getFluidTexture(final FluidStack fluid)
    {

        if ((fluid == null) || (fluid.getFluid() == null))
        {
            return FluidRegistry.LAVA.getIcon();
        }
        return fluid.getFluid().getIcon(fluid);
    }

    public static final void bindItemTexture(final ItemStack stack)
    {

        // engine().bindTexture(stack.getItemSpriteNumber() == 0 ?
        // MC_BLOCK_SHEET : MC_ITEM_SHEET);
    }

    public static final void bindTexture(final String string)
    {

        // engine().bindTexture(string);
    }

    public static final void setBlockTextureSheet()
    {

        bindTexture(MC_BLOCK_SHEET);
    }

    public static final void setItemTextureSheet()
    {

        bindTexture(MC_ITEM_SHEET);
    }

    public static final void setDefaultFontTextureSheet()
    {

        bindTexture("/font/default.png");
    }

    public static final void setSGAFontTextureSheet()
    {

        bindTexture("/font/alternate.png");
    }

}
