/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum_omnium.block.interfaces.ITileHelper;

/**
 * NoTile
 * <p>
 * Implementation for Blocks without Tile Entitys
 * 
 * @author Captain_Shadows
 */
public class NoTile implements ITileHelper
{

    @Override
    public TileEntity createTileEntity(final World world, final int meta)
    {
        return null;
    }

    @Override
    public boolean hasTileEntity(final int meta)
    {
        return false;
    }

    @Override
    public void setTileEntity(final TileEntity tile)
    {}
}