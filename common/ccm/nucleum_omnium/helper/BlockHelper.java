package ccm.nucleum_omnium.helper;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ccm.nucleum_omnium.BaseNIClass;
import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.sub.SubBlock;

/**
 * BlockHelper
 * 
 * @author Captain_Shadows
 */
public class BlockHelper extends BaseNIClass {
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static MainBlock getBlock(World world, int x, int y, int z) {
        return ((MainBlock) Block.blocksList[world.getBlockId(x, y, z)]);
    }
    
    /**
     * @param blockID
     * @return
     */
    public static MainBlock getBlock(int blockID) {
        return ((MainBlock) Block.blocksList[blockID]);
    }
    
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static SubBlock getSubBlock(World world, int x, int y, int z) {
        return ((MainBlock) Block.blocksList[world.getBlockId(x, y, z)]).getSubBlocks()[world.getBlockMetadata(x, y, z)];
    }
    
    /**
     * @param blockID
     * @param item
     * @return
     */
    public static SubBlock getSubBlock(int blockID, ItemStack item) {
        return ((MainBlock) Block.blocksList[blockID]).getSubBlocks()[item.getItemDamage()];
    }
}