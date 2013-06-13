package lib.cofh.gui.element;

import lib.cofh.gui.GuiBase;

public class ElementFluidTank extends ElementBase {

    public ElementFluidTank(final GuiBase gui, final int posX, final int posY) {
        super(gui, posX, posY);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getTooltip() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * protected FluidTank tank; protected int gaugeType; public int scale = 60;
     * public ElementFluidTank(GuiBase gui, int posX, int posY, FluidTank tank)
     * { super(gui, posX, posY); this.tank = tank; this.texture =
     * GuiBase.PATH_ELEMENTS + "FluidTank.png"; this.texW = 64; this.texH = 64;
     * this.sizeX = 16; this.sizeY = scale; } public ElementFluidTank(GuiBase
     * gui, int posX, int posY, FluidTank tank, String texture) { super(gui,
     * posX, posY); this.tank = tank; this.texture = texture; this.texW = 64;
     * this.texH = 64; this.sizeX = 16; this.sizeY = scale; } public
     * ElementFluidTank setGauge(int gaugeType) { this.gaugeType = gaugeType;
     * return this; }
     * 
     * @Override public void draw() { if (!visible) { return; } int amount =
     * getScaled(); gui.drawFluid(posX, posY + sizeY - amount, tank.getFluid(),
     * sizeX, amount); RenderHelper.bindTexture(texture);
     * drawTexturedModalRect(posX, posY, 32 + gaugeType * 16, 1, sizeX, sizeY);
     * }
     * 
     * @Override public String getTooltip() { return "" + tank.getFluidAmount()
     * + " / " + tank.getCapacity() + " mB"; }
     * 
     * @Override public boolean handleMouseClicked(int x, int y, int
     * mouseButton) { return false; } int getScaled() { return
     * tank.getFluidAmount() * sizeY / tank.getCapacity(); }
     */
}