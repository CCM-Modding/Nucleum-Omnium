package lib.cofh.gui.element;

import net.minecraftforge.fluids.FluidTank;

import lib.cofh.gui.GuiBase;
import lib.cofh.render.RenderHelper;

public class ElementFluidTank extends ElementBase
{

    protected FluidTank tank;
    protected int       gaugeType;

    public int          scale = 60;

    public ElementFluidTank(final GuiBase gui, final int posX, final int posY, final FluidTank tank)
    {

        super(gui, posX, posY);
        this.tank = tank;

        texture = GuiBase.PATH_ELEMENTS + "FluidTank.png";
        texW = 64;
        texH = 64;

        sizeX = 16;
        sizeY = scale;
    }

    public ElementFluidTank(final GuiBase gui,
                            final int posX,
                            final int posY,
                            final FluidTank tank,
                            final String texture)
    {

        super(gui, posX, posY);
        this.tank = tank;

        this.texture = texture;
        texW = 64;
        texH = 64;

        sizeX = 16;
        sizeY = scale;
    }

    public ElementFluidTank setGauge(final int gaugeType)
    {

        this.gaugeType = gaugeType;
        return this;
    }

    @Override
    public void draw()
    {

        if (!visible)
        {
            return;
        }
        final int amount = getScaled();
        gui.drawFluid(posX, (posY + sizeY) - amount, tank.getFluid(), sizeX, amount);
        RenderHelper.bindTexture(texture);
        drawTexturedModalRect(posX, posY, 32 + (gaugeType * 16), 1, sizeX, sizeY);
    }

    @Override
    public String getTooltip()
    {

        return "" + tank.getFluidAmount() + " / " + tank.getCapacity() + " mB";
    }

    @Override
    public boolean handleMouseClicked(final int x, final int y, final int mouseButton)
    {

        return false;
    }

    int getScaled()
    {

        return (tank.getFluidAmount() * sizeY) / tank.getCapacity();
    }

}
