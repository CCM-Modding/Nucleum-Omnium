package ccm.nucleum_omnium.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lib.cofh.api.world.WeightedRandomBlock;
import lib.cofh.util.StringHelper;
import lib.cofh.world.feature.FeatureOreGenUniform;
import lib.cofh.world.feature.WorldGenMinableCluster;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;

import ccm.nucleum_omnium.world.generator.WorldGenHandler;

public class WorldGenerator
{

    public static void addOverworldGen(final String modName,
                                       final ItemStack stack,
                                       final String oreName,
                                       final int clusterSize,
                                       final int numClusters,
                                       final int minY,
                                       final int maxY,
                                       final boolean enable)
    {
        addWorldGen(modName, stack, oreName, clusterSize, numClusters, minY, maxY, enable, Block.stone.blockID);
    }

    public static void addEndGen(final String modName,
                                 final ItemStack stack,
                                 final String oreName,
                                 final int clusterSize,
                                 final int numClusters,
                                 final int minY,
                                 final int maxY,
                                 final boolean enable)
    {
        addWorldGen(modName, stack, oreName, clusterSize, numClusters, minY, maxY, enable, Block.whiteStone.blockID);
    }

    public static void addNetherGen(final String modName,
                                    final ItemStack stack,
                                    final String oreName,
                                    final int clusterSize,
                                    final int numClusters,
                                    final int minY,
                                    final int maxY,
                                    final boolean enable)
    {
        addWorldGen(modName, stack, oreName, clusterSize, numClusters, minY, maxY, enable, Block.netherrack.blockID);
    }

    private static void addWorldGen(String modName, ItemStack stack, String oreName, int clusterSize, int numClusters, int minY, int maxY, boolean enable, int blockToReplace)
    {
        Configuration config = NucleumWorld.config.getConfig();
        modName = modName + "." + oreName.toLowerCase(Locale.ENGLISH);
        final ConfigCategory cat = config.getCategory(modName);

        String strMin = "MinY";
        String strMax = "MaxY";

        clusterSize = config.get(modName, "ClusterSize", clusterSize).getInt();
        numClusters = config.get(modName, "NumClusters", numClusters).getInt();
        minY = config.get(modName, strMin, minY).getInt();
        maxY = config.get(modName, strMax, maxY).getInt();
        boolean regen = config.get(modName, "RetroGen", enable).getBoolean(enable);

        cat.setComment("Generating settings for " + StringHelper.titleCase(oreName));

        config.save();

        if (!enable){
            return;
        }

        final List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
        resList.add(new WeightedRandomBlock(stack));
        WorldGenHandler.addOre(new FeatureOreGenUniform(oreName, new WorldGenMinableCluster(resList, clusterSize, blockToReplace), numClusters, minY, maxY, regen));
    }
}