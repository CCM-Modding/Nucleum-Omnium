/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * ICollisionListener
 */
public interface ICollisionListener
{

    /**
     * Called during onEntityCollidedWithBlock()
     */
    public void collide(World world, int x, int y, int z, Entity entity, int meta);
}
