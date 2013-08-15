/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.sub.tick;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import ccm.nucleum.omnium.block.MainBlock;
import ccm.nucleum.omnium.block.interfaces.IDisplayListener;
import ccm.nucleum.omnium.block.sub.SubCrop;

public class CropGrowth implements IDisplayListener
{

    @Override
    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand)
    {

        final SubCrop block = (SubCrop) ((MainBlock) Block.blocksList[world.getBlockId(x, y, z)]).getSubBlocks()[world.getBlockMetadata(x, y, z)];
        if (world.getBlockLightValue(x, y, z) >= 9)
        {
            if (rand.nextInt((int) (25.0F / block.getGrowthRate()) + 1) == 0)
            {
                block.grow();
            }
        }
    }
}