package ccm.nucleum_omnium.helper;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenDesert;

public final class FunctionHelper
{

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
    public static boolean isSunVisible(final World world, final int xCoord, final int yCoord, final int zCoord)
    {
        return (world.isDaytime())
               && (!world.provider.hasNoSky)
               && (world.canBlockSeeTheSky(xCoord, yCoord, zCoord))
               && (((world.getWorldChunkManager().getBiomeGenAt(xCoord, yCoord) instanceof BiomeGenDesert)) || ((!world.isRaining()) && (!world.isThundering())));
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
    public static boolean isFireBelow(final World world, final int xCoord, final int yCoord, final int zCoord)
    {
        final Block block = Block.blocksList[world.getBlockId(xCoord, yCoord, zCoord)];
        if (block.blockMaterial == Material.fire){
            return true;
        }else if (block.blockMaterial == Material.lava){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Converts a {@code List<Integer>} to a {@code int[]}
     * 
     * @param list
     *            The {@code List<Integer>} to convert into a {@code int[]}
     * @return The corresponding {@code int[]}
     */
    public static int[] toIntArray(final List<Integer> list)
    {
        final int[] ret = new int[list.size()];
        int i = 0;
        for (final Integer e : list){
            ret[i++] = e.intValue();
        }
        return ret;
    }

    /**
     * Converts a {@code List<Float>} to a {@code float[]}
     * 
     * @param list
     *            The {@code List<Float>} to convert into a {@code float[]}
     * @return The corresponding {@code float[]}
     */
    public static float[] toFloatArray(final List<Float> list)
    {
        final float[] ret = new float[list.size()];
        int i = 0;
        for (final Float e : list){
            ret[i++] = e.intValue();
        }
        return ret;
    }

    private FunctionHelper()
    {}
}