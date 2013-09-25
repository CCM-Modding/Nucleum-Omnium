/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ConfigCategory;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.configuration.ConfigurationWrapper;
import ccm.nucleum.omnium.utils.helper.JavaHelper;
import ccm.nucleum.world.generator.WorldGenHandler;

import lib.cofh.world.WeightedRandomBlock;
import lib.cofh.world.feature.FeatureOreGenUniform;
import lib.cofh.world.feature.WorldGenMinableCluster;

/**
 * @author Captain_Shadows
 */
public final class WorldGenerator
{
    /**
     * Adds world generation to the End
     * 
     * @param stack
     *            The ItemStack of the Ore
     * @param oreName
     *            The name of the ore
     * @param clusterSize
     *            The size of the clusters
     * @param numClusters
     *            The number of clusters per chunk
     * @param minY
     *            The minimum Y level to find the ore
     * @param maxY
     *            The maximum Y level to find the ore
     * @param enable
     *            is the ore enabled?
     */
    public static void addEndGen(final IMod mod, final ItemStack stack, final String oreName, final int clusterSize, final int numClusters, final int minY, final int maxY,
            final boolean enable)
    {
        WorldGenerator.addWorldGen(mod, stack, oreName, clusterSize, numClusters, minY, maxY, enable, Block.whiteStone);
    }

    /**
     * Adds world generation to the Nether
     * 
     * @param stack
     *            The ItemStack of the Ore
     * @param oreName
     *            The name of the ore
     * @param clusterSize
     *            The size of the clusters
     * @param numClusters
     *            The number of clusters per chunk
     * @param minY
     *            The minimum Y level to find the ore
     * @param maxY
     *            The maximum Y level to find the ore
     * @param enable
     *            is the ore enabled?
     */
    public static void addNetherGen(final IMod mod, final ItemStack stack, final String oreName, final int clusterSize, final int numClusters, final int minY, final int maxY,
            final boolean enable)
    {
        WorldGenerator.addWorldGen(mod, stack, oreName, clusterSize, numClusters, minY, maxY, enable, Block.netherrack);
    }

    /**
     * Adds world generation to the Overworld
     * 
     * @param stack
     *            The ItemStack of the Ore
     * @param oreName
     *            The name of the ore
     * @param clusterSize
     *            The size of the clusters
     * @param numClusters
     *            The number of clusters per chunk
     * @param minY
     *            The minimum Y level to find the ore
     * @param maxY
     *            The maximum Y level to find the ore
     * @param enable
     *            is the ore enabled?
     */
    public static void addOverworldGen(final IMod mod, final ItemStack stack, final String oreName, final int clusterSize, final int numClusters, final int minY, final int maxY,
            final boolean enable)
    {
        WorldGenerator.addWorldGen(mod, stack, oreName, clusterSize, numClusters, minY, maxY, enable, Block.stone);
    }

    public static void addWorldGen(final IMod mod, final ItemStack stack, final String oreName, int clusterSize, int numClusters, int minY, int maxY, final boolean enable,
            final Block blockToReplace)
    {
        final String ore = mod.getName() + "." + JavaHelper.titleCase(oreName);
        final ConfigurationWrapper config = NucleumWorld.instance.getConfiguration();
        final ConfigCategory cat = config.getCategory(ore);

        clusterSize = config.get(ore, "ClusterSize", clusterSize).getInt();
        numClusters = config.get(ore, "NumClusters", numClusters).getInt();
        minY = config.get(ore, "MinHeight", minY).getInt();
        maxY = config.get(ore, "MaxHeight", maxY).getInt();
        final boolean regen = config.get(ore, "RetroGen", enable).getBoolean(enable);

        cat.setComment("Configurations for " + JavaHelper.titleCase(oreName));

        config.save();

        if (!enable)
        {
            return;
        }

        final List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
        resList.add(new WeightedRandomBlock(stack));
        WorldGenHandler.addOre(new FeatureOreGenUniform(oreName, new WorldGenMinableCluster(resList, clusterSize, blockToReplace.blockID), numClusters, minY, maxY, regen));
    }
}