package lib.cofh.gui.element;

import java.util.List;

import lib.cofh.gui.GuiBase;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * Base class for a modular GUI element. Has self-contained rendering methods and a link back to the {@link GuiBase} it is a part of.
 * 
 * @author King Lemming
 */
public abstract class ElementBase
{
    public static final SoundManager elementSoundManager = FMLClientHandler.instance().getClient().sndManager;
    public static final FontRenderer elementFontRenderer = FMLClientHandler.instance().getClient().fontRenderer;

    protected GuiBase gui;
    protected ResourceLocation texture;

    protected int posX;
    protected int posY;

    protected int sizeX;
    protected int sizeY;

    public int texW = 256;
    public int texH = 256;

    protected String name;

    protected boolean visible = true;

    public ElementBase(GuiBase gui, int posX, int posY)
    {
        this.gui = gui;
        this.posX = gui.getGuiLeft() + posX;
        this.posY = gui.getGuiTop() + posY;
    }

    public ElementBase setTexture(String texture, int texW, int texH)
    {
        this.texture = new ResourceLocation(texture);
        this.texW = texW;
        this.texH = texH;
        return this;
    }

    public ElementBase setPosition(int posX, int posY)
    {
        this.posX = gui.getGuiLeft() + posX;
        this.posY = gui.getGuiTop() + posY;
        return this;
    }

    public ElementBase setSize(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        return this;
    }

    public ElementBase setVisible(boolean visible)
    {
        this.visible = visible;
        return this;
    }

    public ElementBase setName(String name)
    {
        this.name = name;
        return this;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void update()
    {}

    public abstract void draw();

    public void draw(int x, int y)
    {
        posX = x;
        posY = y;
        draw();
    }

    public void addTooltip(List<String> list)
    {}

    public boolean intersectsWith(int mouseX, int mouseY)
    {
        mouseX += gui.getGuiLeft();
        mouseY += gui.getGuiTop();

        if ((mouseX >= posX) && (mouseX <= (posX + sizeX)) && (mouseY >= posY) && (mouseY <= (posY + sizeY)))
        {
            return true;
        }
        return false;
    }

    public boolean handleMouseClicked(int x, int y, int mouseButton)
    {
        return false;
    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
    {
        gui.drawSizedTexturedModalRect(x, y, u, v, width, height, texW, texH);
    }

    public String getName()
    {
        return name;
    }
}