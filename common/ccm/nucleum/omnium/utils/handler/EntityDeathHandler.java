/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import ccm.nucleum.omnium.utils.handler.entity.drop.EntityDropHandler;

/**
 * EntityHandler
 * <p>
 * 
 * @author Captain_Shadows
 */
public final class EntityDeathHandler
{
    @ForgeSubscribe
    public void onEntityLivingDeath(final LivingDeathEvent event)
    {
        if (event.source.getDamageType().equalsIgnoreCase("player"))
        {
            EntityDropHandler.dropItem(event.entityLiving);
        } else if (event.source.getSourceOfDamage() instanceof EntityArrow)
        {
            if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity != null)
            {
                if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity instanceof EntityPlayer)
                {
                    EntityDropHandler.dropItem(event.entityLiving);
                }
            }
        }
    }
}