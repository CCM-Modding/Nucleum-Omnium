/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper.item;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ccm.nucleum.omnium.utils.helper.MathHelper;

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
     * @return Adds the stack size of both items and if it is bigger than the max of the first it returns the first.getMaxStackSize() as the
     *         size
     */
    public static ItemStack getUnion(final ItemStack item, final ItemStack item2)
    {// Get the size of both stacks put together, we assume that they are the same...
        final int size = item.stackSize + item2.stackSize;
        if (size > item.getMaxStackSize())
        {// if the combined size is greater than the max size of the first stack then we set the size to the max size of the first stack
            return new ItemStack(item.itemID, item.getMaxStackSize(), item.getItemDamage());
        }
        // Otherwise we set the size to the combined size
        return new ItemStack(item.itemID, size, item.getItemDamage());
    }

    /**
     * Compares two ItemStacks for equality, testing itemID, metaData, stackSize, and their NBTTagCompounds (if they are present)
     * 
     * @param first
     *            The first ItemStack being tested for equality
     * @param second
     *            The second ItemStack being tested for equality
     * @return true if the two ItemStacks are equivalent, false otherwise
     */
    public static boolean compare(ItemStack first, ItemStack second)
    {
        return (compareInt(first, second) == 0);
    }

    /**
     * @param first
     *            The first ItemStack being tested for equality
     * @param second
     *            The second ItemStack being tested for equality
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    public static int compareInt(ItemStack first, ItemStack second)
    {
        if ((first != null) && (second != null))
        { // Sort on itemID
            if (first.itemID == second.itemID)
            { // Then sort on meta
                if (first.getItemDamage() == second.getItemDamage())
                { // Then sort on NBT
                    if (first.hasTagCompound() && second.hasTagCompound())
                    { // Then sort on stack size
                        if (first.getTagCompound().equals(second.getTagCompound()))
                        {
                            return (first.stackSize - second.stackSize);
                        }
                        return (first.getTagCompound().hashCode() - second.getTagCompound().hashCode());
                    } else if (!(first.hasTagCompound()) && second.hasTagCompound())
                    {
                        return -1;
                    } else if (first.hasTagCompound() && !(second.hasTagCompound()))
                    {
                        return 1;
                    } else
                    {
                        return (first.stackSize - second.stackSize);
                    }
                }
                return (first.getItemDamage() - second.getItemDamage());
            }
            return (first.itemID - second.itemID);
        } else if ((first != null) && (second == null))
        {
            return -1;
        } else if ((first == null) && (second != null))
        {
            return 1;
        } else
        {
            return 0;
        }
    }

    /**
     * Compares two ItemStacks for equality, testing itemID, metaData, and their NBTTagCompounds (if they are present)
     * 
     * @param first
     *            The first ItemStack being tested for equality
     * @param second
     *            The second ItemStack being tested for equality
     * @return true if the two ItemStacks are equivalent, false otherwise
     */
    public static boolean compareNoSize(ItemStack first, ItemStack second)
    {
        return (compareIntNoSize(first, second) == 0);
    }

    /**
     * @param first
     *            The first ItemStack being tested for equality
     * @param second
     *            The second ItemStack being tested for equality
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    public static int compareIntNoSize(ItemStack first, ItemStack second)
    {
        if ((first != null) && (second != null))
        { // Sort on itemID
            if (first.itemID == second.itemID)
            { // Then sort on meta
                if (first.getItemDamage() == second.getItemDamage())
                { // Then sort on NBT
                    if (first.hasTagCompound() && second.hasTagCompound())
                    {
                        return (first.getTagCompound().hashCode() - second.getTagCompound().hashCode());
                    } else if (!(first.hasTagCompound()) && second.hasTagCompound())
                    {
                        return -1;
                    } else if (first.hasTagCompound() && !(second.hasTagCompound()))
                    {
                        return 1;
                    } else
                    {
                        return (first.stackSize - second.stackSize);
                    }
                }
                return (first.getItemDamage() - second.getItemDamage());
            }
            return (first.itemID - second.itemID);
        } else if ((first != null) && (second == null))
        {
            return -1;
        } else if ((first == null) && (second != null))
        {
            return 1;
        } else
        {
            return 0;
        }
    }
}