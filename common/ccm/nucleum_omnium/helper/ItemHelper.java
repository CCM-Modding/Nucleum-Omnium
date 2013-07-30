package ccm.nucleum_omnium.helper;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import ccm.nucleum_omnium.base.BaseNIC;

public class ItemHelper extends BaseNIC {

    /**
     * @param item
     *            ItemStack
     * @param item2
     *            ItemStack
     * @return true if they are equal
     */
    public static boolean equals(final ItemStack item, final ItemStack item2) {
        if (item.itemID == item2.itemID) {
            return true;
        }
        return false;
    }

    /**
     * @param item
     *            ItemStack
     * @param item2
     *            ItemStack
     * @return true if they are equal
     */
    public static boolean equalsMeta(final ItemStack item, final ItemStack item2) {
        if (item.isItemEqual(item2)) {
            return true;
        }
        return false;
    }

    /**
     * @param item
     *            ItemStack
     * @param item2
     *            ItemStack
     * @return true if they are equal
     */
    public static boolean equalsNBT(final ItemStack item, final ItemStack item2) {
        if (item.isItemEqual(item2)) {
            if (item.hasTagCompound()) {
                if (item2.hasTagCompound()) {
                    if (item.stackTagCompound.equals(item2.stackTagCompound)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

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
    public static void damageItem(final IInventory inventory, final int slot, final int damage) {
        if (inventory.getStackInSlot(slot) != null) {
            final ItemStack tmp = inventory.getStackInSlot(slot);

            final int dmg = MathHelper.getRandomInt(damage);

            tmp.setItemDamage(tmp.getItemDamage() + dmg);

            if (tmp.getItemDamage() > tmp.getMaxDamage()) {
                inventory.setInventorySlotContents(slot, null);
            } else {
                inventory.setInventorySlotContents(slot, tmp);
            }
        }
    }

    /**
     * @param item
     *            The main ItemStack
     * @param item2
     *            The second ItemStack
     * @return Adds the stack size of both items and if it is bigger than the max of the first it
     *         returns the first.getMaxStackSize() as the size
     */
    public static ItemStack getUniun(final ItemStack item, final ItemStack item2) {
        final int size = item.stackSize + item2.stackSize;
        if (size > item.getMaxStackSize()) {
            return new ItemStack(item.itemID, item.getMaxStackSize(), item.getItemDamage());
        } else {
            return new ItemStack(item.itemID, size, item.getItemDamage());
        }
    }

    /**
     * Gets an Items Unlocalized name without the item prefix.
     * 
     * @param item
     *            The Item to get the name of.
     * @return The Unlocalized name without the item prefix.
     */
    public static String getItemName(final Item item) {
        return item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(".") + 1);
    }
}