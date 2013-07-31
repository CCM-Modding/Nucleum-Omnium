/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.interfaces;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * ITileHelper
 * <p>
 * Does all the Tile Entity related things for the SubBlock
 * 
 * @author Captain_Shadows
 */
public interface ITileHelper
{

    /**
     * @return null if there is no tile entity, a new instance if there is one
     */
    public TileEntity createTileEntity(final World world, final int meta);

    /**
     * @return true if the {@link ITileHelper} has a TileEntity
     */
    public boolean hasTileEntity(final int meta);

    /**
     * Sets the tile entity to use (Inside of the {@link ITileHelper})
     */
    public void setTileEntity(final TileEntity tile);
}
