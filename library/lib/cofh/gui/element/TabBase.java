package lib.cofh.gui.element;

import lib.cofh.gui.GuiBase;
import lib.cofh.gui.TabTracker;
import lib.cofh.render.RenderHelper;

import org.lwjgl.opengl.GL11;

/**
 * Base class for a tab element. Has self-contained rendering methods and a link
 * back to the {@link GuiBase} it is a part of.
 * 
 * @author King Lemming
 */
public abstract class TabBase extends ElementBase {

    public boolean open;

    public byte    side            = 1;

    public int     backgroundColor = 0xffffff;

    public int     currentShiftX   = 0;

    public int     currentShiftY   = 0;

    public int     minWidth        = 22;

    public int     maxWidth        = 124;

    public int     currentWidth    = this.minWidth;

    public int     minHeight       = 22;

    public int     maxHeight       = 22;

    public int     currentHeight   = this.minHeight;

    public String  textureLeft     = GuiBase.PATH_ELEMENTS + "Tab_Left.png";

    public String  textureRight    = GuiBase.PATH_ELEMENTS + "Tab_Right.png";

    public TabBase(final GuiBase gui) {

        super(gui, 0, 0);
    }

    public TabBase(final GuiBase gui, final int side) {

        super(gui, 0, 0);
        this.side = (byte) side;
    }

    @Override
    public void update() {

        if (this.open && this.currentWidth < this.maxWidth)
            this.currentWidth += 8;
        else if (!this.open && this.currentWidth > this.minWidth)
            this.currentWidth -= 8;

        if (this.currentWidth > this.maxWidth)
            this.currentWidth = this.maxWidth;
        else if (this.currentWidth < this.minWidth)
            this.currentWidth = this.minWidth;

        if (this.open && this.currentHeight < this.maxHeight)
            this.currentHeight += 8;
        else if (!this.open && this.currentHeight > this.minHeight)
            this.currentHeight -= 8;

        if (this.currentHeight > this.maxHeight)
            this.currentHeight = this.maxHeight;
        else if (this.currentHeight < this.minHeight)
            this.currentHeight = this.minHeight;

        if (this.open && this.currentWidth == this.maxWidth && this.currentHeight == this.maxHeight)
            this.setFullyOpen();
    }

    public boolean intersectsWith(final int mouseX, final int mouseY, final int shiftX,
            final int shiftY) {

        if (this.side == 0) {
            if (mouseX <= shiftX && mouseX >= shiftX - this.currentWidth && mouseY >= shiftY
                    && mouseY <= shiftY + this.currentHeight)
                return true;
        } else if (mouseX >= shiftX && mouseX <= shiftX + this.currentWidth && mouseY >= shiftY
                && mouseY <= shiftY + this.currentHeight)
            return true;
        return false;
    }

    protected void drawBackground() {

        final float colorR = (this.backgroundColor >> 16 & 255) / 255.0F;
        final float colorG = (this.backgroundColor >> 8 & 255) / 255.0F;
        final float colorB = (this.backgroundColor & 255) / 255.0F;

        GL11.glColor4f(colorR, colorG, colorB, 1.0F);

        if (this.side == 0) {
            RenderHelper.bindTexture(this.textureLeft);

            this.gui.drawTexturedModalRect(this.posX - this.currentWidth, this.posY + 4, 0,
                    256 - this.currentHeight + 4, 4, this.currentHeight - 4);
            this.gui.drawTexturedModalRect(this.posX - this.currentWidth + 4, this.posY,
                    256 - this.currentWidth + 4, 0, this.currentWidth - 4, 4);
            this.gui.drawTexturedModalRect(this.posX - this.currentWidth, this.posY, 0, 0, 4, 4);
            this.gui.drawTexturedModalRect(this.posX - this.currentWidth + 4, this.posY + 4,
                    256 - this.currentWidth + 4, 256 - this.currentHeight + 4,
                    this.currentWidth - 4, this.currentHeight - 4);
        } else {
            RenderHelper.bindTexture(this.textureRight);

            this.gui.drawTexturedModalRect(this.posX, this.posY, 0, 256 - this.currentHeight, 4,
                    this.currentHeight);
            this.gui.drawTexturedModalRect(this.posX + 4, this.posY, 256 - this.currentWidth + 4,
                    0, this.currentWidth - 4, 4);
            this.gui.drawTexturedModalRect(this.posX, this.posY, 0, 0, 4, 4);
            this.gui.drawTexturedModalRect(this.posX + 4, this.posY + 4,
                    256 - this.currentWidth + 4, 256 - this.currentHeight + 4,
                    this.currentWidth - 4, this.currentHeight - 4);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }

    protected void drawTabIcon(final String iconName) {

        int offsetX = 2;
        if (this.side == 0)
            offsetX = 4 - this.currentWidth;
        this.gui.drawIcon(iconName, this.posX + offsetX, this.posY + 3, 1);
    }

    public boolean isFullyOpened() {

        return this.currentWidth >= this.maxWidth;
    }

    public void setFullyOpen() {

        this.open = true;
        this.currentWidth = this.maxWidth;
        this.currentHeight = this.maxHeight;
    }

    public void toggleOpen() {

        if (this.open) {
            this.open = false;
            if (this.side == 0)
                TabTracker.setOpenedLeftTab(null);
            else
                TabTracker.setOpenedRightTab(null);
        } else {
            this.open = true;
            if (this.side == 0)
                TabTracker.setOpenedLeftTab(this.getClass());
            else
                TabTracker.setOpenedRightTab(this.getClass());
        }
    }

}
