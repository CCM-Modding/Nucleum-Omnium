/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.block.interfaces.ITileHelper;
import ccm.nucleum.omnium.tileentity.LogicTE;
import ccm.nucleum.omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum.omnium.utils.handler.LogHandler;

public class TileLogic extends TileInventory implements ITileHelper
{

    protected Class<? extends ITileLogic> logic = null;

    @Override
    public TileEntity createTileEntity(final World world, final int meta)
    {
        if (logic != null)
        {
            try
            {
                if (hasInventory())
                {
                    return ((LogicTE) te.newInstance()).setLogic(logic).setInventorySize(size);
                } else
                {
                    return ((LogicTE) te.newInstance()).setLogic(logic);
                }
            } catch (final Exception e)
            {
                LogHandler.severe(NucleumOmnium.instance, "TileEntity Instance with logic could not be created during createTileEntity \n");
                e.getCause();
                e.printStackTrace();
                return null;
            }
        } else
        {
            return super.createTileEntity(world, meta);
        }
    }

    @Override
    public void setTileEntity(final TileEntity tile)
    {

        super.setTileEntity(tile);

        if (tile instanceof LogicTE)
        {
            if (((LogicTE) tile).hasLogic())
            {
                logic = ((LogicTE) tile).getSrcLogic();
            }
        }
    }
}
