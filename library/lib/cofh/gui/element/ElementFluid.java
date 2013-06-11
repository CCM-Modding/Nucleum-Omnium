package lib.cofh.gui.element;

import lib.cofh.gui.GuiBase;

public class ElementFluid extends ElementBase
{

    public ElementFluid(final GuiBase gui,
                        final int posX,
                        final int posY)
    {
        super(gui, posX, posY);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void draw()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getTooltip()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * public FluidStack fluid;
     * public ElementFluid(GuiBase gui,
     * int posX,
     * int posY)
     * {
     * super(gui, posX, posY);
     * }
     * public ElementFluid setFluid(FluidStack fluid)
     * {
     * this.fluid = fluid;
     * return this;
     * }
     * @Override
     * public void draw()
     * {
     * if (!visible){
     * return;
     * }
     * gui.drawFluid(posX, posY, fluid, sizeX, sizeY);
     * }
     * @Override
     * public String getTooltip()
     * {
     * return null;
     * }
     */

}