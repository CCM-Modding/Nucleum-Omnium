package lib.cofh.util;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Contains various helper functions to assist with {@link Item} and {@link ItemStack} manipulation and interaction.
 * 
 * @author King Lemming
 */
public final class ItemHelper
{
    private ItemHelper()
    {}

    public static ItemStack cloneStack(Item item, int stackSize)
    {
        if (item == null)
        {
            return null;
        }
        ItemStack stack = new ItemStack(item, stackSize);

        return stack;
    }

    public static ItemStack cloneStack(ItemStack stack, int stackSize)
    {
        if (stack == null)
        {
            return null;
        }
        ItemStack retStack = stack.copy();
        retStack.stackSize = stackSize;

        return retStack;
    }

    public static ItemStack copyTag(ItemStack container, ItemStack other)
    {
        if ((other != null) && (other.stackTagCompound != null))
        {
            container.stackTagCompound = (NBTTagCompound) other.stackTagCompound.copy();
        }
        return container;
    }

    public static ItemStack consumeItem(ItemStack stack)
    {
        if (stack.stackSize == 1)
        {
            if (stack.getItem().hasContainerItem())
            {
                return stack.getItem().getContainerItemStack(stack);
            }
            return null;
        }
        stack.splitStack(1);
        return stack;
    }

