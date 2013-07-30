package ccm.nucleum_omnium.block.interfaces;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITileHelper {

    public void setTileEntity(final TileEntity tile);

    public boolean hasTileEntity(final int meta);

    public TileEntity createTileEntity(final World world, final int meta);
}
