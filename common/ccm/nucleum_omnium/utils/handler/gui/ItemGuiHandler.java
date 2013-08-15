/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

/**
 * ItemHandler
 * <p>
 * 
 * @author Captain_Shadows
 */
public class ItemGuiHandler extends AbstractGuiHandler
{
    public ItemGuiHandler(final Class<? extends Container> container)
    {
        super(container);
    }

    public ItemGuiHandler(final Class<? extends GuiContainer> gui, final Class<? extends Container> container)
    {
        super(gui, container);
    }

    @Override
    public Object getServerGuiElement(final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        Container tmp = null;
        try
        {
            tmp = container.getConstructor(EntityPlayer.class).newInstance(player);
        } catch (final Exception e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return tmp;
    }

    @Override
    public Object getClientGuiElement(final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        GuiContainer tmp = null;
        try
        {
            tmp = gui.getConstructor(EntityPlayer.class).newInstance(player);
        } catch (final Exception e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return tmp;
    }
}