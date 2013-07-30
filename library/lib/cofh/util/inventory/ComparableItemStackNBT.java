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
public class ComparableItemStackNBT extends ComparableItemStack {

    public NBTTagCompound tag;

    public ComparableItemStackNBT(final ItemStack stack) {

        super(stack);

        if (stack != null) {
            if (stack.stackTagCompound != null) {
                tag = (NBTTagCompound) stack.stackTagCompound.copy();
            }
        }
    }

    @Override
    public boolean isStackEqual(final ComparableItemStack other) {

        return super.isStackEqual(other) && isStackTagEqual((ComparableItemStackNBT) other);
    }

    private boolean isStackTagEqual(final ComparableItemStackNBT other) {

        return tag == null ? other.tag == null : other.tag == null ? false : tag.equals(other.tag);
    }

    @Override
    public ItemStack toItemStack() {

        if (tag == null) {
            return super.toItemStack();
        }
        if ((itemID < 0) || (itemID >= 32000)) {
            return null;
        }
        final ItemStack ret = new ItemStack(itemID, stackSize, metadata);
        ret.stackTagCompound = (NBTTagCompound) tag.copy();

        return ret;
    }

}
