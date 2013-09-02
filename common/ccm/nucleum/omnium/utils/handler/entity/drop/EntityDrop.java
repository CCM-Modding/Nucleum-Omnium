/**
 * CCM Modding, ModJam
 */
package ccm.nucleum.omnium.utils.handler.entity.drop;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.utils.helper.MathHelper;

/**
 * EntityDropHandler
 * <p>
 * This class defines a new Entity Drop
 * 
 * @author Captain_Shadows
 */
public final class EntityDrop
{
    /** The mod registering this coin */
    private final IMod mod;

    /** Item to drop */
    private final ItemStack item;

    /** The drop rate of it */
    private final float dropRate;

    /** The Maximum to drop */
    private final int maxValue;

    /** The Minimum to drop */
    private final int minValue;

    /** The entity that should drop it */
    private final Class<? extends Entity> entity;

    /**
     * @param mod
     *            The mod adding this drop
     * @param dropRate
     *            The Rate at which to drop it
     * @param entitys
     *            The entity's that are allowed to drop it
     */
    public EntityDrop(final IMod mod, final ItemStack item, final float dropRate, final int minValue, final int maxValue, final Class<? extends Entity> entity)
    {
        this.mod = mod;
        this.item = item;
        this.dropRate = dropRate;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.entity = entity;
    }

    /**
     * @return The mod that registered this coin
     */
    public IMod getMod()
    {
        return mod;
    }

    /**
     * @return The item to drop
     */
    public ItemStack getDrop()
    {
        return item;
    }

    /**
     * @return The drop rate
     */
    public float getDropRate()
    {
        return dropRate;
    }

    /**
     * @return The entity that should drop it
     */
    public Class<? extends Entity> getEntity()
    {
        return entity;
    }

    /**
     * @param entity
     *            the entity class to check
     * @return true if and ONLY if the entity class is the same as the class that this Handler "uses"
     */
    public boolean shouldDrop(final Class<? extends Entity> entity)
    {
        return entity.equals(this.entity);
    }

    /**
     * @param entity
     *            Drops the Item
     */
    public void dropItem(final Entity entity)
    {
        if (shouldDrop(entity.getClass()))
        {
            if (MathHelper.rand().nextDouble() < dropRate)
            {
                entity.entityDropItem(new ItemStack(item.itemID, MathHelper.getRandInt(minValue, maxValue), item.getItemDamage()), 0.0F);
            }
        }
    }
}