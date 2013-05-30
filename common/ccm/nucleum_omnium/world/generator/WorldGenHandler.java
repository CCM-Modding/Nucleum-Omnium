package ccm.nucleum_omnium.world.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomItem;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenHandler implements IWorldGenerator
{

    private static List<GeneratorEntry> generatorListNether  = new ArrayList<GeneratorEntry>();

    private static List<GeneratorEntry> generatorListSurface = new ArrayList<GeneratorEntry>();

    private static List<GeneratorEntry> generatorListEnd     = new ArrayList<GeneratorEntry>();

    public static WorldGenHandler       instance             = new WorldGenHandler();

    @Override
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider)
    {

        this.generateWorld(world, random, chunkX, chunkZ);
    }

    protected void genStandardOre1(final World world, final GeneratorEntry entry, final Random random, final int blockX, final int blockZ)
    {
        for (int i = 0; i < entry.count; i++){
            final int x = blockX + random.nextInt(16);
            final int y = entry.minY + random.nextInt(entry.maxY - entry.minY);
            final int z = blockZ + random.nextInt(16);
            entry.worldGen.generate(world, random, x, y, z);
        }
    }

    protected void genStandardOre2(final World world, final GeneratorEntry entry, final Random random, final int blockX, final int blockZ)
    {
        for (int i = 0; i < entry.count; i++){
            final int x = blockX + random.nextInt(16);
            final int y = (random.nextInt(entry.maxY) + random.nextInt(entry.maxY) + entry.minY) - entry.maxY;
            final int z = blockZ + random.nextInt(16);
            entry.worldGen.generate(world, random, x, y, z);
        }
    }

    public static void addNetherGenerator(final WorldGenerator generator, final int minY, final int maxY, final int count, final int type)
    {
        generatorListNether.add(new GeneratorEntry(generator, minY, maxY, count, type));
    }

    public static void addSurfaceGenerator(final WorldGenerator generator, final int minY, final int maxY, final int count, final int type)
    {
        generatorListSurface.add(new GeneratorEntry(generator, minY, maxY, count, type));
    }

    public static void addEndGenerator(final WorldGenerator generator, final int minY, final int maxY, final int count, final int type)
    {
        generatorListEnd.add(new GeneratorEntry(generator, minY, maxY, count, type));
    }

    public static void prependNetherGenerator(final WorldGenerator generator, final int minY, final int maxY, final int count, final int type)
    {
        generatorListNether.add(0, new GeneratorEntry(generator, minY, maxY, count, type));
    }

    public static void prependSurfaceGenerator(final WorldGenerator generator, final int minY, final int maxY, final int count, final int type)
    {
        generatorListSurface.add(0, new GeneratorEntry(generator, minY, maxY, count, type));
    }

    public static void prependEndGenerator(final WorldGenerator generator, final int minY, final int maxY, final int count, final int type)
    {
        generatorListEnd.add(0, new GeneratorEntry(generator, minY, maxY, count, type));
    }

    private void generateWorld(final World world, final Random random, final int chunkX, final int chunkZ)
    {
        List<GeneratorEntry> toGenList;
        switch (world.provider.dimensionId) {
            case -1:
                toGenList = generatorListNether;
                break;
            case 0:
                toGenList = generatorListSurface;
                break;
            case 1:
                toGenList = generatorListEnd;
                break;
            default:
                toGenList = generatorListSurface;
        }

        final int blockX = chunkX * 16;
        final int blockZ = chunkZ * 16;

        for (final GeneratorEntry entry : toGenList){
            if (entry.type == GenType.ORE_1.ordinal()){
                this.genStandardOre1(world, entry, random, blockX, blockZ);
            }else if (entry.type == GenType.ORE_2.ordinal()){
                this.genStandardOre2(world, entry, random, blockX, blockZ);
            }
        }
    }

    public static enum GenType
    {
        ORE_1,
        ORE_2;
    }

    public static class GeneratorEntry
    {

        public final WorldGenerator worldGen;

        public final int            minY;

        public final int            maxY;

        public final int            count;

        public final int            type;

        public GeneratorEntry(final WorldGenerator worldGen,
                              final int minY,
                              final int maxY,
                              final int count,
                              final int type)
        {
            this.worldGen = worldGen;
            this.minY = minY;
            this.maxY = maxY;
            this.count = count;
            this.type = type;
        }
    }

    public static class ResourceEntry extends WeightedRandomItem
    {

        public final int blockId;

        public final int metadata;

        public ResourceEntry(final ItemStack ore,
                             final int weight)
        {
            super(weight);
            this.blockId = ore.itemID;
            this.metadata = ore.getItemDamage();
        }
    }
}