/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.sub.tick;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ccm.nucleum.omnium.block.interfaces.IDisplayListener;
import ccm.nucleum.omnium.tileentity.PlantTE;
import ccm.nucleum.omnium.utils.helper.BlockHelper;

public class CropGrowth implements IDisplayListener
{

    @Override
    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand)
    {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (world.getBlockLightValue(x, y, z) >= 9)
        {
            if (tile instanceof PlantTE)
            {
                PlantTE plant = (PlantTE) tile;

                if (rand.nextInt((int) (25.0F / plant.getGrowthRate()) + 1) == 0)
                {
                    plant.grow();
                    BlockHelper.updateAdjacent(world, x, y, z, 3);
                }
            }
        }
    }
}