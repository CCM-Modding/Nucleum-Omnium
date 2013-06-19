package ccm.nucleum_world.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;

import lib.cofh.util.ChunkCoord;
import net.minecraft.world.World;
import ccm.nucleum_world.generator.WorldGenHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandlerWorld implements ITickHandler {
    public static TickHandlerWorld               instance    = new TickHandlerWorld();
    
    public static HashMap<Integer, ArrayList<?>> chunksToGen = new HashMap<Integer, ArrayList<?>>();
    
    @SuppressWarnings("rawtypes")
    public void tickStart(EnumSet type, Object... tickData) {
    }
    
    @SuppressWarnings("rawtypes")
    public void tickEnd(EnumSet type, Object... tickData) {
        World world = (World) tickData[0];
        int dim = world.provider.dimensionId;
        ArrayList<?> chunks = (ArrayList<?>) chunksToGen.get(Integer.valueOf(dim));
        
        if ((chunks != null) && (chunks.size() > 0)) {
            ChunkCoord c = (ChunkCoord) chunks.get(0);
            long worldSeed = world.getSeed();
            Random rand = new Random(worldSeed);
            long xSeed = rand.nextLong() >> 3;
            long zSeed = rand.nextLong() >> 3;
            rand.setSeed(xSeed * c.chunkX + zSeed * c.chunkZ ^ worldSeed);
            WorldGenHandler.instance.generateWorld(rand, c.chunkX, c.chunkZ, world, false);
            chunks.remove(0);
            chunksToGen.put(Integer.valueOf(dim), chunks);
        }
    }
    
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.WORLD);
    }
    
    @Override
    public String getLabel() {
        return "nucleum_world.tick";
    }
}