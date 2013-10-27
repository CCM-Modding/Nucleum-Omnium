package ccm.nucleum.omnium.inventory.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public abstract class BaseGui extends GuiContainer
{
    public BaseGui(Container container)
    {
        super(container);
    }

    public abstract Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);
}