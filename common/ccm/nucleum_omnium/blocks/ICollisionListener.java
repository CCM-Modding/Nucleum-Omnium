package ccm.nucleum_omnium.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * WIP(Work In Progress) DO NOT USE!
 */
public interface ICollisionListener
{

    public void collide(World world, int x, int y, int z, Entity entity, int meta);

}