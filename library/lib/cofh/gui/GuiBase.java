package lib.cofh.gui;

import java.util.ArrayList;

import lib.cofh.gui.element.ElementBase;
import lib.cofh.gui.element.TabBase;
import lib.cofh.gui.render.IconRegistry;
import lib.cofh.gui.render.RenderHelper;
import lib.cofh.util.MathHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.util.Icon;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * Base class for a modular GUIs. Works with Elements {@link ElementBase} and Tabs {@link TabBase} which are both modular elements.
 * 
 * @author King Lemming
 */
public abstract class GuiBase extends GuiContainer {
    
    public static final String       PATH_ELEMENTS = "/mods/lib/textures/gui/elements/";
    public static final String       PATH_ICONS    = "/mods/lib/textures/gui/icons/";
    
    protected int                    mouseX        = 0;
    protected int                    mouseY        = 0;
    
    protected String                 name;
    protected String                 texture;
    protected ArrayList<TabBase>     tabs          = new ArrayList<TabBase>();
    protected ArrayList<ElementBase> elements      = new ArrayList<ElementBase>();
    
    public GuiBase(final Container container) {
        
        super(container);
    }
    
    public GuiBase(final Container container, final String texture) {
        
        super(container);
        this.texture = texture;
    }
    
    @Override
    public void initGui() {
        
        super.initGui();
        tabs.clear();
        elements.clear();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int x, final int y) {
        
        GL11.glDisable(GL11.GL_LIGHTING);
        drawTooltips();
        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int x, final int y) {
        
        updateElements();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        drawElements();
        drawTabs();
    }
    
    @Override
    protected void mouseClicked(final int x, final int y, final int mouseButton) {
        
        super.mouseClicked(x, y, mouseButton);
        
        final TabBase tab = getTabAtPosition(mouseX, mouseY);
        
        if ((tab != null) && !tab.handleMouseClicked(mouseX, mouseY, mouseButton)) {
            for (final TabBase other : tabs) {
                if ((other != tab) && other.open && (other.side == tab.side)) {
                    other.toggleOpen();
                }
            }
            tab.toggleOpen();
        }
    }
    
    @Override
    public void handleMouseInput() {
        
        final int x = (Mouse.getEventX() * width) / mc.displayWidth;
        final int y = height - ((Mouse.getEventY() * height) / mc.displayHeight) - 1;
        
        mouseX = x - guiLeft;
        mouseY = y - guiTop;
        
        super.handleMouseInput();
    }
    
    /**
     * Draws the elements for this GUI.
     */
    protected void drawElements() {
        
        for (final ElementBase element : elements) {
            element.draw();
        }
    }
    
    /**
     * Draws the tabs for this GUI. Handles Tab open/close animation.
     */
    protected void drawTabs() {
        
        int yPosRight = 4;
        int yPosLeft = 4;
        
        for (final TabBase tab : tabs) {
            tab.update();
            if (!tab.isVisible()) {
                continue;
            }
            if (tab.side == 0) {
                tab.draw(guiLeft, guiTop + yPosLeft);
                yPosLeft += tab.currentHeight;
            } else {
                tab.draw(guiLeft + xSize, guiTop + yPosRight);
                yPosRight += tab.currentHeight;
            }
        }
    }
    
    protected void drawTooltips() {
        
        final TabBase tab = getTabAtPosition(mouseX, mouseY);
        
        if (tab != null) {
            drawTooltip(tab.getTooltip());
            return;
        }
        final ElementBase element = getElementAtPosition(mouseX, mouseY);
        
        if (element != null) {
            drawTooltip(element.getTooltip());
            return;
        }
    }
    
    /* ELEMENTS */
    public ElementBase addElement(final ElementBase element) {
        
        elements.add(element);
        return element;
    }
    
    public TabBase addTab(final TabBase tab) {
        
        tabs.add(tab);
        if ((TabTracker.getOpenedLeftTab() != null) && tab.getClass().equals(TabTracker.getOpenedLeftTab())) {
            tab.setFullyOpen();
        } else if ((TabTracker.getOpenedRightTab() != null) && tab.getClass().equals(TabTracker.getOpenedRightTab())) {
            tab.setFullyOpen();
        }
        return tab;
    }
    
    protected ElementBase getElementAtPosition(final int mX, final int mY) {
        
        for (final ElementBase element : elements) {
            if (element.intersectsWith(mX, mY)) {
                return element;
            }
        }
        return null;
    }
    
