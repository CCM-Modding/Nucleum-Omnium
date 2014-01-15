package ccm.nucleum.omnium.inventory.gui.element;

import ccm.nucleum.omnium.utils.handler.ResourceHandler;

import lib.cofh.gui.GuiBase;
import lib.cofh.gui.element.ElementDualScaled;

public class ProgressElement extends ElementDualScaled
{
    public static final String ELEMENT_NAME = "progress";

    public ProgressElement(GuiBase gui, int posX, int posY, String guiName)
    {
        super(gui, posX, posY);
        setMode(Mode.RIGHT);
        setSize(24, 16);
        setTexture(ResourceHandler.getElement(ELEMENT_NAME, guiName), 128, 64);
        setVisible(true);
    }
}