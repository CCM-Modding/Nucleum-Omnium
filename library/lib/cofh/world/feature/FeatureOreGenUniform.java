package lib.cofh.world.feature;

import java.util.Locale;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FeatureOreGenUniform extends FeatureBase {

    final WorldGenerator worldGen;

    final int            count;

    final int            minY;

    final int            maxY;

    public FeatureOreGenUniform(final String name, final WorldGenerator worldGen, final int count,
            final int minY, final int maxY, final boolean regen) {

        super(name, regen);
        this.worldGen = worldGen;
        this.count = count;
        this.minY = minY;
        this.maxY = maxY;
    }

    /* IFeatureGenerator */
    @Override
    public boolean generateFeature(final Random random, final int chunkX, final int chunkZ,
            final World world, final boolean newGen) {

        if (!newGen && !this.regen)
            return false;
        final int blockX = chunkX * 16;
        final int blockZ = chunkZ * 16;

        if (this.type > 0) {
            final String biomeName = world.getBiomeGenForCoords(chunkX, chunkZ).biomeName
                    .toLowerCase(Locale.ENGLISH);
            final boolean onList = this.biomes.contains(biomeName);

            if (this.type == 1 && !onList || this.type == 2 && onList)
                return false;
        }
        for (int i = 0; i < this.count; i++) {
            final int x = blockX + random.nextInt(16);
            final int y = this.minY + random.nextInt(this.maxY - this.minY);
            final int z = blockZ + random.nextInt(16);
            this.worldGen.generate(world, random, x, y, z);
        }
        return true;
    }

}
