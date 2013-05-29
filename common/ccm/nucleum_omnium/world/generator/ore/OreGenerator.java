package ccm.nucleum_omnium.world.generator.ore;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class OreGenerator extends BaseOreGenerator
{

    final WorldGenerator worldGen;

    final int            count;

    final int            meanY;

    final int            maxVar;

    public OreGenerator(final String name,
                        final WorldGenerator worldGen,
                        final int count,
                        final int meanY,
                        final int maxVar,
                        final boolean regen)
    {
        super(name, regen);
        this.worldGen = worldGen;
        this.count = count;
        this.meanY = meanY;
        this.maxVar = maxVar;
    }

    @Override
    public boolean generateOre(final Random random, final int chunkX, final int chunkZ, final World world, final boolean newGen)
    {
        if ((!newGen) && (!this.regen)){
            return false;
        }
        final int blockX = chunkX * 16;
        final int blockZ = chunkZ * 16;

        for (int i = 0; i < this.count; i++){
            final int x = blockX + random.nextInt(16);
            final int y = (random.nextInt(this.maxVar) + random.nextInt(this.maxVar) + this.meanY) - this.maxVar;
            final int z = blockZ + random.nextInt(16);
            this.worldGen.generate(world, random, x, y, z);
        }
        return true;
    }
}