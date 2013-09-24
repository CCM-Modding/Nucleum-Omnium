/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.client.renderer.item;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import ccm.nucleum.omnium.utils.handler.IconHandler;
import ccm.nucleum.omnium.utils.helper.NBTItemHelper;

public class MultyTextureItemRenderer extends BaseItemRenderer
{

    private static final ResourceLocation glint  = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    Random                                rand   = new Random();
    int                                   zLevel = 0;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return !type.equals(ItemRenderType.FIRST_PERSON_MAP);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
    {
        GL11.glPushMatrix();

        ResourceLocation overrideLoc = new ResourceLocation();
        boolean override = false;

        Icon layer = IconHandler.getIcon(NBTItemHelper.getString(stack, ""), NBTItemHelper.getString(stack, ""));

        if (type == ItemRenderType.INVENTORY)
        {
            int passes = ((ItemWeapon) stack.getItem()).getPasses(stack);
            Icon[] icons = new Icon[passes];

            for (int i = 0; i < passes; i++)
            {
                icons[i] = ((ItemWeapon) stack.getItem()).getIcon(stack, i, override);
            }

            TextureManager engine = Minecraft.getMinecraft().getTextureManager();
            RenderBlocks renderBlocks = (RenderBlocks) data[0];

            int k = stack.itemID;
            int l = stack.getItemDamage();

            float f;
            int i1;
            float f1;
            float f2;

            Block block = (k < Block.blocksList.length ? Block.blocksList[k] : null);
            if (stack.getItemSpriteNumber() == 0 && block != null && RenderBlocks.renderItemIn3d(Block.blocksList[k].getRenderType()))
            {
                engine.bindTexture(TextureMap.locationBlocksTexture);
                GL11.glPushMatrix();
                GL11.glScalef(10.0F, 10.0F, 10.0F);
                GL11.glTranslatef(1.0F, 0.5F, 1.0F);
                GL11.glScalef(1.0F, 1.0F, -1.0F);
                GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

                i1 = ((ItemWeapon) stack.getItem()).getColorFromPass(stack, passes, override);
                f = (float) (i1 >> 16 & 255) / 255.0F;
                f1 = (float) (i1 >> 8 & 255) / 255.0F;
                f2 = (float) (i1 & 255) / 255.0F;

                GL11.glColor4f(f, f1, f2, 1.0F);

                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                renderBlocks.useInventoryTint = false;
                renderBlocks.renderBlockAsItem(block, l, 1.0F);
                renderBlocks.useInventoryTint = true;
                GL11.glPopMatrix();
            }

            GL11.glDisable(GL11.GL_LIGHTING);
            ResourceLocation resourcelocation = engine.getResourceLocation(stack.getItemSpriteNumber());
            engine.bindTexture(resourcelocation);

            for (int i = 0; i < passes; i++)
            {
                i1 = ((ItemWeapon) stack.getItem()).getColorFromPass(stack, i, override);
                f = (float) (i1 >> 16 & 255) / 255.0F;
                f1 = (float) (i1 >> 8 & 255) / 255.0F;
                f2 = (float) (i1 & 255) / 255.0F;

                GL11.glColor4f(f, f1, f2, 1.0F);

                if (icons[i] != null)
                {
                    renderIcon(0, 0, icons[i], 16, 16);
                }
            }

            GL11.glEnable(GL11.GL_LIGHTING);

            if (stack.hasEffect(0))
            {
                renderEffect(engine, 0, 0);
            }

            GL11.glEnable(GL11.GL_CULL_FACE);
        }

        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            int passes = ((ItemWeapon) stack.getItem()).getPasses(stack);
            Icon[] icons = new Icon[passes];

            for (int i = 0; i < passes; i++)
            {
                icons[i] = ((ItemWeapon) stack.getItem()).getIcon(stack, i, override);
            }

            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();

            for (int i = 0; i < passes; i++)
            {
                Icon icon = icons[i];

                if (icon == null)
                {
                    GL11.glPopMatrix();
                    return;
                }

                texturemanager.bindTexture(texturemanager.getResourceLocation(stack.getItemSpriteNumber()));
                Tessellator tessellator = Tessellator.instance;
                float f = icon.getMinU();
                float f1 = icon.getMaxU();
                float f2 = icon.getMinV();
                float f3 = icon.getMaxV();

                int color = ((ItemWeapon) stack.getItem()).getColorFromPass(stack, i, override);
                float r = (float) (color >> 16 & 255) / 255.0F;
                float g = (float) (color >> 8 & 255) / 255.0F;
                float b = (float) (color & 255) / 255.0F;
                GL11.glColor4f(r, g, b, 1.0F);

                ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

                if (stack.hasEffect(0))
                {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    texturemanager.bindTexture(glint);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float f7 = 0.76F;
                    GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f8 = 0.125F;
                    GL11.glScalef(f8, f8, f8);
                    float f9 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(f9, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f8, f8, f8);
                    f9 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-f9, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }

                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            }

        }

