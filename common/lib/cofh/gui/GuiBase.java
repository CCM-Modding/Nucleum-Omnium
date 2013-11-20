package lib.cofh.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import lib.cofh.gui.element.ElementBase;
import lib.cofh.gui.element.TabBase;
import lib.cofh.gui.slot.SlotFalseCopy;
import lib.cofh.render.IconRegistry;
import lib.cofh.render.RenderHelper;
import lib.cofh.util.StringHelper;

/**
 * Base class for a modular GUIs. Works with Elements {@link ElementBase} and Tabs {@link TabBase} which are both modular elements.
 * 
 * @author King Lemming
 */
public abstract class GuiBase extends GuiContainer
{
    protected boolean drawInventory = true;
    protected int mouseX = 0;
    protected int mouseY = 0;

    protected int lastIndex = -1;

    protected String name;
    protected ResourceLocation texture;
    protected ArrayList<TabBase> tabs = new ArrayList<TabBase>();
    protected ArrayList<ElementBase> elements = new ArrayList<ElementBase>();

    public GuiBase(Container container)
    {
        super(container);
    }

    public GuiBase(Container container, ResourceLocation texture)
    {
        super(container);
        this.texture = texture;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        tabs.clear();
        elements.clear();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        fontRenderer.drawString(StringHelper.localize(name), getCenteredOffset(StringHelper.localize(name)), 6, 0x404040);
        if (drawInventory)
        {
            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 3, 0x404040);
        }
        drawTooltips();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        updateElements();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        drawElements();
        drawTabs();
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton)
    {
        super.mouseClicked(x, y, mouseButton);

        TabBase tab = getTabAtPosition(mouseX, mouseY);

        if ((tab != null) && !tab.handleMouseClicked(mouseX, mouseY, mouseButton))
        {
            for (TabBase other : tabs)
            {
                if ((other != tab) && other.open && (other.side == tab.side))
                {
                    other.toggleOpen();
                }
            }
            tab.toggleOpen();
        }
        ElementBase element = getElementAtPosition(mouseX, mouseY);

        if (element != null)
        {
            element.handleMouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    /**
     * Returns the slot at the given coordinates or null if there is none.
     */
    protected Slot getSlotAtPosition(int par1, int par2)
    {
        for (int k = 0; k < inventorySlots.inventorySlots.size(); ++k)
        {
            Slot slot = (Slot) inventorySlots.inventorySlots.get(k);

            if (isMouseOverSlot(slot, par1, par2))
            {
                return slot;
            }
        }

        return null;
    }

    /**
     * Returns if the passed mouse position is over the specified slot.
     */
    protected boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
    {
        return isPointInRegion(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
    }

    @Override
    protected void mouseClickMove(int mX, int mY, int lastClick, long timeSinceClick)
    {
        Slot slot = getSlotAtPosition(mX, mY);
        ItemStack itemstack = mc.thePlayer.inventory.getItemStack();

        if (field_94076_q && (slot != null) && (itemstack != null) && (slot instanceof SlotFalseCopy))
        {
            if (lastIndex != slot.slotNumber)
            {
                lastIndex = slot.slotNumber;
                handleMouseClick(slot, slot.slotNumber, 0, 0);
            }
        } else
        {
            lastIndex = -1;
            super.mouseClickMove(mX, mY, lastClick, timeSinceClick);
        }
    }

    @Override
    public void handleMouseInput()
    {
        int x = (Mouse.getEventX() * width) / mc.displayWidth;
        int y = height - ((Mouse.getEventY() * height) / mc.displayHeight) - 1;

        mouseX = x - guiLeft;
        mouseY = y - guiTop;

        super.handleMouseInput();
    }

    /**
     * Draws the elements for this GUI.
     */
    protected void drawElements()
    {
        for (ElementBase element : elements)
        {
            element.draw();
        }
    }

    /**
     * Draws the tabs for this GUI. Handles Tab open/close animation.
     */
    protected void drawTabs()
    {
        int yPosRight = 4;
        int yPosLeft = 4;

        for (TabBase tab : tabs)
        {
            tab.update();
            if (!tab.isVisible())
            {
                continue;
            }
            if (tab.side == 0)
            {
                tab.draw(guiLeft, guiTop + yPosLeft);
                yPosLeft += tab.currentHeight;
            } else
            {
                tab.draw(guiLeft + xSize, guiTop + yPosRight);
                yPosRight += tab.currentHeight;
            }
        }
    }

    protected void drawTooltips()
    {
        TabBase tab = getTabAtPosition(mouseX, mouseY);

        if (tab != null)
        {
            drawTooltip(tab.getTooltip());
            return;
        }
        ElementBase element = getElementAtPosition(mouseX, mouseY);

        if (element != null)
        {
            drawTooltip(element.getTooltip());
            return;
        }

    }

    /* ELEMENTS */
    public ElementBase addElement(ElementBase element)
    {
        elements.add(element);
        return element;
    }

    public TabBase addTab(TabBase tab)
    {
        tabs.add(tab);
        if ((TabTracker.getOpenedLeftTab() != null) && tab.getClass().equals(TabTracker.getOpenedLeftTab()))
        {
            tab.setFullyOpen();
        } else if ((TabTracker.getOpenedRightTab() != null) && tab.getClass().equals(TabTracker.getOpenedRightTab()))
        {
            tab.setFullyOpen();
        }
        return tab;
    }

    protected ElementBase getElementAtPosition(int mX, int mY)
    {
        for (ElementBase element : elements)
        {
            if (element.intersectsWith(mX, mY))
            {
                return element;
            }
        }
        return null;
    }

    protected TabBase getTabAtPosition(int mX, int mY)
    {
        int xShift = 0;
        int yShift = 4;

        for (TabBase tab : tabs)
        {
            if (!tab.isVisible() || (tab.side == 1))
            {
                continue;
            }
            tab.currentShiftX = xShift;
            tab.currentShiftY = yShift;
            if (tab.intersectsWith(mX, mY, xShift, yShift))
            {
                return tab;
            }
            yShift += tab.currentHeight;
        }

        xShift = xSize;
        yShift = 4;

        for (TabBase tab : tabs)
        {
            if (!tab.isVisible() || (tab.side == 0))
            {
                continue;
            }
            tab.currentShiftX = xShift;
            tab.currentShiftY = yShift;
            if (tab.intersectsWith(mX, mY, xShift, yShift))
            {
                return tab;
            }
            yShift += tab.currentHeight;
        }
        return null;
    }

    protected void updateElements()
    {}

    public void handleElementButtonClick(String buttonName, int mouseButton)
    {}

    /* HELPERS */
    /**
     * Essentially a placeholder method for tabs to use should they need to draw a button.
     */
    public void drawButton(Icon icon, int x, int y, int spriteSheet, int mode)
    {
        drawIcon(icon, x, y, spriteSheet);
    }

    public void drawButton(String iconName, int x, int y, int spriteSheet, int mode)
    {
        drawButton(IconRegistry.getIcon(iconName), x, y, spriteSheet, mode);
    }

    /**
     * Simple method used to draw a fluid of arbitrary size.
     */
    public void drawFluid(int x, int y, FluidStack fluid, int width, int height)
    {
        if ((fluid == null) || (fluid.getFluid() == null))
        {
            return;
        }
        RenderHelper.setBlockTextureSheet();
        drawTiledTexture(x, y, fluid.getFluid().getIcon(fluid), width, height);
    }

    public void drawTiledTexture(int x, int y, Icon icon, int width, int height)
    {
        int i = 0;
        int j = 0;

        int drawHeight = 0;
        int drawWidth = 0;

        for (i = 0; i < width; i += 16)
        {
            for (j = 0; j < height; j += 16)
            {
                drawWidth = Math.min(width - i, 16);
                drawHeight = Math.min(height - j, 16);
                drawScaledTexturedModelRectFromIcon(x + i, y + j, icon, drawWidth, drawHeight);
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }

    public void drawIcon(Icon icon, int x, int y, int spriteSheet)
    {
        if (spriteSheet == 0)
        {
            RenderHelper.setBlockTextureSheet();
        } else
        {
            RenderHelper.setItemTextureSheet();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
        drawTexturedModelRectFromIcon(x, y, icon, 16, 16);
    }

    public void drawIcon(String iconName, int x, int y, int spriteSheet)
    {
        drawIcon(IconRegistry.getIcon(iconName), x, y, spriteSheet);
    }

    public void drawSizedTexturedModalRect(int x, int y, int u, int v, int width, int height, float texW, float texH)
    {
        float texU = 1 / texW;
        float texV = 1 / texH;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, (u + 0) * texU, (v + height) * texV);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) * texU, (v + height) * texV);
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, (u + width) * texU, (v + 0) * texV);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, (u + 0) * texU, (v + 0) * texV);
        tessellator.draw();
    }

    public void drawScaledTexturedModelRectFromIcon(int x, int y, Icon icon, int width, int height)
    {
        if (icon == null)
        {
            return;
        }
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, minU, minV + (((maxV - minV) * height) / 16F));
        tessellator.addVertexWithUV(x + width, y + height, zLevel, minU + (((maxU - minU) * width) / 16F), minV + (((maxV - minV) * height) / 16F));
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, minU + (((maxU - minU) * width) / 16F), minV);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, minU, minV);
        tessellator.draw();
    }

    public void drawTooltip(String tooltip)
    {
        if ((tooltip == null) || tooltip.equals(""))
        {
            return;
        }
        drawTooltipHoveringText(Arrays.asList(new String[]
        { tooltip }), mouseX, mouseY, fontRenderer);
    }

    protected void drawTooltipHoveringText(List list, int x, int y, FontRenderer font)
    {
        if (list.isEmpty())
        {
            return;
        }
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        int k = 0;
        Iterator iterator = list.iterator();

        while (iterator.hasNext())
        {
            String s = (String) iterator.next();
            int l = font.getStringWidth(s);

            if (l > k)
            {
                k = l;
            }
        }
        int i1 = x + 12;
        int j1 = y - 12;
        int k1 = 8;

        if (list.size() > 1)
        {
            k1 += 2 + ((list.size() - 1) * 10);
        }
        if ((i1 + k) > width)
        {
            i1 -= 28 + k;
        }
        if ((j1 + k1 + 6) > height)
        {
            j1 = height - k1 - 6;
        }
        zLevel = 300.0F;
        itemRenderer.zLevel = 300.0F;
        int l1 = -267386864;
        drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
        drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
        drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
        drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
        drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
        int i2 = 1347420415;
        int j2 = ((i2 & 16711422) >> 1) | (i2 & -16777216);
        drawGradientRect(i1 - 3, (j1 - 3) + 1, (i1 - 3) + 1, (j1 + k1 + 3) - 1, i2, j2);
        drawGradientRect(i1 + k + 2, (j1 - 3) + 1, i1 + k + 3, (j1 + k1 + 3) - 1, i2, j2);
        drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, (j1 - 3) + 1, i2, i2);
        drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

        for (int k2 = 0; k2 < list.size(); ++k2)
        {
            String s1 = (String) list.get(k2);
            font.drawStringWithShadow(s1, i1, j1, -1);

            if (k2 == 0)
            {
                j1 += 2;
            }
            j1 += 10;
        }
        zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    /**
     * Passthrough method for tab use.
     */
    public void mouseClicked(int mouseButton)
    {
        super.mouseClicked(guiLeft + mouseX, guiTop + mouseY, mouseButton);
    }

    protected int getCenteredOffset(String string)
    {
        return this.getCenteredOffset(string, xSize);
    }

    protected int getCenteredOffset(String string, int xWidth)
    {
        return (xWidth - fontRenderer.getStringWidth(string)) / 2;
    }

    public int getGuiLeft()
    {
        return guiLeft;
    }

    public int getGuiTop()
    {
        return guiTop;
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    public void overlayRecipe()
    {}
}