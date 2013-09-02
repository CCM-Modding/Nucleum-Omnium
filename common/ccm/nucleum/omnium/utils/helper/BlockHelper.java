/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.block.MainBlock;
import ccm.nucleum.omnium.block.sub.SubBlock;

/**
 * BlockHelper
 * 
 * @author Captain_Shadows
 */
public class BlockHelper extends BaseNIC
{
    /**
     * @return a MainBlock with the id, if it is not and instance of MainBlock the the result is null
     */
    public static MainBlock getBlock(final int blockID)
    {
        final Block block = Block.blocksList[blockID];
        if (block instanceof MainBlock)
        {
            return ((MainBlock) block);
        }
        return null;
    }

    /**
     * @return a MainBlock in the world, x, y, z; if it is not and instance of MainBlock the the result is null
     */
    public static MainBlock getBlock(final World world, final int x, final int y, final int z)
    {
        return getBlock(world.getBlockId(x, y, z));
    }

    /**
     * @return
     */
    public static SubBlock getSubBlock(final int blockID, final ItemStack item)
    {
        return getBlock(blockID).getSubBlocks()[item.getItemDamage()];
    }

    /**
     * @return
     */
    public static SubBlock getSubBlock(final World world, final int x, final int y, final int z)
    {
        return getBlock(world, x, y, z).getSubBlocks()[world.getBlockMetadata(x, y, z)];
    }

    /**
     * Notifies blocks that this block changed, depending on the flag
     * 
     * @param flag
     *            1 will cause a block update. 2 will send the change to clients (you almost always want this). 4 prevents the block from being re-rendered, if this is a client
     *            world. Flags can be added together
     * @return true if it succeeded false otherwise
     */
    public static boolean updateAdjacent(final World world, final int x, final int y, final int z, final int flag)
    {
        if ((x >= -30000000) && (z >= -30000000) && (x < 30000000) && (z < 30000000))
        {
            if (y < 0)
            {
                return false;
            } else if (y >= 256)
            {
                return false;
            } else
            {
                final Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                final int x1 = x & 15;
                final int z1 = z & 15;

                final int blockID = chunk.getBlockID(x1, y, z1);

                if (((flag & 2) != 0) && (!world.isRemote || ((flag & 4) == 0)))
                {
                    world.markBlockForUpdate(x, y, z);
                }

                if (!world.isRemote && ((flag & 1) != 0))
                {
                    world.notifyBlockChange(x, y, z, blockID);
                    final Block block = Block.blocksList[blockID];

                    if ((block != null) && block.hasComparatorInputOverride())
                    {
                        world.func_96440_m(x, y, z, blockID);
                    }
                }

                return true;
            }
        } else
        {
            return false;
        }
    }
}