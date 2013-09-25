/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.loader.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum.omnium.block.interfaces.ITileHelper;
import ccm.nucleum.omnium.tileentity.InventoryTE;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

public class TileInventory extends TileBase implements ITileHelper
{

    protected boolean hasInv = false;

    protected int size;

    @Override
    public TileEntity createTileEntity(final World world, final int meta)
    {
        try
        {
            if (hasInventory())
            {
                return ((InventoryTE) te.newInstance()).setInventorySize(size);
            } else
            {
                return super.createTileEntity(world, meta);
            }
        } catch (final Exception e)
        {
            CCMLogger.DEFAULT_LOGGER.printCatch(e, "TileEntity INSTANCE COULD NOT BE CREATED DURING createTileEntity\n");
            return null;
        }
    }

    public boolean hasInventory()
    {
        return hasInv;
    }

    @Override
    public void setTileEntity(final TileEntity tile)
    {
        super.setTileEntity(tile);

        if (tile instanceof InventoryTE)
        {
            hasInv = true;
            size = ((InventoryTE) tile).getSizeInventory();
        }
    }
}
