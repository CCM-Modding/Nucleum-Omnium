package ccm.nucleum_omnium.world.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import ccm.nucleum_omnium.world.utils.RandomWeightedBlock;

public class WorldGenMinable extends WorldGenerator
{

    private final List<RandomWeightedBlock> cluster;

    private final int                       genClusterSize;

    private int                             genBlockID = Block.stone.blockID;

    public WorldGenMinable(final ItemStack ore,
                                  final int clusterSize)
    {
        this.cluster = new ArrayList<RandomWeightedBlock>();
        this.cluster.add(new RandomWeightedBlock(ore));
        this.genClusterSize = clusterSize;
    }

    public WorldGenMinable(final RandomWeightedBlock resource,
                                  final int clusterSize)
    {
        this.cluster = new ArrayList<RandomWeightedBlock>();
        this.cluster.add(resource);
        this.genClusterSize = clusterSize;
    }

    public WorldGenMinable(final List<RandomWeightedBlock> resource,
                                  final int clusterSize)
    {
        this.cluster = resource;
        this.genClusterSize = clusterSize;
    }

    public WorldGenMinable(final ItemStack ore,
                                  final int clusterSize,
                                  final int blockID)
    {
        this.cluster = new ArrayList<RandomWeightedBlock>();
        this.cluster.add(new RandomWeightedBlock(ore, 1));
        this.genClusterSize = clusterSize;
        this.genBlockID = blockID;
    }

    public WorldGenMinable(final RandomWeightedBlock resource,
                                  final int clusterSize,
                                  final int blockID)
    {
        this.cluster = new ArrayList<RandomWeightedBlock>();
        this.cluster.add(resource);
        this.genClusterSize = clusterSize;
        this.genBlockID = blockID;
    }

    public WorldGenMinable(final List<RandomWeightedBlock> resource,
                                  final int clusterSize,
                                  final int blockID)
    {
        this.cluster = resource;
        this.genClusterSize = clusterSize;
        this.genBlockID = blockID;
    }

    @Override
    public boolean generate(final World world, final Random rand, final int x, final int y, final int z)
    {
        final float f = rand.nextFloat() * 3.141593F;
        final double d0 = x + 8 + ((MathHelper.sin(f) * this.genClusterSize) / 8.0F);
        final double d1 = (x + 8) - ((MathHelper.sin(f) * this.genClusterSize) / 8.0F);
        final double d2 = z + 8 + ((MathHelper.cos(f) * this.genClusterSize) / 8.0F);
        final double d3 = (z + 8) - ((MathHelper.cos(f) * this.genClusterSize) / 8.0F);
        final double d4 = (y + rand.nextInt(3)) - 2;
        final double d5 = (y + rand.nextInt(3)) - 2;

        for (int l = 0; l <= this.genClusterSize; l++){
            final double d6 = d0 + (((d1 - d0) * l) / this.genClusterSize);
            final double d7 = d4 + (((d5 - d4) * l) / this.genClusterSize);
            final double d8 = d2 + (((d3 - d2) * l) / this.genClusterSize);
            final double d9 = (rand.nextDouble() * this.genClusterSize) / 16.0D;
            final double d10 = ((MathHelper.sin((l * 3.141593F) / this.genClusterSize) + 1.0F) * d9) + 1.0D;
            final double d11 = ((MathHelper.sin((l * 3.141593F) / this.genClusterSize) + 1.0F) * d9) + 1.0D;
            final int i1 = MathHelper.floor_double(d6 - (d10 / 2.0D));
            final int j1 = MathHelper.floor_double(d7 - (d11 / 2.0D));
            final int k1 = MathHelper.floor_double(d8 - (d10 / 2.0D));
            final int l1 = MathHelper.floor_double(d6 + (d10 / 2.0D));
            final int i2 = MathHelper.floor_double(d7 + (d11 / 2.0D));
            final int j2 = MathHelper.floor_double(d8 + (d10 / 2.0D));

            for (int k2 = i1; k2 <= l1; k2++){
                final double d12 = ((k2 + 0.5D) - d6) / (d10 / 2.0D);

                if ((d12 * d12) < 1.0D){
                    for (int l2 = j1; l2 <= i2; l2++){
                        final double d13 = ((l2 + 0.5D) - d7) / (d11 / 2.0D);

                        if (((d12 * d12) + (d13 * d13)) < 1.0D){
                            for (int i3 = k1; i3 <= j2; i3++){
                                final double d14 = ((i3 + 0.5D) - d8) / (d10 / 2.0D);

                                final Block block = Block.blocksList[world.getBlockId(k2, l2, i3)];
                                if ((((d12 * d12) + (d13 * d13) + (d14 * d14)) < 1.0D) && (block != null) && (block.isGenMineableReplaceable(world, k2, l2, i3, this.genBlockID))){
                                    final RandomWeightedBlock ore = (RandomWeightedBlock) WeightedRandom.getRandomItem(world.rand, this.cluster);
                                    world.setBlock(k2, l2, i3, ore.blockId, ore.metadata, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}