    /**
     * Gets a vanilla CraftingManager result.
     */
    public static ItemStack findMatchingRecipe(InventoryCrafting inv, World world)
    {
        ItemStack[] dmgItems = new ItemStack[2];
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            if (inv.getStackInSlot(i) != null)
            {
                if (dmgItems[0] == null)
                {
                    dmgItems[0] = inv.getStackInSlot(i);
                } else
                {
                    dmgItems[1] = inv.getStackInSlot(i);
                    break;
                }
            }
        }
        if ((dmgItems[1] != null) && (dmgItems[0].itemID == dmgItems[1].itemID) && (dmgItems[0].stackSize == 1) && (dmgItems[1].stackSize == 1)
                && Item.itemsList[dmgItems[0].itemID].isRepairable())
        {
            Item theItem = Item.itemsList[dmgItems[0].itemID];
            int var13 = theItem.getMaxDamage() - dmgItems[0].getItemDamageForDisplay();
            int var8 = theItem.getMaxDamage() - dmgItems[1].getItemDamageForDisplay();
            int var9 = var13 + var8 + ((theItem.getMaxDamage() * 5) / 100);
            int var10 = Math.max(0, theItem.getMaxDamage() - var9);
            return new ItemStack(dmgItems[0].itemID, 1, var10);
        }
        IRecipe recipe;
        for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); ++i)
        {
            recipe = (IRecipe) CraftingManager.getInstance().getRecipeList().get(i);

            if (recipe.matches(inv, world))
            {
                return recipe.getCraftingResult(inv);
            }
        }
        return null;
    }

    /**
     * Get a hashcode based on the ItemStack's ID and Metadata. As both of these are shorts, this should be collision-free for non-NBT sensitive ItemStacks.
     * 
     * @param stack
     *            The ItemStack to get a hashcode for.
     * @return The hashcode.
     */
    public static int getHashCode(ItemStack stack)
    {
        return stack.getItemDamage() | (stack.itemID << 16);
    }

    /**
     * Get a hashcode based on an ID and Metadata pair. As both of these are shorts, this should be collision-free if NBT is not involved.
     * 
     * @param id
     *            ID value to use.
     * @param metadata
     *            Metadata value to use.
     * @return The hashcode.
     */
    public static int getHashCode(int id, int metadata)
    {
        return metadata | (id << 16);
    }

    /**
     * Extract the ID from a hashcode created from one of the getHashCode() methods in this class.
     */
    public static int getIDFromHashCode(int hashCode)
    {
        return hashCode >> 16;
    }

    /**
     * Extract the Metadata from a hashcode created from one of the getHashCode() methods in this class.
     */
    public static int getMetaFromHashCode(int hashCode)
    {
        return hashCode & 0xFF;
    }

    public static String getOreName(ItemStack stack)
    {
        return OreDictionary.getOreName(OreDictionary.getOreID(stack));
    }

    public static boolean isOreID(ItemStack stack, int oreID)
    {
        return OreDictionary.getOreID(stack) == oreID;
    }

    public static boolean isOreName(ItemStack stack, String oreName)
    {
        return OreDictionary.getOreName(OreDictionary.getOreID(stack)).equals(oreName);
    }

    /**
     * Determine if a player is holding a registered Fluid Container.
     */
    public static final boolean isPlayerHoldingFluidContainer(EntityPlayer player)
    {
        return FluidContainerRegistry.isContainer(player.getCurrentEquippedItem());
    }

    public static final boolean isPlayerHoldingEnergyContainerItem(EntityPlayer player)
    {
        return EnergyHelper.isPlayerHoldingEnergyContainerItem(player);
    }

    /**
     * Determine if a player is holding an ItemStack of a specific Item type.
     */
    public static final boolean isPlayerHoldingItem(Item item, EntityPlayer player)
    {
        Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
        return item == null ? equipped == null : item.equals(equipped);
    }

    /**
     * Determine if a player is holding an ItemStack with a specific Item ID, Metadata, and NBT.
     */
    public static final boolean isPlayerHoldingItemStack(ItemStack stack, EntityPlayer player)
    {
        ItemStack equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem() : null;
        return stack == null ? equipped == null : (equipped != null) && stack.isItemEqual(equipped) && ItemStack.areItemStackTagsEqual(stack, equipped);
    }

    public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB)
    {
        return ItemStack.areItemStacksEqual(stackA, stackB);
    }

    public static boolean areItemStacksEqualNoNBT(ItemStack stackA, ItemStack stackB)
    {
        if (stackB == null)
        {
            return false;
        }
        return (stackA.itemID == stackB.itemID)
                && (stackA.getItemDamage() == OreDictionary.WILDCARD_VALUE ? true : stackB.getItemDamage() == OreDictionary.WILDCARD_VALUE ? true
                        : stackA.getHasSubtypes() == false ? true : stackB.getItemDamage() == stackA.getItemDamage());
    }

    public static boolean craftingEquivalent(ItemStack checked, ItemStack source, String oreDict)
    {
        return areItemStacksEqualNoNBT(checked, source) ? true : oreDict == null ? false : oreDict.equals("Unknown") ? false : getOreName(checked).equalsIgnoreCase(oreDict);
    }

    public static String getItemNBTString(ItemStack theItem, String nbtKey, String invalidReturn)
    {
        return theItem.stackTagCompound != null ? theItem.stackTagCompound.hasKey(nbtKey) ? theItem.stackTagCompound.getString(nbtKey) : invalidReturn : invalidReturn;
    }

    public static Item getItemFromStack(ItemStack theStack)
    {
        return theStack == null ? null : theStack.getItem();
    }

    public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB)
    {
        return (stackA.itemID == stackB.itemID) && ((stackA.getItemDamage() == stackB.getItemDamage()) || (stackA.getHasSubtypes() == false));
    }

    public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB)
    {
        return stackA.itemID == stackB.itemID;
    }

    public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT)
    {
        return (stackA.itemID == stackB.itemID) && (stackA.getItemDamage() == stackB.getItemDamage())
                && (!checkNBT || doNBTsMatch(stackA.stackTagCompound, stackB.stackTagCompound));
    }

    public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT)
    {
        return (stackA.itemID == stackB.itemID) && (!checkNBT || doNBTsMatch(stackA.stackTagCompound, stackB.stackTagCompound));
    }

    public static boolean doOreIDsMatch(ItemStack stackA, ItemStack stackB)
    {
        return (OreDictionary.getOreID(stackA) >= 0) && (OreDictionary.getOreID(stackA) == OreDictionary.getOreID(stackB));
    }

    public static boolean doNBTsMatch(NBTTagCompound nbtA, NBTTagCompound nbtB)
    {
        return nbtA == null ? nbtB == null ? true : false : nbtB == null ? false : nbtA.equals(nbtB);
    }

    /**
     * Adds Inventory information to ItemStacks which themselves hold things. Called in addInformation().
     */
    public static void addInventoryInformation(ItemStack stack, List<String> list)
    {
        if (stack.stackTagCompound.hasKey("Inventory") && (stack.stackTagCompound.getTagList("Inventory").tagCount() > 0))
        {
            if (!StringHelper.isShiftKeyDown())
            {
                list.add(StringHelper.shiftForInfo);
            }
            if (!StringHelper.isShiftKeyDown())
            {
                return;
            }
            list.add("Contents:");
            NBTTagList nbtlist = stack.stackTagCompound.getTagList("Inventory");
            ItemStack curStack;
            for (int i = 0; i < nbtlist.tagCount(); i++)
            {
                NBTTagCompound tag = (NBTTagCompound) nbtlist.tagAt(i);
                curStack = ItemStack.loadItemStackFromNBT(tag);
                if (curStack != null)
                {
                    list.add("    " + StringHelper.BRIGHT_GREEN + curStack.stackSize + " " + StringHelper.GRAY + curStack.getDisplayName());
                }
            }
        }
    }

    public static void addInventoryInformation(ItemStack stack, List<String> list, int minSlot, int maxSlot)
    {
        if (stack.stackTagCompound.hasKey("Inventory") && (stack.stackTagCompound.getTagList("Inventory").tagCount() > 0))
        {
            if (!StringHelper.isShiftKeyDown())
            {
                list.add(StringHelper.shiftForInfo);
            }
            if (!StringHelper.isShiftKeyDown())
            {
                return;
            }
            list.add("Contents:");
            NBTTagList nbtlist = stack.stackTagCompound.getTagList("Inventory");
            ItemStack curStack;
            for (int i = 0; i < nbtlist.tagCount(); i++)
            {
                NBTTagCompound tag = (NBTTagCompound) nbtlist.tagAt(i);
                int slot = tag.getInteger("Slot");

                if ((slot < minSlot) || (slot > maxSlot))
                {
                    continue;
                }
                curStack = ItemStack.loadItemStackFromNBT(tag);
                if (curStack != null)
                {
                    list.add("    " + StringHelper.BRIGHT_GREEN + curStack.stackSize + " " + StringHelper.GRAY + curStack.getDisplayName());
                }
            }
        }
    }
}