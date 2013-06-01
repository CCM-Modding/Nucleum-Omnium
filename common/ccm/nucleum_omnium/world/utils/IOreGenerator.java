package ccm.nucleum_omnium.world.utils;

import java.util.Random;

import net.minecraft.world.World;

public interface IOreGenerator
{

    public abstract String getOreName();

    public abstract boolean generateOre(Random random, int x, int z, World world, boolean regen);
}