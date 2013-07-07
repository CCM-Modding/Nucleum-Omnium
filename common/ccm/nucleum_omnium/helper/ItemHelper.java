package ccm.nucleum_omnium.helper;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ccm.nucleum_omnium.base.BaseNIC;

public class ItemHelper extends BaseNIC {

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
			ItemStack tmp = inventory.getStackInSlot(slot);
			if (tmp.attemptDamageItem(MathHelper.getRandomInt(damage, false), rand)) {
				inventory.setInventorySlotContents(slot, getEmty(tmp));
			}
			inventory.setInventorySlotContents(slot, tmp);
		}
	}

	/**
	 * @param item
	 *            Where to get the ID and meta
	 * @return A empty version of the stack
	 */
	public static ItemStack getEmty(ItemStack item) {
		return new ItemStack(item.itemID, 0, item.getItemDamage());
	}

	/**
	 * @param item
	 *            The main ItemStack
	 * @param item2
	 *            The second ItemStack
	 * @return Adds the stack size of both items and if it is bigger than the max of the first it
	 *         returns the first.getMaxStackSize() as the size
	 */
	public static ItemStack getUniun(ItemStack item, ItemStack item2) {
		int size = item.stackSize + item2.stackSize;
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