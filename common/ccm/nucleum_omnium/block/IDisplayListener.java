package ccm.nucleum_omnium.block;

import java.util.Random;

import net.minecraft.world.World;

public interface IDisplayListener {

	public void randomDisplayTick(World world, int x, int y, int z, Random rand);
}
