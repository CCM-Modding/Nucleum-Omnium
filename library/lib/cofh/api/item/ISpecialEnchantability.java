package lib.cofh.api.item;

import net.minecraft.item.ItemStack;

/**
 * Implement this on Items which should have varying enchantability based on metadata or NBT.
 * 
 * @author King Lemming
 */
public interface ISpecialEnchantability {
    
    public int getItemEnchantability(ItemStack stack);
    
}
