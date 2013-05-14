package ccm.nucleum_omnium.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHelper
{

    /**
     * Damages a Item inside of a inventory.
     * 
     * @param inventory
     *            The inventory in which the Item is found in.
     * @param slot
     *            The Slot in which the Item is found in.
     * @param damage
     *            The maximum amount of damage possible.
     */
    public static void damageItem(final ItemStack[] inventory, final int slot, final int damage)
    {
        if (inventory[slot] != null){
            if (inventory[slot].attemptDamageItem(MathHelper.getRandomInt(damage), MathHelper.rand)){
                inventory[slot] = null;
            }
        }
    }

    /**
     * Gets an Items Unlocalized name without the item prefix.
     * 
     * @param item
     *            The Item to get the name of.
     * @return The Unlocalized name without the item prefix.
     */
    public static String getItemName(final Item item)
    {
        return item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(".") + 1);
    }

    private ItemHelper()
    {}
}