/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.helper.enums;

import net.minecraft.item.Item;
import net.minecraft.util.Icon;

/**
 * This Interface Has to be extended by any enum that registers Items in order for it to work
 * correctly
 * 
 * @author Captain_Shadows
 */
public interface IItemEnum
{

    Icon getIcon();

    /**
     * @return A new {@link Item} instance, the instance should be the registered and instantiated
     *         Item that contains all of these sub Items
     */
    public Item getBaseItem();
}