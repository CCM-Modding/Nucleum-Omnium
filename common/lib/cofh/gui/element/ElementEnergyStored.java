package lib.cofh.gui.element;

import java.util.List;

import lib.cofh.api.energy.IEnergyStorage;
import lib.cofh.gui.GuiBase;
import lib.cofh.gui.GuiProps;
import lib.cofh.render.RenderHelper;
import lib.cofh.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ElementEnergyStored extends ElementBase
{
    public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Energy.png");
    public static final int DEFAULT_SCALE = 42;

    protected IEnergyStorage storage;

    public ElementEnergyStored(GuiBase gui, int posX, int posY, IEnergyStorage storage)
    {
        super(gui, posX, posY);
        this.storage = storage;

        texture = DEFAULT_TEXTURE;
        sizeX = 16;
        sizeY = DEFAULT_SCALE;

        texW = 32;
        texH = 64;
    }

    @Override
    public void draw()
    {
        if (!visible)
        {
            return;
        }
        RenderHelper.bindTexture(texture);
        drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
        int qty = getScaled();
        drawTexturedModalRect(posX, (posY + DEFAULT_SCALE) - qty, 16, DEFAULT_SCALE - qty, sizeX, qty);
    }

    @Override
    public void addTooltip(List<String> list)
    {
        if (storage.getMaxEnergyStored() < 0)
        {
            list.add("Infinite RF");
        } else
        {
            list.add("" + storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF");
        }
    }

    int getScaled()
    {
        if (storage.getMaxEnergyStored() < 0)
        {
            return sizeY;
        }
        return MathHelper.round((storage.getEnergyStored() * sizeY) / storage.getMaxEnergyStored());
    }
}