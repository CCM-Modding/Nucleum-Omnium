package ccm.nucleum_omnium.world.generator.ore;

import java.util.HashSet;

import net.minecraft.world.gen.feature.WorldGenerator;
import ccm.nucleum_omnium.world.utils.IOreGenerator;

public abstract class BaseOreGenerator implements IOreGenerator
{

    final String     name;

    final boolean    regen;

    final HashSet<?> biomes = new HashSet<Object>();

    public BaseOreGenerator(final String name,
                            final boolean regen)
    {
        this.name = name;
        this.regen = regen;
    }

    public BaseOreGenerator(final String name,
                            final WorldGenerator worldGen,
                            final boolean regen)
    {
        this.name = name;
        this.regen = regen;
    }

    @Override
    public final String getOreName()
    {
        return this.name;
    }
}