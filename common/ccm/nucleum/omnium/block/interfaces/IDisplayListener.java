/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.interfaces;

import java.util.Random;

import net.minecraft.world.World;

/**
 * IDisplayListener
 */
public interface IDisplayListener
{

    /**
     * Called during randomDisplayTick()
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random rand);
}
