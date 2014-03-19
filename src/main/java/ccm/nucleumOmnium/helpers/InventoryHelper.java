package ccm.nucleumOmnium.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Useful methods, all static.
 *
 * @author Dries007
 */
public class InventoryHelper
{
    public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2, boolean ifNull)
    {
        if (stack1 == null || stack2 == null) return ifNull;
        if (!stack1.isItemEqual(stack2)) return false;
        if (!ItemStack.areItemStackTagsEqual(stack1, stack2)) return false;
        return true;
    }

    public static int[] slotArray(int start, int size)
    {
        int[] out = new int[size];
        for (int i = 0; i < size; i++)
            out[i] = i + start;

        return out;
    }

    public static InventoryCrafting newCraftingMatrix(int size, final int stackLimit)
    {
        int i;
        if (size == 9) i = 3;
        else if (size == 4) i = 2;
        else throw new IllegalArgumentException("Crating matrix must be 2x2 or 3x3");
        return new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(EntityPlayer entityplayer)
            {
                return false;
            }
        }, i, i)
        {
            public int getInventoryStackLimit()
            {
                return stackLimit;
            }
        };
    }

    public static void dropItems(World world, IInventory inv, int x, int y, int z)
    {
        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack items = inv.getStackInSlot(i);

            if (items != null && items.stackSize > 0)
            {
                MiscHelper.dropItems(world, inv.getStackInSlot(i).copy(), x, y, z);
            }
        }
    }

    public static List<ItemStack> getList(IInventory inventory)
    {
        List<ItemStack> input = new LinkedList<ItemStack>();

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stackToAdd = inventory.getStackInSlot(i);
            if (stackToAdd == null) continue;
            boolean f = false;
            for (ItemStack stackInList : input)
            {
                if (stackInList == null) continue;
                if (canStacksMerge(stackToAdd, stackInList, false))
                {
                    stackInList.stackSize += stackToAdd.stackSize;
                    f = true;
                    break;
                }
            }
            if (!f) input.add(stackToAdd.copy());
        }

        return input;
    }

    public static boolean contains(ItemStack input, IInventory inv)
    {
        for (int slot = 0; slot < inv.getSizeInventory(); ++slot)
        {
            ItemStack existing = inv.getStackInSlot(slot);

            if (canStacksMerge(existing, input, false)) return true;
        }
        return false;
    }

    public static boolean hasSpaceFor(IInventory inventoryOut, ItemStack output)
    {
        for (int i = 0; i < inventoryOut.getSizeInventory(); i++)
        {
            ItemStack slotStack = inventoryOut.getStackInSlot(i);
            if (slotStack == null) return true;
        }

        ItemStack outCopy = output.copy();
        for (int i = 0; i < inventoryOut.getSizeInventory(); i++)
        {
            ItemStack slotStack = inventoryOut.getStackInSlot(i);
            if (slotStack == null || !canStacksMerge(slotStack, outCopy, false)) continue;

            if (slotStack.stackSize + outCopy.stackSize > outCopy.getMaxStackSize())
            {
                outCopy.stackSize -= (slotStack.getMaxStackSize() - slotStack.stackSize);
                if (outCopy.stackSize == 0) return true;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    public static void addToInventory(IInventory inventoryOut, List<ItemStack> outputItems)
    {
        for (ItemStack stack : outputItems) addToInventory(inventoryOut, stack);
    }

    public static void removeFromInventory(IInventory inventoryIn, List<ItemStack> requiredItems)
    {
        for (ItemStack requiredItem : requiredItems) removeFromInventory(inventoryIn, requiredItem);
    }

    public static void removeFromInventory(IInventory inventoryIn, ItemStack requiredItems)
    {
        for (int i = 0; i < inventoryIn.getSizeInventory(); i++)
        {
            if (canStacksMerge(requiredItems, inventoryIn.getStackInSlot(i), false))
            {
                requiredItems.stackSize -= inventoryIn.decrStackSize(i, requiredItems.stackSize).stackSize;
                if (requiredItems.stackSize == 0) return;
            }
        }
    }

    public static ItemStack addToInventory(IInventory inventoryOut, ItemStack output)
    {
        if (output == null) return null;
        output = output.copy();
        for (int i = 0; i < inventoryOut.getSizeInventory(); i++)
        {
            ItemStack slotStack = inventoryOut.getStackInSlot(i);
            if (slotStack == null || slotStack.stackSize == slotStack.getMaxStackSize()) continue;

            if (canStacksMerge(slotStack, output, false))
            {
                if (slotStack.stackSize + output.stackSize > slotStack.getMaxStackSize())
                {
                    output.stackSize -= (slotStack.getMaxStackSize() - slotStack.stackSize);
                    slotStack.stackSize = slotStack.getMaxStackSize();
                }
                else
                {
                    slotStack.stackSize += output.stackSize;
                    output.stackSize = 0;
                }
            }

            if (output.stackSize == 0) return null;
        }

        for (int i = 0; i < inventoryOut.getSizeInventory(); i++)
        {
            ItemStack slotStack = inventoryOut.getStackInSlot(i);
            if (slotStack != null) continue;
            inventoryOut.setInventorySlotContents(i, output.copy());
            return null;
        }

        return output;
    }

    public static void writeInvToNBT(IInventory inv, String tag, NBTTagCompound data)
    {
        NBTTagList list = new NBTTagList();
        for (byte slot = 0; slot < inv.getSizeInventory(); slot++)
        {
            ItemStack stack = inv.getStackInSlot(slot);
            if (stack != null)
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", slot);
                stack.writeToNBT(itemTag);
                list.appendTag(itemTag);
            }
        }
        data.setTag(tag, list);
    }

    public static void readInvFromNBT(IInventory inv, String tag, NBTTagCompound data)
    {
        NBTTagList list = data.getTagList(tag);
        for (byte entry = 0; entry < list.tagCount(); entry++)
        {
            NBTTagCompound itemTag = (NBTTagCompound) list.tagAt(entry);
            int slot = itemTag.getByte("Slot");
            if (slot >= 0 && slot < inv.getSizeInventory())
            {
                ItemStack stack = ItemStack.loadItemStackFromNBT(itemTag);
                inv.setInventorySlotContents(slot, stack);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static IRecipe findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        // Begin repair recipe handler
        int itemNum = 0;
        ItemStack item1 = null;
        ItemStack item2 = null;
        int slot;

        for (slot = 0; slot < par1InventoryCrafting.getSizeInventory(); ++slot)
        {
            ItemStack itemInSlot = par1InventoryCrafting.getStackInSlot(slot);

            if (itemInSlot != null)
            {
                if (itemNum == 0)
                {
                    item1 = itemInSlot;
                }

                if (itemNum == 1)
                {
                    item2 = itemInSlot;
                }

                ++itemNum;
            }
        }

        if (itemNum == 2 && item1.itemID == item2.itemID && item1.stackSize == 1 && item2.stackSize == 1 && Item.itemsList[item1.itemID].isRepairable())
        {
            Item itemBase = Item.itemsList[item1.itemID];
            int item1Durability = itemBase.getMaxDamage() - item1.getItemDamageForDisplay();
            int item2Durability = itemBase.getMaxDamage() - item2.getItemDamageForDisplay();
            int repairAmt = item1Durability + item2Durability + itemBase.getMaxDamage() * 5 / 100;
            int newDamage = itemBase.getMaxDamage() - repairAmt;

            if (newDamage < 0)
            {
                newDamage = 0;
            }

            ArrayList ingredients = new ArrayList<ItemStack>(2);
            ingredients.add(item1);
            ingredients.add(item2);
            return new ShapelessRecipes(new ItemStack(item1.itemID, 1, newDamage), ingredients);
        }
        // End repair recipe handler
        else
        {
            List recipes = CraftingManager.getInstance().getRecipeList();
            for (Object recipe : recipes)
            {
                IRecipe currentRecipe = (IRecipe) recipe;

                if (currentRecipe.matches(par1InventoryCrafting, par2World))
                {
                    return currentRecipe;
                }
            }

            return null;
        }
    }
}
