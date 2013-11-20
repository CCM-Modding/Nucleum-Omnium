package lib.cofh.gui.element;

import lib.cofh.gui.GuiBase;
import lib.cofh.render.RenderHelper;
import lib.cofh.util.StringHelper;

public class ElementButton extends ElementBase
{
    int sheetX;
    int sheetY;
    int hoverX;
    int hoverY;
    int disabledX = 0;
    int disabledY = 0;
    boolean disabled = false;
    String tooltip;

    public ElementButton(GuiBase gui, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int sizeX, int sizeY, String texture)
    {
        super(gui, posX, posY);
        setName(name);
        setSize(sizeX, sizeY);
        setTexture(texture, texW, texH);
        this.sheetX = sheetX;
        this.sheetY = sheetY;
        this.hoverX = hoverX;
        this.hoverY = hoverY;
    }

    public ElementButton(GuiBase gui, int posX, int posY, String name, int sheetX, int sheetY, int hoverX, int hoverY, int disabledX, int disabledY, int sizeX, int sizeY,
            String texture)
    {
        super(gui, posX, posY);
        setName(name);
        setSize(sizeX, sizeY);
        setTexture(texture, texW, texH);
        this.sheetX = sheetX;
        this.sheetY = sheetY;
        this.hoverX = hoverX;
        this.hoverY = hoverY;
        this.disabledX = disabledX;
        this.disabledY = disabledY;
    }

    public String toolTip;

    @Override
    public void draw()
    {
        RenderHelper.bindTexture(texture);
        if (!disabled)
        {
            if (intersectsWith(gui.getMouseX(), gui.getMouseY()))
            {

                drawTexturedModalRect(posX, posY, hoverX, hoverY, sizeX, sizeY);
            } else
            {
                drawTexturedModalRect(posX, posY, sheetX, sheetY, sizeX, sizeY);
            }
        } else
        {
            drawTexturedModalRect(posX, posY, disabledX, disabledY, sizeX, sizeY);
        }
    }

    @Override
    public String getTooltip()
    {
        return StringHelper.localize(tooltip);
    }

    public ElementButton setToolTip(String tooltip)
    {
        this.tooltip = tooltip;
        return this;
    }

    public ElementButton clearToolTip()
    {
        tooltip = null;
        return this;
    }

    @Override
    public boolean handleMouseClicked(int x, int y, int mouseButton)
    {
        if (!disabled)
        {
            gui.handleElementButtonClick(getName(), mouseButton);
            return true;
        }
        return false;
    }

    public void setSheetX(int pos)
    {
        sheetX = pos;
    }

    public void setSheetY(int pos)
    {
        sheetY = pos;
    }

    public void setHoverX(int pos)
    {
        hoverX = pos;
    }

    public void setHoverY(int pos)
    {
        hoverY = pos;
    }

    public void setDisabled()
    {
        disabled = true;
    }

    public void setActive()
    {
        disabled = false;
    }
}