        if (type == ItemRenderType.ENTITY)
        {   
            EntityItem entityItem = (EntityItem) data[1];
            
            float bob = MathHelper.sin(((float)entityItem.age) / 10.0F + entityItem.hoverStart) * 0.1F + 0.1F;
            GL11.glTranslatef((float)0, (float)0 + bob, (float)0);

            int passes = ((ItemWeapon) stack.getItem()).getPasses(stack);
            Icon[] icons = new Icon[passes];

            for (int i = 0; i < passes; i++)
            {
                icons[i] = ((ItemWeapon) stack.getItem()).getIcon(stack, i, override);
            }

            float f2 = MathHelper.sin(((float) entityItem.age /** + partialTicks */
            ) / 10.0F + entityItem.hoverStart) * 0.1F + 0.1F;
            float f3 = (((float) entityItem.age /** + partialTicks */
            ) / 20.0F + entityItem.hoverStart) * (180F / (float) Math.PI);
            byte b0 = getMiniBlockCount(stack);

            for (int i = 0; i < passes; i++)
            {
                Icon icon = icons[i];

                this.rand.setSeed(187L);

                int color = ((ItemWeapon) stack.getItem()).getColorFromPass(stack, i, override);
                float r = (float) (color >> 16 & 255) / 255.0F;
                float g = (float) (color >> 8 & 255) / 255.0F;
                float b = (float) (color & 255) / 255.0F;

                this.renderDroppedItem(entityItem, icon, b0, 0, r, g, b, 0);
            }
        }

