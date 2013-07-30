package ccm.nucleum_omnium.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.block.interfaces.ITileHelper;
import ccm.nucleum_omnium.handler.LogHandler;
import ccm.nucleum_omnium.tileentity.InventoryTE;

public class TileInventory extends TileBase implements ITileHelper {

    protected boolean hasInv = false;

    protected int     size;

    public boolean hasInventory() {
        return hasInv;
    }

    @Override
    public void setTileEntity(final TileEntity tile) {
        super.setTileEntity(tile);

        if (tile instanceof InventoryTE) {
            hasInv = true;
            size = ((InventoryTE) tile).getSizeInventory();
        }
    }

    @Override
    public TileEntity createTileEntity(final World world, final int meta) {
        try {
            if (hasInventory()) {
                return ((InventoryTE) te.newInstance()).setInventorySize(size);
            } else {
                return super.createTileEntity(world, meta);
            }
        } catch (final Exception e) {
            LogHandler.severe(NucleumOmnium.instance,
                              "TileEntity Instance could not be created during createTileEntity \n");
            e.getCause();
            e.printStackTrace();
            return null;
        }
    }
}
