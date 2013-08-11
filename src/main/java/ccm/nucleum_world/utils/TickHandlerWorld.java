/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_world.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.world.World;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import ccm.nucleum_world.generator.WorldGenHandler;

import lib.cofh.world.util.ChunkCoord;

public class TickHandlerWorld implements ITickHandler
{

    public static TickHandlerWorld               instance    = new TickHandlerWorld();

    public static HashMap<Integer, ArrayList<?>> chunksToGen = new HashMap<Integer, ArrayList<?>>();

    @Override
    public String getLabel()
    {
        return "nucleum_world.tick";
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void tickEnd(final EnumSet type, final Object... tickData)
    {
        final World world = (World) tickData[0];
        final int dim = world.provider.dimensionId;
        final ArrayList<?> chunks = chunksToGen.get(Integer.valueOf(dim));

        if ((chunks != null) && (chunks.size() > 0))
        {
            final ChunkCoord c = (ChunkCoord) chunks.get(0);
            final long worldSeed = world.getSeed();
            final Random rand = new Random(worldSeed);
            final long xSeed = rand.nextLong() >> 3;
            final long zSeed = rand.nextLong() >> 3;
            rand.setSeed(((xSeed * c.chunkX) + (zSeed * c.chunkZ)) ^ worldSeed);
            WorldGenHandler.instance.generateWorld(rand, c.chunkX, c.chunkZ, world, false);
            chunks.remove(0);
            chunksToGen.put(Integer.valueOf(dim), chunks);
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void tickStart(final EnumSet type, final Object... tickData)
    {}
}