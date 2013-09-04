/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper.item;

import java.util.ArrayList;
import java.util.Comparator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * OreStack
 * <p>
 * 
 * @author pahimar
 */
public final class OreStack implements Comparator<OreStack>
{
    /** The name of the ore */
    private final String name;

    /** The amount */
    private int amount;

    public OreStack()
    {
        name = "Unknown";
        amount = 0;
    }

    public OreStack(final String name, final int stackSize)
    {
        this.name = name;
        amount = stackSize;
    }

    public OreStack(final String name)
    {
        this(name, 1);
    }

    public OreStack(final int oreID)
    {
        this(OreDictionary.getOreName(oreID));
    }

    public OreStack(final int oreID, final int stackSize)
    {
        this(OreDictionary.getOreName(oreID), stackSize);
    }

    public OreStack(final ItemStack itemStack)
    {
        this(OreDictionary.getOreID(itemStack), itemStack.stackSize);
    }

    public String name()
    {
        return name;
    }

    public int size()
    {
        return amount;
    }

    public void setSize(final int size)
    {
        amount = size;
    }

    public boolean isOre()
    {
        return OreDictionaryHelper.containsOre(name);
    }

    public ArrayList<ItemStack> getOres()
    {
        return OreDictionary.getOres(name);
    }

    public int getOreID()
    {
        return OreDictionary.getOreID(name);
    }
    
    public ItemStack toItemStack()
    {
        return getOres().get(0);
    }

    @Override
    public int compare(final OreStack stack, final OreStack stack2)
    {
        if ((stack != null) && (stack2 != null))
        {
            if (stack.name.equals(stack2.name))
            {
                return 0;
            }
        }
        return -1;
    }

    public static boolean compareStacks(final OreStack stack, final OreStack stack2)
    {
        return stack.compare(stack2);
    }

    public boolean compare(final OreStack stack)
    {
        return compare(this, stack) == 0;
    }
}