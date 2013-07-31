package lib.cofh.gui.element;

import org.lwjgl.opengl.GL11;

import lib.cofh.gui.GuiBase;
import lib.cofh.gui.TabTracker;
import lib.cofh.render.RenderHelper;

/**
 * Base class for a tab element. Has self-contained rendering methods and a link back to the {@link GuiBase}
 * it is a part of.
 * 
 * @author King Lemming
 */
public abstract class TabBase extends ElementBase
{

    public boolean open;
    public byte    side            = 1;

    public int     backgroundColor = 0xffffff;

    public int     currentShiftX   = 0;
    public int     currentShiftY   = 0;

    public int     minWidth        = 22;
    public int     maxWidth        = 124;
    public int     currentWidth    = minWidth;

    public int     minHeight       = 22;
    public int     maxHeight       = 22;
    public int     currentHeight   = minHeight;

    public String  textureLeft     = GuiBase.PATH_ELEMENTS + "Tab_Left.png";
    public String  textureRight    = GuiBase.PATH_ELEMENTS + "Tab_Right.png";

    public TabBase(final GuiBase gui)
    {

        super(gui, 0, 0);
    }

    public TabBase(final GuiBase gui, final int side)
    {

        super(gui, 0, 0);
        this.side = (byte) side;
    }

    @Override
    public void update()
    {

        if (open && (currentWidth < maxWidth))
        {
            currentWidth += 8;
        } else if (!open && (currentWidth > minWidth))
        {
            currentWidth -= 8;
        }

        if (currentWidth > maxWidth)
        {
            currentWidth = maxWidth;
        } else if (currentWidth < minWidth)
        {
            currentWidth = minWidth;
        }

        if (open && (currentHeight < maxHeight))
        {
            currentHeight += 8;
        } else if (!open && (currentHeight > minHeight))
        {
            currentHeight -= 8;
        }

        if (currentHeight > maxHeight)
        {
            currentHeight = maxHeight;
        } else if (currentHeight < minHeight)
        {
            currentHeight = minHeight;
        }

        if (open && (currentWidth == maxWidth) && (currentHeight == maxHeight))
        {
            setFullyOpen();
        }
    }

    public boolean intersectsWith(final int mouseX, final int mouseY, final int shiftX, final int shiftY)
    {

        if (side == 0)
        {
            if ((mouseX <= shiftX) && (mouseX >= (shiftX - currentWidth))
                && (mouseY >= shiftY)
                && (mouseY <= (shiftY + currentHeight)))
            {
                return true;
            }
        } else if ((mouseX >= shiftX) && (mouseX <= (shiftX + currentWidth))
                   && (mouseY >= shiftY)
                   && (mouseY <= (shiftY + currentHeight)))
        {
            return true;
        }
        return false;
    }

    protected void drawBackground()
    {

        final float colorR = ((backgroundColor >> 16) & 255) / 255.0F;
        final float colorG = ((backgroundColor >> 8) & 255) / 255.0F;
        final float colorB = (backgroundColor & 255) / 255.0F;

        GL11.glColor4f(colorR, colorG, colorB, 1.0F);

        if (side == 0)
        {
            RenderHelper.bindTexture(textureLeft);

            gui.drawTexturedModalRect(posX - currentWidth,
                                      posY + 4,
                                      0,
                                      (256 - currentHeight) + 4,
                                      4,
                                      currentHeight - 4);
            gui.drawTexturedModalRect((posX - currentWidth) + 4,
                                      posY,
                                      (256 - currentWidth) + 4,
                                      0,
                                      currentWidth - 4,
                                      4);
            gui.drawTexturedModalRect(posX - currentWidth, posY, 0, 0, 4, 4);
            gui.drawTexturedModalRect((posX - currentWidth) + 4,
                                      posY + 4,
                                      (256 - currentWidth) + 4,
                                      (256 - currentHeight) + 4,
                                      currentWidth - 4,
                                      currentHeight - 4);
        } else
        {
            RenderHelper.bindTexture(textureRight);

            gui.drawTexturedModalRect(posX, posY, 0, 256 - currentHeight, 4, currentHeight);
            gui.drawTexturedModalRect(posX + 4, posY, (256 - currentWidth) + 4, 0, currentWidth - 4, 4);
            gui.drawTexturedModalRect(posX, posY, 0, 0, 4, 4);
            gui.drawTexturedModalRect(posX + 4,
                                      posY + 4,
                                      (256 - currentWidth) + 4,
                                      (256 - currentHeight) + 4,
                                      currentWidth - 4,
                                      currentHeight - 4);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }

    protected void drawTabIcon(final String iconName)
    {

        int offsetX = 2;
        if (side == 0)
        {
            offsetX = 4 - currentWidth;
        }
        gui.drawIcon(iconName, posX + offsetX, posY + 3, 1);
    }

    public boolean isFullyOpened()
    {

        return currentWidth >= maxWidth;
    }

    public void setFullyOpen()
    {

        open = true;
        currentWidth = maxWidth;
        currentHeight = maxHeight;
    }

    public void toggleOpen()
    {

        if (open)
        {
            open = false;
            if (side == 0)
            {
                TabTracker.setOpenedLeftTab(null);
            } else
            {
                TabTracker.setOpenedRightTab(null);
            }
        } else
        {
            open = true;
            if (side == 0)
            {
                TabTracker.setOpenedLeftTab(this.getClass());
            } else
            {
                TabTracker.setOpenedRightTab(this.getClass());
            }
        }
    }

}
