package nucleum_omnium.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenHandler implements IWorldGenerator
{

    private final int oreID;

    private final int maxHeight;

    private final int minHeight;

    private final int size;

    private final int density;

    public WorldGenHandler(final int oreID,
                           final int maxHeight,
                           final int minHeight,
                           final int size,
                           final int density)
    {
        this.oreID = oreID;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.size = size;
        this.density = density;
    }

    @Override
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider)
    {
        final int veinCount = random.nextInt(10);
        for (int n = 0; n < veinCount; n++){
            final int randPosX = (chunkX * 16) + random.nextInt(16);
            final int randPosY = random.nextInt(this.maxHeight - this.minHeight) + this.minHeight;
            final int randPosZ = (chunkZ * 16) + random.nextInt(16);
            new WorldGenMineable(this.oreID, this.size, this.density, Block.stone.blockID).generate(world, random, randPosX, randPosY, randPosZ);
        }
    }
}