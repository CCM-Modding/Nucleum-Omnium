/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ConfigCategory;

import ccm.nucleum_omnium.configuration.AdvConfiguration;
import ccm.nucleum_world.generator.WorldGenHandler;

import lib.cofh.api.world.WeightedRandomBlock;
import lib.cofh.util.StringHelper;
import lib.cofh.world.feature.FeatureOreGenUniform;
import lib.cofh.world.feature.WorldGenMinableCluster;

/**
 * @author Captain_Shadows
 */
public final class WorldGenerator
{

    private static void addWorldGen(final String modName,
                                    final ItemStack stack,
                                    final String oreName,
                                    int clusterSize,
                                    int numClusters,
                                    int minY,
                                    int maxY,
                                    final boolean enable,
                                    final Block blockToReplace)
    {
        final String ore = modName + "." + StringHelper.titleCase(oreName);
        final AdvConfiguration config = NucleumWorld.instance.getConfigFile();
        final ConfigCategory cat = config.getCategory(ore);

        clusterSize = config.get(ore, "ClusterSize", clusterSize).getInt();
        numClusters = config.get(ore, "NumClusters", numClusters).getInt();
        minY = config.get(ore, "MinHeight", minY).getInt();
        maxY = config.get(ore, "MaxHeight", maxY).getInt();
        final boolean regen = config.get(ore, "RetroGen", enable).getBoolean(enable);

        cat.setComment("Configurations for " + StringHelper.titleCase(oreName));

        config.save();

        if (!enable)
        {
            return;
        }

        final List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
        resList.add(new WeightedRandomBlock(stack));
        WorldGenHandler.addOre(new FeatureOreGenUniform(oreName,
                                                        new WorldGenMinableCluster(resList,
                                                                                   clusterSize,
                                                                                   blockToReplace.blockID),
                                                        numClusters,
                                                        minY,
                                                        maxY,
                                                        regen));
    }

    /**
     * Adds world generation to the End
     * 
     * @param modName
     *            The name of the mod adding the world gen
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
    public static void addEndGen(final String modName,
                                 final ItemStack stack,
                                 final String oreName,
                                 final int clusterSize,
                                 final int numClusters,
                                 final int minY,
                                 final int maxY,
                                 final boolean enable)
    {
        WorldGenerator.addWorldGen(modName,
                                   stack,
                                   oreName,
                                   clusterSize,
                                   numClusters,
                                   minY,
                                   maxY,
                                   enable,
                                   Block.whiteStone);
    }

    /**
     * Adds world generation to the Nether
     * 
     * @param modName
     *            The name of the mod adding the world gen
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
    public static void addNetherGen(final String modName,
                                    final ItemStack stack,
                                    final String oreName,
                                    final int clusterSize,
                                    final int numClusters,
                                    final int minY,
                                    final int maxY,
                                    final boolean enable)
    {
        WorldGenerator.addWorldGen(modName,
                                   stack,
                                   oreName,
                                   clusterSize,
                                   numClusters,
                                   minY,
                                   maxY,
                                   enable,
                                   Block.netherrack);
    }

    /**
     * Adds world generation to the Overworld
     * 
     * @param modName
     *            The name of the mod adding the world gen
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
    public static void addOverworldGen(final String modName,
                                       final ItemStack stack,
                                       final String oreName,
                                       final int clusterSize,
                                       final int numClusters,
                                       final int minY,
                                       final int maxY,
                                       final boolean enable)
    {
        WorldGenerator.addWorldGen(modName,
                                   stack,
                                   oreName,
                                   clusterSize,
                                   numClusters,
                                   minY,
                                   maxY,
                                   enable,
                                   Block.stone);
    }
}