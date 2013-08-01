package lib.cofh.util.fluid;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class DispenserFilledBucketHandler extends BehaviorDefaultDispenseItem
{

    private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    @Override
    public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack stackBucket)
    {

        final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
        final World world = blockSource.getWorld();

        final int x = blockSource.getXInt() + facing.getFrontOffsetX();
        final int y = blockSource.getYInt() + facing.getFrontOffsetY();
        final int z = blockSource.getZInt() + facing.getFrontOffsetZ();

        if (!world.isAirBlock(x, y, z) && world.getBlockMaterial(x, y, z).isSolid())
        {
            return stackBucket;
        }
        if (BucketHandler.emptyBucket(blockSource.getWorld(), x, y, z, stackBucket))
        {
            return new ItemStack(Item.bucketEmpty);
        }
        return defaultDispenserItemBehavior.dispense(blockSource, stackBucket);
    }

}
