package lib.cofh.gui.element;

import lib.cofh.gui.GuiBase;
import lib.cofh.render.RenderHelper;

public class ElementDualScaled extends ElementBase
{
    public enum Mode
    {
        UP,
        RIGHT,
        LEFT;
    }

    public int quantity;
    public Mode mode;
    public boolean background = true;

    public ElementDualScaled(GuiBase gui, int posX, int posY)
    {
        super(gui, posX, posY);
    }

    public ElementDualScaled setMode(Mode mode)
    {
        this.mode = mode;
        return this;
    }

    public ElementDualScaled setQuantity(int quantity)
    {
        this.quantity = quantity;
        return this;
    }

    public ElementDualScaled setBackground(boolean background)
    {
        this.background = background;
        return this;
    }

    @Override
    public void draw()
    {
        if (!visible)
        {
            return;
        }
        RenderHelper.bindTexture(texture);
        if (background)
        {
            drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
        }
        switch (mode)
        {
            case UP:
                // vertical bottom -> top
                drawTexturedModalRect(posX, (posY + sizeY) - quantity, sizeX, sizeY - quantity, sizeX, quantity);
                return;
            case RIGHT:
                // horizontal left -> right
                drawTexturedModalRect(posX, posY, sizeX, 0, quantity, sizeY);
                return;
            case LEFT:
                // horizontal right -> left
                drawTexturedModalRect((posX + sizeX) - quantity, posY, (sizeX + sizeX) - quantity, 0, quantity, sizeY);
                return;
        }
    }
}