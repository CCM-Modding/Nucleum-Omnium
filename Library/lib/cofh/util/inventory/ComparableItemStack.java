package lib.cofh.util.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class allows for OreDictionary-compatible ItemStack comparisons.
 * The intended purpose of this is for things such as Recipe Handlers or HashMaps of ItemStacks.
 * 
 * @author King Lemming
 */
public class ComparableItemStack
{

    public int itemID    = -1;

    public int metadata  = -1;

    public int stackSize = -1;

    public int oreID     = -1;

    public ComparableItemStack(final ItemStack stack)
    {

        if (stack != null){
            this.itemID = stack.itemID;
            this.metadata = stack.getItemDamage();
            this.stackSize = stack.stackSize;
            this.oreID = OreDictionary.getOreID(stack);
        }
    }

    public ComparableItemStack(final int itemID,
                               final int damage,
                               final int stackSize)
    {

        this.itemID = itemID;
        this.metadata = damage;
        this.stackSize = stackSize;
        this.oreID = OreDictionary.getOreID(this.toItemStack());
    }

    public ComparableItemStack(final ComparableItemStack stack)
    {

        this.itemID = stack.itemID;
        this.metadata = stack.metadata;
        this.stackSize = stack.stackSize;
        this.oreID = stack.oreID;
    }

    public ComparableItemStack(final String oreName)
    {

        if (!OreDictionary.getOres(oreName).isEmpty()){
            final ItemStack ore = OreDictionary.getOres(oreName).get(0);
            this.itemID = ore.itemID;
            this.metadata = ore.getItemDamage();
            this.stackSize = 1;
            this.oreID = OreDictionary.getOreID(oreName);
        }
    }

    public boolean isItemEqual(final ComparableItemStack other)
    {

        return (other != null) && (((this.oreID != -1) && (this.oreID == other.oreID)) || ((this.itemID == other.itemID) && (this.metadata == other.metadata)));
    }

    public boolean isStackEqual(final ComparableItemStack other)
    {

        return this.isItemEqual(other) && (this.stackSize == other.stackSize);
    }

    public boolean isStackValid()
    {

        return this.getItem() != null;
    }

    public Item getItem()
    {

        return (this.itemID < 0) || (this.itemID >= 32000) ? null : Item.itemsList[this.itemID];
    }

    public ItemStack toItemStack()
    {

        return (this.itemID < 0) || (this.itemID >= 32000) ? null : new ItemStack(this.itemID, this.stackSize, this.metadata);
    }

    @Override
    public ComparableItemStack clone()
    {

        return new ComparableItemStack(this);
    }

    @Override
    public int hashCode()
    {

        return this.oreID != -1 ? this.oreID : this.metadata | (this.itemID << 16);
    }

    @Override
    public boolean equals(final Object o)
    {

        if (!(o instanceof ComparableItemStack)){
            return false;
        }
        return this.isItemEqual((ComparableItemStack) o);
    }

}
