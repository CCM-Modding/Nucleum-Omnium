/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * WrapperStack
 * <p>
 * 
 * @author pahimar
 */
public final class WrapperStack implements Comparable<WrapperStack>
{
    public static final int ORE_DICTIONARY_NOT_FOUND = -1;

    private int stackSize;
    private ItemStack itemStack;
    private OreStack oreStack;

    /**
     * Creates a new WrapperStack object which wraps the given input. Valid inputs would be ItemStacks or OreStacks. If something other than an ItemStack or an OreStack is used as
     * input, nothing is wrapped and the size of the wrapped stack is set to -1 to indicate an invalid wrapped stack.
     * 
     * @param object
     *            The newly created wrapped stack object
     */
    public WrapperStack(Object object)
    {
        if (canBeWrapped(object))
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
                final ItemStack itemStack = (ItemStack) object;
                /*
                 * If the ItemStack does not exist in the OreDictionary, wrap it as an ItemStack
                 */
                if (OreDictionary.getOreID(itemStack) == ORE_DICTIONARY_NOT_FOUND)
                {
                    this.itemStack = itemStack.copy();
                    oreStack = null;
                    stackSize = this.itemStack.stackSize;
                    this.itemStack.stackSize = 1;
                }
                /*
                 * Else the ItemStack exists in the OreDictionary, so wrap it as an OreStack instead of an ItemStack
                 */
                else
                {
                    this.itemStack = null;
                    oreStack = new OreStack(itemStack);
                    stackSize = oreStack.size();
                    oreStack.setSize(1);
                }
            }
            /*
             * Or we are given an OreStack to wrap
             */
            else if (object instanceof OreStack)
            {
                itemStack = null;
                oreStack = (OreStack) object;
                stackSize = oreStack.size();
                oreStack.setSize(1);
            } else if (object instanceof ArrayList)
            {
                itemStack = null;

                final ArrayList<?> objectList = (ArrayList<?>) object;

                if (!objectList.isEmpty())
                {
                    for (final Object listElement : objectList)
                    {
                        if (listElement instanceof ItemStack)
                        {
                            final ItemStack stack = (ItemStack) listElement;

                            if (OreDictionary.getOreID(stack) != ORE_DICTIONARY_NOT_FOUND)
                            {
                                oreStack = new OreStack(stack);
                                stackSize = oreStack.size();
                                oreStack.setSize(1);
                                break;
                            }
                        }
                    }
                }
            } else if (object instanceof WrapperStack)
            {
                final WrapperStack wrappedStack = (WrapperStack) object;

                itemStack = wrappedStack.itemStack;
                oreStack = wrappedStack.oreStack;
                stackSize = wrappedStack.stackSize;
            }
        }
        /*
         * Else, we are given something we cannot wrap
         */
        else
        {
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
    public void setStackSize(final int stackSize)
    {
        this.stackSize = stackSize;
    }

    /**
     * Returns the wrapped stack
     * 
     * @return The wrapped ItemStack, OreStack, or null if something other than an ItemStack, or OreStack, was used to create this object
     */
    public ItemStack getWrappedStack()
    {
        if (itemStack != null)
        {
            return itemStack;
        } else if (oreStack != null)
        {
            return oreStack.toItemStack();
        }
        return null;
    }
    
    public static boolean canBeWrapped(final Object object)
    {
        return ((object instanceof WrapperStack) || (object instanceof ItemStack) || (object instanceof OreStack) || (object instanceof Item) || (object instanceof Block));
    }
    
    public static List<WrapperStack> toWrapperList(List<Object> list)
    {
        List<WrapperStack> tmp = new ArrayList<WrapperStack>();
        for (Object obj : list)
        {
            if (canBeWrapped(obj))
            {
                tmp.add(new WrapperStack(obj));
            }
        }
        return tmp;
    }

    @Override
    public int compareTo(WrapperStack o)
    {
        if (itemStack != null && o.itemStack != null)
        {
            if (ItemStack.areItemStacksEqual(itemStack, o.itemStack))
            {
                if (itemStack.hasTagCompound() && o.itemStack.hasTagCompound())
                {
                    if (!ItemStack.areItemStackTagsEqual(itemStack, o.itemStack))
                    {
                        return -1;
                    }
                }
                if (itemStack.stackSize == o.itemStack.stackSize)
                {
                    return 0;
                } else if (itemStack.stackSize > o.itemStack.stackSize)
                {
                    return 1;
                }
            }
        } else if (oreStack != null && o.oreStack != null)
        {

        }
        return -1;
    }

    public static int compare(Object o, Object o2)
    {
        if (o != null && o2 != null)
        {
            return new WrapperStack(o).compareTo(new WrapperStack(o2));
        }
        return -1;
    }
}