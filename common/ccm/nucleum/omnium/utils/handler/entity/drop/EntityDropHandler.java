/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.entity.drop;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import ccm.nucleum.omnium.CCMMod;

/**
 * EntityDrops
 * <p>
 * 
 * @author Captain_Shadows
 */
public final class EntityDropHandler
{
    /** All of the registered Drop Handlers */
    private static final List<EntityDrop> drops = new ArrayList<EntityDrop>();

    /**
     * @param mod
     *            The mod adding this drop
     * @param item
     *            The Item to drop
     */
    public static void registerDrop(final CCMMod mod,
                                    final ItemStack item,
                                    final float dropRate,
                                    final int minValue,
                                    final int maxValue,
                                    final Class<? extends Entity> entity)
    {
        drops.add(new EntityDrop(mod, item, dropRate, minValue, maxValue, entity));
    }

    /**
     * @return true if and ONLY if there is a DropHandler registered to that entity
     */
    public static boolean isEntityRegistered(final Entity entity)
    {
        for (final EntityDrop drop : drops)
        {
            if (drop.shouldDrop(entity.getClass()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param entity
     *            The entity that is dropping the Item
     */
    public static void dropItem(final Entity entity)
    {
        if (isEntityRegistered(entity))
        {
            for (final EntityDrop drop : drops)
            {
                drop.dropItem(entity);
            }
        }
    }
}