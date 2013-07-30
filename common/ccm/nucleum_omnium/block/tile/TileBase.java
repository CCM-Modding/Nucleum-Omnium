package ccm.nucleum_omnium.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.block.interfaces.ITileHelper;
import ccm.nucleum_omnium.handler.LogHandler;

public class TileBase implements ITileHelper {

    protected Class<? extends TileEntity> te    = null;

    protected boolean                     hasTE = false;

    @Override
    public boolean hasTileEntity(final int meta) {
        return hasTE;
    }

    @Override
    public void setTileEntity(final TileEntity tile) {
        if (tile != null) {
            te = tile.getClass();
            hasTE = true;
        } else {
            LogHandler.warning(NucleumOmnium.instance,
                               "TileEntity was NULL!! during setTileEntity @ SBTile \n");
        }
    }

    @Override
    public TileEntity createTileEntity(final World world, final int meta) {
        try {
            return te.newInstance();
        } catch (final Exception e) {
            LogHandler.severe(NucleumOmnium.instance,
                              "TileEntity Instance could not be created during createTileEntity \n");
            e.getCause();
            e.printStackTrace();
            return null;
        }
    }
}