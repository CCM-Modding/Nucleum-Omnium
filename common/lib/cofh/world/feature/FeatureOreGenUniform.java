package lib.cofh.world.feature;

import java.util.Locale;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FeatureOreGenUniform extends FeatureBase
{

    final WorldGenerator worldGen;
    final int count;
    final int minY;
    final int maxY;

    public FeatureOreGenUniform(final String name, final WorldGenerator worldGen, final int count, final int minY, final int maxY, final boolean regen)
    {

        super(name, regen);
        this.worldGen = worldGen;
        this.count = count;
        this.minY = minY;
        this.maxY = maxY;
    }

    /* IFeatureGenerator */
    @Override
    public boolean generateFeature(final Random random, final int chunkX, final int chunkZ, final World world, final boolean newGen)
    {

        if (!newGen && !regen)
        {
            return false;
        }
        final int blockX = chunkX * 16;
        final int blockZ = chunkZ * 16;

        if (type > 0)
        {
            final String biomeName = world.getBiomeGenForCoords(chunkX, chunkZ).biomeName.toLowerCase(Locale.ENGLISH);
            final boolean onList = biomes.contains(biomeName);

            if (((type == 1) && !onList) || ((type == 2) && onList))
            {
                return false;
            }
        }
        for (int i = 0; i < count; i++)
        {
            final int x = blockX + random.nextInt(16);
            final int y = minY + random.nextInt(maxY - minY);
            final int z = blockZ + random.nextInt(16);
            worldGen.generate(world, random, x, y, z);
        }
        return true;
    }

}
