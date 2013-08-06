package lib.cofh.gui.element;

import net.minecraftforge.fluids.FluidStack;

import lib.cofh.gui.GuiBase;

public class ElementFluid extends ElementBase
{

    public FluidStack fluid;

    public ElementFluid(final GuiBase gui, final int posX, final int posY)
    {

        super(gui, posX, posY);
    }

    public ElementFluid setFluid(final FluidStack fluid)
    {

        this.fluid = fluid;
        return this;
    }

    @Override
    public void draw()
    {

        if (!visible)
        {
            return;
        }
        gui.drawFluid(posX, posY, fluid, sizeX, sizeY);
    }

    @Override
    public String getTooltip()
    {

        return null;
    }

}
