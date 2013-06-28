package lib.cofh.gui.element;

import lib.cofh.gui.GuiBase;
import lib.cofh.gui.render.RenderHelper;

/**
 * Basic element which can render an arbitrary texture.
 * 
 * @author King Lemming
 */
public class ElementSimple extends ElementBase {

	int	texU	= 0;
	int	texV	= 0;

	public ElementSimple(final GuiBase gui, final int posX, final int posY) {

		super(gui, posX, posY);
	}

	public ElementSimple setTextureOffsets(final int u, final int v) {

		texU = u;
		texV = v;
		return this;
	}

	@Override
	public void draw() {

		if (!visible) {
			return;
		}
		RenderHelper.bindTexture(texture);
		drawTexturedModalRect(posX, posY, texU, texV, sizeX, sizeY);
	}

	@Override
	public String getTooltip() {

		return null;
	}

}
