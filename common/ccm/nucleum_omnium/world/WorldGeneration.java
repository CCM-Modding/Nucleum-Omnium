package ccm.nucleum_omnium.world;

import java.util.ArrayList;
import java.util.List;

import lib.cofh.api.world.WeightedRandomBlock;
import lib.cofh.world.feature.FeatureOreGenUniform;
import lib.cofh.world.feature.WorldGenMinableCluster;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import ccm.nucleum_omnium.world.generator.WorldGenHandler;

public class WorldGeneration
{

    public static void addOverworldGen(final String category,
                                       final ItemStack stack,
                                       final String featureName,
                                       final int clusterSize,
                                       final int numClusters,
                                       final int minY,
                                       final int maxY,
                                       final int feature,
                                       final boolean regen,
                                       final boolean enable)
    {
        final List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
        resList.add(new WeightedRandomBlock(stack));
        WorldGenHandler.addOre(new FeatureOreGenUniform(featureName, new WorldGenMinableCluster(resList, clusterSize), numClusters, minY, maxY, regen));
    }

    public static void addEndGen(final String category,
                                 final ItemStack stack,
                                 final String featureName,
                                 final int clusterSize,
                                 final int numClusters,
                                 final int minY,
                                 final int maxY,
                                 final int feature,
                                 final boolean regen,
                                 final boolean enable)
    {
        final List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
        resList.add(new WeightedRandomBlock(stack));
        WorldGenHandler.addOre(new FeatureOreGenUniform(featureName, new WorldGenMinableCluster(resList, clusterSize, Block.whiteStone.blockID), numClusters, minY, maxY, regen));
    }

    public static void addNetherGen(final String category,
                                    final ItemStack stack,
                                    final String featureName,
                                    final int clusterSize,
                                    final int numClusters,
                                    final int minY,
                                    final int maxY,
                                    final int feature,
                                    final boolean regen,
                                    final boolean enable)
    {
        final List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
        resList.add(new WeightedRandomBlock(stack));
        WorldGenHandler.addOre(new FeatureOreGenUniform(featureName, new WorldGenMinableCluster(resList, clusterSize, Block.netherrack.blockID), numClusters, minY, maxY, regen));
    }
}