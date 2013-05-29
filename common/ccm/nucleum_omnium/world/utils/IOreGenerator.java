package ccm.nucleum_omnium.world.utils;

import java.util.Random;

import net.minecraft.world.World;

public abstract interface IOreGenerator
{

    public abstract String getOreName();

    public abstract boolean generateOre(Random paramRandom, int paramInt1, int paramInt2, World paramWorld, boolean paramBoolean);
}
