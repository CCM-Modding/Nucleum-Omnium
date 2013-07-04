package ccm.nucleum_omnium.helper;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.sub.SubBlock;

/**
 * BlockHelper
 * 
 * @author Captain_Shadows
 */
public class BlockHelper extends BaseNIC {
	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static MainBlock getBlock(final World world, final int x, final int y, final int z) {
		return ((MainBlock) Block.blocksList[world.getBlockId(x, y, z)]);
	}

	/**
	 * @param blockID
	 * @return
	 */
	public static MainBlock getBlock(final int blockID) {
		return ((MainBlock) Block.blocksList[blockID]);
	}

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static SubBlock getSubBlock(final World world, final int x, final int y, final int z) {
		return ((MainBlock) Block.blocksList[world.getBlockId(x, y, z)]).getSubBlocks()[world.getBlockMetadata(	x,
																												y,
																												z)];
	}

	/**
	 * @param blockID
	 * @param item
	 * @return
	 */
	public static SubBlock getSubBlock(final int blockID, final ItemStack item) {
		return ((MainBlock) Block.blocksList[blockID]).getSubBlocks()[item.getItemDamage()];
	}
}