/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

/**
 * GuiHandling
 * <p>
 * 
 * @author Captain_Shadows
 */
public abstract class GuiHandling
{
    protected final Class<? extends Container> container;
    protected Class<? extends GuiContainer>    gui;

    public GuiHandling(final Class<? extends Container> container)
    {
        this.container = container;
    }

    public GuiHandling(final Class<? extends GuiContainer> gui, final Class<? extends Container> container)
    {
        this(container);
        this.gui = gui;
    }

    public abstract Object getServerGuiElement(final EntityPlayer player,
                                               final World world,
                                               final int x,
                                               final int y,
                                               final int z);

    public abstract Object getClientGuiElement(final EntityPlayer player,
                                               final World world,
                                               final int x,
                                               final int y,
                                               final int z);
}