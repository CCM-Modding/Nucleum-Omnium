package ccm.nucleum_omnium.helper.enums;

import net.minecraft.item.Item;

/**
 * This Interface Has to be extended by any enum that registers Items in order
 * for it to work correctly
 * 
 * @author Captain_Shadows
 */
public interface IItemEnum extends IEnum {
    
    /**
     * @return A new {@link Item} instance, the instance should be the
     *         registered and instantiated Item that contains all of these sub
     *         Items
     */
    public Item getBaseItem();
}