package ccm.nucleum.omnium.inventory.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import ccm.nucleum.omnium.utils.handler.ResourceHandler;

import lib.cofh.gui.GuiBase;

public abstract class BaseGui extends GuiBase
{
    public BaseGui(Container container)
    {
        super(container);
    }
    
    public BaseGui(Container container, String name)
    {
        super(container, ResourceHandler.getGUI(name));
    }

    public abstract Object getClientGuiElement(EntityPlayer player, World world, int x, int y, int z);
}