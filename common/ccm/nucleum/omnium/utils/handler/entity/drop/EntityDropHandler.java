/**
 * CCM Modding, ModJam
 */
package ccm.nucleum.omnium.utils.handler.entity.drop;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import ccm.nucleum.omnium.IMod;

/**
 * EntityDrops
 * <p>
 * 
 * @author Captain_Shadows
 */
public class EntityDropHandler
{
    /** All of the registered Drop Handlers */
    private static final List<EntityDrop> drops = new ArrayList<EntityDrop>();

    /** Registers a EntityDrop */
    public static void registerDrop(final EntityDrop drop)
    {
        drops.add(drop);
    }

    /**
     * @param mod
     *            The mod adding this drop
     * @param item
     *            The Item to drop
     */
    public static EntityDrop registerDrop(final IMod mod, final ItemStack item, final float dropRate, final int minValue, final int maxValue, final Class<? extends Entity> entity)
    {
        final EntityDrop tmp = new EntityDrop(mod, item, dropRate, minValue, maxValue, entity);
        registerDrop(tmp);
        return tmp;
    }

    /**
     * @return true if and ONLY if there is a DropHandler registered to that entity
     */
    public static boolean isEntityRegistered(final Entity entity)
    {
        boolean registered = false;
        for (final EntityDrop drop : drops)
        {
            if (drop.shouldDrop(entity.getClass()))
            {
                registered = true;
                break;
            }
        }
        return registered;
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

    /**
     * @return a copy of the all the {@link EntityDrop} registered
     */
    public static List<EntityDrop> getDrops()
    {
        return new ArrayList<EntityDrop>(drops);
    }
}