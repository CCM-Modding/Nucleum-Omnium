package ccm.nucleum_omnium.helper.enums;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import ccm.nucleum_omnium.helper.BaseHelper;

public final class EnumHelper extends BaseHelper
{

    public static ItemStack getItemIS(final Item type, final Enum<? extends IEnum> enumType)
    {
        return new ItemStack(type, 1, enumType.ordinal());
    }

    public static ItemStack getItemIS(final Item type, final Enum<? extends IEnum> enumType, final int amount)
    {
        return new ItemStack(type, amount, enumType.ordinal());
    }

    public static ItemStack getBlockIS(final Block type, final Enum<? extends IEnum> enumType)
    {
        return new ItemStack(type, 1, enumType.ordinal());
    }

    public static ItemStack getBlockIS(final Block type, final Enum<? extends IEnum> enumType, final int amount)
    {
        return new ItemStack(type, amount, enumType.ordinal());
    }
}