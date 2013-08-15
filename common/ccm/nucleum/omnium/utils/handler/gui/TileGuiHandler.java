/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * TileHandler
 * <p>
 * 
 * @author Captain_Shadows
 */
public class TileGuiHandler extends AbstractGuiHandler
{
    public TileGuiHandler(final Class<? extends Container> container)
    {
        super(container);
    }

    public TileGuiHandler(final Class<? extends GuiContainer> gui, final Class<? extends Container> container)
    {
        super(gui, container);
    }

    @Override
    public Object getServerGuiElement(final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        Container tmp = null;
        try
        {
            tmp = container.getConstructor(InventoryPlayer.class, TileEntity.class).newInstance(player.inventory, te);
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
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        GuiContainer tmp = null;
        try
        {
            tmp = gui.getConstructor(InventoryPlayer.class, TileEntity.class).newInstance(player.inventory, te);
        } catch (final Exception e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return tmp;
    }
}