    protected TabBase getTabAtPosition(final int mX, final int mY) {
        
        int xShift = 0;
        int yShift = 4;
        
        for (final TabBase tab : tabs) {
            if (!tab.isVisible() || (tab.side == 1)) {
                continue;
            }
            tab.currentShiftX = xShift;
            tab.currentShiftY = yShift;
            if (tab.intersectsWith(mX, mY, xShift, yShift)) {
                return tab;
            }
            yShift += tab.currentHeight;
        }
        
        xShift = xSize;
        yShift = 4;
        
        for (final TabBase tab : tabs) {
            if (!tab.isVisible() || (tab.side == 0)) {
                continue;
            }
            tab.currentShiftX = xShift;
            tab.currentShiftY = yShift;
            if (tab.intersectsWith(mX, mY, xShift, yShift)) {
                return tab;
            }
            yShift += tab.currentHeight;
        }
        return null;
    }
    
    protected void updateElements() {
        
    }
    
    /* HELPERS */
    /**
     * Essentially a placeholder method for tabs to use should they need to draw a button.
     */
    public void drawButton(final Icon icon, final int x, final int y, final int spriteSheet, final int mode) {
        
        drawIcon(icon, x, y, spriteSheet);
    }
    
    public void drawButton(final String iconName, final int x, final int y, final int spriteSheet, final int mode) {
        
        drawButton(IconRegistry.getIcon(iconName), x, y, spriteSheet, mode);
    }
    
    /**
     * Simple method used to draw a fluid of arbitrary size.
     */
    /*
     * public void drawFluid(final int x, final int y, final FluidStack fluid, final int width, final int height) { if ((fluid == null) || (fluid.getFluid() == null)) { return; }
     * RenderHelper.setBlockTextureSheet(); drawTiledTexture(x, y, fluid.getFluid().getIcon(fluid), width, height); }
     */
    
    public void drawTiledTexture(final int x, final int y, final Icon icon, final int width, final int height) {
        
        int i = 0;
        int j = 0;
        
        int drawHeight = 0;
        int drawWidth = 0;
        
        for (i = 0; i < width; i += 16) {
            for (j = 0; j < height; j += 16) {
                drawWidth = MathHelper.minI(width - i, 16);
                drawHeight = MathHelper.minI(height - j, 16);
                drawScaledTexturedModelRectFromIcon(x + i, y + j, icon, drawWidth, drawHeight);
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }
    
    public void drawIcon(final Icon icon, final int x, final int y, final int spriteSheet) {
        
        if (spriteSheet == 0) {
            RenderHelper.setBlockTextureSheet();
        } else {
            RenderHelper.setItemTextureSheet();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
        drawTexturedModelRectFromIcon(x, y, icon, 16, 16);
    }
    
    public void drawIcon(final String iconName, final int x, final int y, final int spriteSheet) {
        
        drawIcon(IconRegistry.getIcon(iconName), x, y, spriteSheet);
    }
    
    public void drawSizedTexturedModalRect(final int x, final int y, final int u, final int v, final int width, final int height, final float texW, final float texH) {
        
        final float texU = 1 / texW;
        final float texV = 1 / texH;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, (u + 0) * texU, (v + height) * texV);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) * texU, (v + height) * texV);
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, (u + width) * texU, (v + 0) * texV);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, (u + 0) * texU, (v + 0) * texV);
        tessellator.draw();
    }
    
    public void drawScaledTexturedModelRectFromIcon(final int x, final int y, final Icon icon, final int width, final int height) {
        
        if (icon == null) {
            return;
        }
        final double minU = icon.getMinU();
        final double maxU = icon.getMaxU();
        final double minV = icon.getMinV();
        final double maxV = icon.getMaxV();
        
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, minU, minV + (((maxV - minV) * height) / 16F));
        tessellator.addVertexWithUV(x + width, y + height, zLevel, minU + (((maxU - minU) * width) / 16F), minV + (((maxV - minV) * height) / 16F));
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, minU + (((maxU - minU) * width) / 16F), minV);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, minU, minV);
        tessellator.draw();
    }
    
    public void drawTooltip(final String tooltip) {
        
        if ((tooltip == null) || tooltip.equals("")) {
            return;
        }
        drawCreativeTabHoveringText(tooltip, mouseX, mouseY);
    }
    
    /**
     * Passthrough method for tab use.
     */
    public void mouseClicked(final int mouseButton) {
        
        super.mouseClicked(guiLeft + mouseX, guiTop + mouseY, mouseButton);
    }
    
    protected int getCenteredOffset(final String string) {
        
        return this.getCenteredOffset(string, xSize);
    }
    
    protected int getCenteredOffset(final String string, final int xWidth) {
        
        return (xWidth - fontRenderer.getStringWidth(string)) / 2;
    }
    
}
