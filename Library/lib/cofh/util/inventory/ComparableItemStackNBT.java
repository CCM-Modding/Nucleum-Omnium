package lib.cofh.util.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Extension of {@link ComparableItemStack} except NBT sensitive.
 * It is expected that this will have limited use, so this is a child class for overhead performance
 * reasons.
 * 
 * @author King Lemming
 */
public class ComparableItemStackNBT extends ComparableItemStack
{

    public NBTTagCompound tag;

    public ComparableItemStackNBT(final ItemStack stack)
    {

        super(stack);

        if (stack != null){
            if (stack.stackTagCompound != null){
                this.tag = (NBTTagCompound) stack.stackTagCompound.copy();
            }
        }
    }

    @Override
    public boolean isStackEqual(final ComparableItemStack other)
    {

        return super.isStackEqual(other) && this.isStackTagEqual((ComparableItemStackNBT) other);
    }

    private boolean isStackTagEqual(final ComparableItemStackNBT other)
    {

        return this.tag == null ? other.tag == null : other.tag == null ? false : this.tag.equals(other.tag);
    }

    @Override
    public ItemStack toItemStack()
    {

        if (this.tag == null){
            return super.toItemStack();
        }
        if ((this.itemID < 0) || (this.itemID >= 32000)){
            return null;
        }
        final ItemStack ret = new ItemStack(this.itemID, this.stackSize, this.metadata);
        ret.stackTagCompound = (NBTTagCompound) this.tag.copy();

        return ret;
    }

}
