/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper.item;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import ccm.nucleum.omnium.BaseNIC;
import ccm.nucleum.omnium.utils.helper.MathHelper;

public class ItemHelper extends BaseNIC
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
    public static void damageItem(final IInventory inventory, final int slot, final int damage)
    {// check that the item in the slot is not null
        if (inventory.getStackInSlot(slot) != null)
        {// get the Item in the slot
            final ItemStack tmp = inventory.getStackInSlot(slot);
            // get the amount of damage to apply
            final int dmg = MathHelper.getRandInt(damage);
            // set the damage to whatever it was plus the random amount
            tmp.setItemDamage(tmp.getItemDamage() + dmg);

            if (tmp.getItemDamage() > tmp.getMaxDamage())
            {// if the damage is more than the max the set it to null
                inventory.setInventorySlotContents(slot, null);
            } else
            {// otherwise set it to the temp itemstack
                inventory.setInventorySlotContents(slot, tmp);
            }
        }
    }

    /**
     * @param item
     *            ItemStack
     * @param item2
     *            ItemStack
     * @return true if they have the same ID
     */
    public static boolean equals(final ItemStack item, final ItemStack item2)
    {// check item IDs
        if (item.itemID == item2.itemID)
        {
            return true;
        }
        return false;
    }

    /**
     * @param item
     *            ItemStack
     * @param item2
     *            ItemStack
     * @return true if they have the same ID, and metadata
     */
    public static boolean equalsMeta(final ItemStack item, final ItemStack item2)
    {// let the item handle this
        if (item.isItemEqual(item2))
        {
            return true;
        }
        return false;
    }

    /**
     * @param item
     *            ItemStack
     * @param item2
     *            ItemStack
     * @return true if they have the same ID, metadata, and NBT Tags
     */
    public static boolean equalsNBT(final ItemStack item, final ItemStack item2)
    {// check if ID and Meta are equal
        if (item.isItemEqual(item2))
        {// check if they both have Tag compounds
            if (item.hasTagCompound() && item2.hasTagCompound())
            {// if they do then check them for equality
                if (item.stackTagCompound.equals(item2.stackTagCompound))
                {
                    return true;
                }
            } else
            {// other wise having no NBT Tags the are well .... the same
                return true;
            }
        }
        return false;
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

    /**
     * @param item
     *            The main ItemStack
     * @param item2
     *            The second ItemStack
     * @return Adds the stack size of both items and if it is bigger than the max of the first it returns the first.getMaxStackSize() as the size
     */
    public static ItemStack getUnion(final ItemStack item, final ItemStack item2)
    {// Get the size of both stacks put together, we assume that they are the same...
        final int size = item.stackSize + item2.stackSize;
        if (size > item.getMaxStackSize())
        {// if the combined size is greater than the max size of the first stack then we set the size to the max size of the first stack
            return new ItemStack(item.itemID, item.getMaxStackSize(), item.getItemDamage());
        } else
        {// Otherwise we set the size to the combined size
            return new ItemStack(item.itemID, size, item.getItemDamage());
        }
    }
}