package ccm.nucleum_omnium.world.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomItem;

public class RandomWeightedBlock extends WeightedRandomItem
{

    public final int blockId;

    public final int metadata;

    public RandomWeightedBlock(final ItemStack ore)
    {
        super(100);
        this.blockId = ore.itemID;
        this.metadata = ore.getItemDamage();
    }

    public RandomWeightedBlock(final ItemStack ore,
                               final int weight)
    {
        super(weight);
        this.blockId = ore.itemID;
        this.metadata = ore.getItemDamage();
    }
}