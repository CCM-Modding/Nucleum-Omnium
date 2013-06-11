package lib.cofh.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Contains various helper functions to assist with {@link Item} and {@link ItemStack} manipulation
 * and interaction.
 * 
 * @author King Lemming
 */
public final class ItemHelper
{

    private ItemHelper()
    {}

    public static ItemStack consumeItem(final ItemStack stack)
    {

        if (stack.stackSize == 1){
            if (stack.getItem().hasContainerItem()){
                return stack.getItem().getContainerItemStack(stack);
            }else{
                return null;
            }
        }
        stack.splitStack(1);
        return stack;
    }

    /**
     * Get a hashcode based on the ItemStack's ID and Metadata. As both of these are shorts, this
     * should be collision-free for non-NBT sensitive ItemStacks.
     * 
     * @param stack
     *            The ItemStack to get a hashcode for.
     * @return The hashcode.
     */
    public static int getHashCode(final ItemStack stack)
    {

        return stack.getItemDamage() | (stack.itemID << 16);
    }

    /**
     * Get a hashcode based on an ID and Metadata pair. As both of these are shorts, this should be
     * collision-free if NBT is not involved.
     * 
     * @param id
     *            ID value to use.
     * @param metadata
     *            Metadata value to use.
     * @return The hashcode.
     */
    public static int getHashCode(final int id, final int metadata)
    {

        return metadata | (id << 16);
    }

    /**
     * Extract the ID from a hashcode created from one of the getHashCode() methods in this class.
     */
    public static int getIDFromHashCode(final int hashCode)
    {

        return hashCode >> 16;
    }

    /**
     * Extract the Metadata from a hashcode created from one of the getHashCode() methods in this
     * class.
     */
    public static int getMetaFromHashCode(final int hashCode)
    {

        return hashCode & 0xFF;
    }

    public static String getOreName(final ItemStack stack)
    {

        return OreDictionary.getOreName(OreDictionary.getOreID(stack));
    }

    public static boolean isOreID(final ItemStack stack, final int oreID)
    {

        return OreDictionary.getOreID(stack) == oreID;
    }

    public static boolean isOreName(final ItemStack stack, final String oreName)
    {

        return OreDictionary.getOreName(OreDictionary.getOreID(stack)).equals(oreName);
    }

    /**
     * Determine if a player is holding a registered Fluid Container.
     */
    /*
     * public static boolean isPlayerHoldingFluidContainer(EntityPlayer player) {
     * return FluidContainerRegistry.isContainer(player.getCurrentEquippedItem());
     * }
     */

    /**
     * Determine if a player is holding an ItemStack of a specific Item type.
     */
    public static boolean isPlayerHoldingItem(final Item item, final EntityPlayer player)
    {

        final Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
        return item == null ? equipped == null : item.equals(equipped);
    }

    /**
     * Determine if a player is holding an ItemStack with a specific Item ID, Metadata, and NBT.
     */
    public static boolean isPlayerHoldingItemStack(final ItemStack stack, final EntityPlayer player)
    {

        final ItemStack equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem() : null;
        return stack == null ? equipped == null : (equipped != null) && stack.isItemEqual(equipped) && ItemStack.areItemStackTagsEqual(stack, equipped);
    }

}
