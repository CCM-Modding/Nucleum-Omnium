/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.loader.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum.omnium.block.interfaces.ITileHelper;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

public class TileBase implements ITileHelper
{

    protected Class<? extends TileEntity> te = null;

    protected boolean hasTE = false;

    @Override
    public TileEntity createTileEntity(final World world, final int meta)
    {
        try
        {
            return te.newInstance();
        } catch (final Exception e)
        {
            CCMLogger.DEFAULT_LOGGER.printCatch(e, "TileEntity INSTANCE COULD NOT BE CREATED DURING createTileEntity\n");
            return null;
        }
    }

    @Override
    public boolean hasTileEntity(final int meta)
    {
        return hasTE;
    }

    @Override
    public void setTileEntity(final TileEntity tile)
    {
        if (tile != null)
        {
            te = tile.getClass();
            hasTE = true;
        } else
        {
            CCMLogger.DEFAULT_LOGGER.debug("TileEntity WAS NULL DURING setTileEntity @ SBTile\n");
        }
    }
}