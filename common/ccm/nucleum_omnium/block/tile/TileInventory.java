/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.block.interfaces.ITileHelper;
import ccm.nucleum_omnium.tileentity.InventoryTE;
import ccm.nucleum_omnium.utils.handler.LogHandler;

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
            LogHandler.severe(NucleumOmnium.instance, "TileEntity Instance could not be created during createTileEntity \n");
            e.getCause();
            e.printStackTrace();
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
