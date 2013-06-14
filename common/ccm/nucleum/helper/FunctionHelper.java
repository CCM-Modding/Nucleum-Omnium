package ccm.nucleum.helper;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenDesert;
import ccm.nucleum.BaseNIClass;
import ccm.nucleum.NucleumOmnium;

public final class FunctionHelper extends BaseNIClass {
    
    /**
     * Checks if the sun is visible
     * 
     * @param world
     *            The world to check in
     * @param xCoord
     *            The X coordinate to check in
     * @param yCoord
     *            The Y coordinate to check in + 1 so that the block doesn't
     *            interrupt the operation
     * @param zCoord
     *            The Z coordinate to check in
     * @return true if the sun is visible. Otherwise false
     */
    public static boolean isSunVisible(final World world, final int xCoord, final int yCoord,
            final int zCoord) {
        return world.isDaytime()
                && !world.provider.hasNoSky
                && world.canBlockSeeTheSky(xCoord, yCoord, zCoord)
                && ((world.getWorldChunkManager().getBiomeGenAt(xCoord, yCoord) instanceof BiomeGenDesert) || (!world
                        .isRaining() && !world.isThundering()));
    }
    
    /**
     * Checks if there is Lava or Fire below the block
     * 
     * @param world
     *            The world to check in
     * @param xCoord
     *            The X coordinate to check in
     * @param yCoord
     *            The Y coordinate to check in - 1 so that the block doesn't
     *            interrupt the operation
     * @param zCoord
     *            The Z coordinate to check in
     * @return true if there is Fire or Lava below. Otherwise false
     */
    public static boolean isFireBelow(final World world, final int xCoord, final int yCoord,
            final int zCoord) {
        final Block block = Block.blocksList[world.getBlockId(xCoord, yCoord, zCoord)];
        if (block.blockMaterial == Material.fire) {
            return true;
        } else if (block.blockMaterial == Material.lava) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gets all the Dimensions listed by their Dimension ID
     * 
     * @return A {@code HashMap<Integer, World>} containig all the Dimensions
     */
    public static HashMap<Integer, World> getDimensions() {
        final HashMap<Integer, World> dimensions = new HashMap<Integer, World>();
        for (final World world : NucleumOmnium.server.worldServers) {
            dimensions.put(Integer.valueOf(world.provider.dimensionId), world);
        }
        return dimensions;
    }
}