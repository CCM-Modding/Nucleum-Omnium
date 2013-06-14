package ccm.nucleum.world;

import java.util.ArrayList;
import java.util.List;

import lib.cofh.api.world.WeightedRandomBlock;
import lib.cofh.util.StringHelper;
import lib.cofh.world.feature.FeatureOreGenUniform;
import lib.cofh.world.feature.WorldGenMinableCluster;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ConfigCategory;
import ccm.nucleum.world.generator.WorldGenHandler;

public class WorldGenerator {
    
    public static void addOverworldGen(final String modName, final ItemStack stack,
            final String oreName, final int clusterSize, final int numClusters, final int minY,
            final int maxY, final boolean enable) {
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
    
    public static void addEndGen(final String modName, final ItemStack stack, final String oreName,
            final int clusterSize, final int numClusters, final int minY, final int maxY,
            final boolean enable) {
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
    
    public static void addNetherGen(final String modName, final ItemStack stack,
            final String oreName, final int clusterSize, final int numClusters, final int minY,
            final int maxY, final boolean enable) {
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
    
    private static void addWorldGen(String modName, final ItemStack stack, final String oreName,
            int clusterSize, int numClusters, int minY, int maxY, final boolean enable,
            final Block blockToReplace) {
        modName = modName + "." + StringHelper.titleCase(oreName);
        final ConfigCategory cat = NucleumWorld.config.getCategory(modName);
        
        final String strMin = "MinHeight";
        final String strMax = "MaxHeight";
        
        clusterSize = NucleumWorld.config.get(modName, "ClusterSize", clusterSize).getInt();
        numClusters = NucleumWorld.config.get(modName, "NumClusters", numClusters).getInt();
        minY = NucleumWorld.config.get(modName, strMin, minY).getInt();
        maxY = NucleumWorld.config.get(modName, strMax, maxY).getInt();
        final boolean regen = NucleumWorld.config.get(modName, "RetroGen", enable)
                .getBoolean(enable);
        
        cat.setComment("Configurations for " + StringHelper.titleCase(oreName));
        
        NucleumWorld.config.save();
        
        if (!enable) {
            return;
        }
        
        final List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
        resList.add(new WeightedRandomBlock(stack));
        WorldGenHandler.addOre(new FeatureOreGenUniform(oreName, new WorldGenMinableCluster(
                resList, clusterSize, blockToReplace.blockID), numClusters, minY, maxY, regen));
    }
}