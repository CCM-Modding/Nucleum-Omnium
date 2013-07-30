package ccm.nucleum_omnium.block.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface ICollisionListener {

    public void collide(World world, int x, int y, int z, Entity entity, int meta);
}
