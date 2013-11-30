/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper.item;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//REDO
public class WrappedStack implements Comparable<WrappedStack>
{
    public static final int ORE_DICTIONARY_NOT_FOUND = -1;

    private int stackSize;
    private final ItemStack itemStack;
    private final OreStack oreStack;

    /**
     * Creates a new WrappedStack object which wraps the given input. Valid inputs would be ItemStacks or OreStacks. If something other than an ItemStack or an OreStack is used as
     * input, nothing is wrapped and the size of the wrapped stack is set to -1 to indicate an invalid wrapped stack.
     * 
     * @param object
     *            The newly created wrapped stack object
     */
    public WrappedStack(Object object)
    {
        /*
         * If we are given an Item or a Block, convert it to an ItemStack for further inspection
         */
        if (object instanceof Item)
        {
            object = new ItemStack((Item) object);
        } else if (object instanceof Block)
        {
            object = new ItemStack((Block) object);
        }

        /*
         * We are given an ItemStack to wrap
         */
        if (object instanceof ItemStack)
        {

            ItemStack itemStack = (ItemStack) object;

            this.itemStack = itemStack.copy();
            oreStack = null;
            stackSize = this.itemStack.stackSize;
            this.itemStack.stackSize = 1;
        }
        /*
         * Or we are given an OreStack to wrap
         */
        else if (object instanceof OreStack)
        {

            itemStack = null;
            oreStack = (OreStack) object;
            stackSize = oreStack.stackSize;
            oreStack.stackSize = 1;
        } else if (object instanceof ArrayList)
        {
            itemStack = null;

            ArrayList<?> objectList = (ArrayList<?>) object;

            OreStack tempOreStack = getOreStackFromList(objectList);

            if (tempOreStack != null)
            {
                oreStack = new OreStack(tempOreStack.oreName, 1);
                stackSize = tempOreStack.stackSize;
            } else
            {
                oreStack = null;
                stackSize = -1;
            }
        } else if (object instanceof WrappedStack)
        {
            WrappedStack wrappedStack = (WrappedStack) object;

            itemStack = wrappedStack.itemStack;
            oreStack = wrappedStack.oreStack;
            stackSize = wrappedStack.stackSize;
        }
        /*
         * Else, we are given something we cannot wrap
         */
        else
        {
            itemStack = null;
            oreStack = null;
            stackSize = -1;
        }
    }

    /**
     * Returns the stack size of the wrapped stack, or -1 if we wrapped an invalid input
     * 
     * @return The size of the wrapped stack
     */
    public int getStackSize()
    {
        return stackSize;
    }

    /**
     * Sets the size of the wrapped stack
     * 
     * @param stackSize
     *            The new size of the wrapped stack
     */
    public void setStackSize(int stackSize)
    {
        this.stackSize = stackSize;
    }

    /**
     * Returns the wrapped stack
     * 
     * @return The wrapped ItemStack, OreStack, or EnergyStack, or null if something other than an ItemStack, OreStack, or EnergyStack was used to create this object
     */
    public Object getWrappedStack()
    {
        if (itemStack != null)
        {
            return itemStack;
        } else if (oreStack != null)
        {
            return oreStack;
        }
        return null;
    }

    /**
     * Returns the wrapped item stack
     * 
     * @return The wrapped ItemStack
     */
    public ItemStack getItemStack()
    {
        if (itemStack != null)
        {
            return itemStack;
        } else if (oreStack != null)
        {
            return oreStack.getItemStack();
        }
        return null;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof WrappedStack))
        {
            return false;
        }

        WrappedStack WrappedStack = (WrappedStack) object;

        if ((getWrappedStack() instanceof ItemStack) && (WrappedStack.getWrappedStack() instanceof ItemStack))
        {
            return (ItemHelper.compare(itemStack, WrappedStack.itemStack) && (stackSize == WrappedStack.itemStack.stackSize));
        } else if ((getWrappedStack() instanceof OreStack) && (WrappedStack.getWrappedStack() instanceof OreStack))
        {
            return (oreStack.equals(WrappedStack.getWrappedStack()) && (stackSize == WrappedStack.stackSize));
        }
        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (itemStack != null)
        {
            try
            {
                stringBuilder.append(String.format("%sxitemStack[%s:%s:%s:%s]", stackSize, itemStack.itemID, itemStack.getItemDamage(), itemStack.getUnlocalizedName(), itemStack
                        .getItem().getClass().getCanonicalName()));
            } catch (ArrayIndexOutOfBoundsException e)
            {

            }
        } else if (oreStack != null)
        {
            stringBuilder.append(String.format("%dxoreDictionary.%s", stackSize, oreStack.oreName));
        } else
        {
            stringBuilder.append("null");
        }

        return stringBuilder.toString();
    }

    @Override
    public int hashCode()
    {
        int hashCode = 1;

        hashCode = (37 * hashCode) + stackSize;

        if (itemStack != null)
        {
            hashCode = (37 * hashCode) + itemStack.itemID;
            hashCode = (37 * hashCode) + itemStack.getItemDamage();
        } else if (oreStack != null)
        {
            if (oreStack.oreName != null)
            {
                hashCode = (37 * hashCode) + oreStack.oreName.hashCode();
            }
        }
        return hashCode;
    }

    public static boolean canBeWrapped(Object object)
    {
        return (object instanceof WrappedStack) || (object instanceof ItemStack) || (object instanceof OreStack) || (object instanceof Item) || (object instanceof Block);
    }

    /*
     * Sort order (class-wise) goes null, OreStack, ItemStack
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(WrappedStack WrappedStack)
    {
        if (getWrappedStack() instanceof OreStack)
        {
            if (WrappedStack.getWrappedStack() instanceof OreStack)
            {
                if (oreStack.equals(WrappedStack.oreStack))
                {
                    return stackSize - WrappedStack.stackSize;
                }
                return oreStack.compareTo(WrappedStack.oreStack);
            } else if (WrappedStack.getWrappedStack() instanceof ItemStack)
            {
                return -1;
            } else
            {
                return 1;
            }
        } else if (getWrappedStack() instanceof ItemStack)
        {
            if (WrappedStack.getWrappedStack() instanceof OreStack)
            {
                return 1;
            } else if (WrappedStack.getWrappedStack() instanceof ItemStack)
            {
                if (ItemHelper.compare(itemStack, WrappedStack.itemStack))
                {
                    return stackSize - WrappedStack.stackSize;
                }
                return ItemHelper.compareInt(itemStack, WrappedStack.itemStack);
            } else
            {
                return 1;
            }
        } else
        {
            if (WrappedStack.getWrappedStack() != null)
            {
                return -1;
            }
            return 0;
        }
    }

    private OreStack getOreStackFromList(ArrayList<?> objectList)
    {
        for (Object listElement : objectList)
        {
            if (listElement instanceof ItemStack)
            {
                ItemStack stack = (ItemStack) listElement;

                if (OreDictionary.getOreID(stack) != ORE_DICTIONARY_NOT_FOUND)
                {
                    return new OreStack(stack);
                }
            }
        }

        return null;
    }
}