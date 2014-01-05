/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper.item;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

// REDO
public class OreStack implements Comparable<OreStack>
{
    public int oreId;
    public String oreName;
    public int stackSize;

    public OreStack(String oreName, int stackSize)
    {
        if ((oreName != null) && (oreName.length() > 0))
        {
            if (this.oreName == null)
            {
                for (String oreDictionaryName : OreDictionary.getOreNames())
                {
                    if (oreDictionaryName.equalsIgnoreCase(oreName))
                    {
                        oreId = OreDictionary.getOreID(oreDictionaryName);
                        this.oreName = oreDictionaryName;
                    }
                }
            }
        } else
        {
            oreId = -1;
            this.oreName = OreDictionary.getOreName(oreId);
        }
        this.stackSize = stackSize;
    }

    public OreStack(String oreName)
    {
        this(oreName, 1);
    }

    public OreStack(int oreId, int stackSize)
    {
        this(OreDictionary.getOreName(oreId), stackSize);
    }

    public OreStack(ItemStack itemStack)
    {
        this(OreDictionary.getOreID(itemStack), itemStack.stackSize);
    }

    public OreStack(int oreId)
    {
        this(oreId, 1);
    }

    public ArrayList<ItemStack> getOres()
    {
        if (oreId != -1)
        {
            return OreDictionary.getOres(oreId);
        }
        return new ArrayList<ItemStack>();
    }

    public ItemStack getItemStack()
    {
        return OreDictionary.getOres(oreId).get(0);
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%dxoreDictionary.%s[oreId:%s]", stackSize, oreName, oreId));
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof OreStack))
        {
            return false;
        }
        OreStack oreStackObject = (OreStack) object;
        return (compareTo(oreStackObject) == 0);
    }

    public static boolean compareOreNames(OreStack oreStack1, OreStack oreStack2)
    {
        if ((oreStack1 != null) && (oreStack2 != null))
        {
            if ((oreStack1.oreName != null) && (oreStack2.oreName != null))
            {
                return oreStack1.oreName.equalsIgnoreCase(oreStack2.oreName);
            }
        }
        return false;
    }

    @Override
    public int compareTo(OreStack oreStack)
    {
        if (oreStack != null)
        {
            if (oreId != oreStack.oreId)
            {
                if (oreId > oreStack.oreId)
                {
                    return 1;
                }
                return -1;
            }
            if ((oreName != null) && (oreStack.oreName != null))
            {
                if (oreName.equalsIgnoreCase(oreStack.oreName))
                {
                    return (stackSize - oreStack.stackSize);
                }
                return oreName.compareToIgnoreCase(oreStack.oreName);
            } else if ((oreName != null) && (oreStack.oreName == null))
            {
                return 1;
            } else if ((oreName == null) && (oreStack.oreName != null))
            {
                return -1;
            } else
            {
                return (stackSize - oreStack.stackSize);
            }
        }
        return 1;
    }
}