        GL11.glPopMatrix();
    }

    private void renderDroppedItem(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7, int pass)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Tessellator tessellator = Tessellator.instance;

        if (par2Icon == null)
        {
            ResourceLocation resourcelocation = texturemanager.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
            par2Icon = ((TextureMap)texturemanager.getTexture(resourcelocation)).getAtlasSprite("missingno");
        }

        float f4 = ((Icon)par2Icon).getMinU();
        float f5 = ((Icon)par2Icon).getMaxU();
        float f6 = ((Icon)par2Icon).getMinV();
        float f7 = ((Icon)par2Icon).getMaxV();
        float f8 = 1.0F;
        float f9 = 0.5F;
        float f10 = 0.25F;
        float f11;
        
        if (Minecraft.getMinecraft().gameSettings.fancyGraphics)
        {
            GL11.glPushMatrix();

            float f12 = 0.0625F;
            f11 = 0.021875F;
            ItemStack itemstack = par1EntityItem.getEntityItem();
            int j = itemstack.stackSize;
            byte b0 = getMiniItemCount(itemstack);
            
            GL11.glRotatef((((float)par1EntityItem.age) / 20.0F + par1EntityItem.hoverStart) * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            
            GL11.glTranslatef(-f9, -f10, -((f12 + f11) * (float)b0 / 2.0F));

            for (int k = 0; k < b0; ++k)
            {
                if (k > 0)
                {
                    float x = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    float y = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    float z = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    GL11.glTranslatef(x, y, f12 + f11);
                }
                else
                {
                    GL11.glTranslatef(0f, 0f, f12 + f11);
                }

                if (itemstack.getItemSpriteNumber() == 0)
                {
                    texturemanager.bindTexture(TextureMap.locationBlocksTexture);
                }
                else
                {
                    texturemanager.bindTexture(TextureMap.locationItemsTexture);
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                ItemRenderer.renderItemIn2D(tessellator, f5, f6, f4, f7, ((Icon)par2Icon).getIconWidth(), ((Icon)par2Icon).getIconHeight(), f12);

                if (itemstack.hasEffect(pass))
                {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    texturemanager.bindTexture(glint);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float f13 = 0.76F;
                    GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f14 = 0.125F;
                    GL11.glScalef(f14, f14, f14);
                    float f15 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(f15, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f14, f14, f14);
                    f15 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-f15, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }
            }

            GL11.glPopMatrix();
    }
        
        else
        {
            for (int l = 0; l < par3; ++l)
            {
                GL11.glPushMatrix();

                if (l > 0)
                {
                    f11 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f16 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f17 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(f11, f16, f17);
                }
                
                GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);

                GL11.glColor4f(par5, par6, par7, 1.0F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.addVertexWithUV((double)(0.0F - f9), (double)(0.0F - f10), 0.0D, (double)f4, (double)f7);
                tessellator.addVertexWithUV((double)(f8 - f9), (double)(0.0F - f10), 0.0D, (double)f5, (double)f7);
                tessellator.addVertexWithUV((double)(f8 - f9), (double)(1.0F - f10), 0.0D, (double)f5, (double)f6);
                tessellator.addVertexWithUV((double)(0.0F - f9), (double)(1.0F - f10), 0.0D, (double)f4, (double)f6);
                tessellator.draw();
                GL11.glPopMatrix();
            }
        }
    }

    public byte getMiniItemCount(ItemStack stack)
    {
        byte ret = 1;
        if (stack.stackSize > 1)
            ret = 2;
        if (stack.stackSize > 15)
            ret = 3;
        if (stack.stackSize > 31)
            ret = 4;
        return ret;
    }

    public byte getMiniBlockCount(ItemStack stack)
    {
        byte ret = 1;
        if (stack.stackSize > 1)
            ret = 2;
        if (stack.stackSize > 5)
            ret = 3;
        if (stack.stackSize > 20)
            ret = 4;
        if (stack.stackSize > 40)
            ret = 5;
        return ret;
    }

    public void renderIcon(int x, int y, Icon icon, int par4, int par5)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + par5), (double) zLevel, (double) icon.getMinU(), (double) icon.getMaxV());
        tessellator.addVertexWithUV((double) (x + par4), (double) (y + par5), (double) zLevel, (double) icon.getMaxU(), (double) icon.getMaxV());
        tessellator.addVertexWithUV((double) (x + par4), (double) (y + 0), (double) zLevel, (double) icon.getMaxU(), (double) icon.getMinV());
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel, (double) icon.getMinU(), (double) icon.getMinV());
        tessellator.draw();
    }

    private void renderEffect(TextureManager manager, int x, int y)
    {
        GL11.glDepthFunc(GL11.GL_GREATER);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        manager.bindTexture(glint);
        this.zLevel -= 50.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
        GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
        this.renderGlint(x * 431278612 + y * 32178161, x - 2, y - 2, 20, 20);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        this.zLevel += 50.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
    }

    private void renderGlint(int par1, int par2, int par3, int par4, int par5)
    {
        for (int j1 = 0; j1 < 2; ++j1)
        {
            if (j1 == 0)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            if (j1 == 1)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            float f = 0.00390625F;
            float f1 = 0.00390625F;
            float f2 = (float) (Minecraft.getSystemTime() % (long) (3000 + j1 * 1873)) / (3000.0F + (float) (j1 * 1873)) * 256.0F;
            float f3 = 0.0F;
            Tessellator tessellator = Tessellator.instance;
            float f4 = 4.0F;

            if (j1 == 1)
            {
                f4 = -1.0F;
            }

            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double) (par2 + 0), (double) (par3 + par5), (double) this.zLevel, (double) ((f2 + (float) par5 * f4) * f), (double) ((f3 + (float) par5) * f1));
            tessellator.addVertexWithUV((double) (par2 + par4), (double) (par3 + par5), (double) this.zLevel, (double) ((f2 + (float) par4 + (float) par5 * f4) * f), (double) ((f3 + (float) par5) * f1));
            tessellator.addVertexWithUV((double) (par2 + par4), (double) (par3 + 0), (double) this.zLevel, (double) ((f2 + (float) par4) * f), (double) ((f3 + 0.0F) * f1));
            tessellator.addVertexWithUV((double) (par2 + 0), (double) (par3 + 0), (double) this.zLevel, (double) ((f2 + 0.0F) * f), (double) ((f3 + 0.0F) * f1));
            tessellator.draw();
        }
    }

}