package lib.cofh.gui.element;

import lib.cofh.gui.GuiBase;
import lib.cofh.render.RenderHelper;

/**
 * Basic element which can render an arbitrary texture.
 * 
 * @author King Lemming
 */
public class ElementSimple extends ElementBase
{

    int texU = 0;

    int texV = 0;

    public ElementSimple(final GuiBase gui,
                         final int posX,
                         final int posY)
    {

        super(gui, posX, posY);
    }

    public ElementSimple setTextureOffsets(final int u, final int v)
    {

        this.texU = u;
        this.texV = v;
        return this;
    }

    @Override
    public void draw()
    {

        if (!this.visible){
            return;
        }
        RenderHelper.bindTexture(this.texture);
        this.drawTexturedModalRect(this.posX, this.posY, this.texU, this.texV, this.sizeX, this.sizeY);
    }

    @Override
    public String getTooltip()
    {

        return null;
    }

}
