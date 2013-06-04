package ccm.nucleum_omnium.helper.enums;

import net.minecraft.item.ItemStack;

import ccm.nucleum_omnium.helper.BaseHelper;

public final class EnumHelper extends BaseHelper
{

    public static ItemStack getItemIS(final Enum<? extends IItemEnum> enumType)
    {
        return new ItemStack(((IItemEnum) enumType).getBaseItem(), 1, enumType.ordinal());
    }

    public static ItemStack getItemIS(final Enum<? extends IItemEnum> enumType, final int amount)
    {
        return new ItemStack(((IItemEnum) enumType).getBaseItem(), amount, enumType.ordinal());
    }

    public static ItemStack getBlockIS(final Enum<? extends IBlockEnum> enumType)
    {
        return new ItemStack(((IBlockEnum) enumType).getBaseBlock(), 1, enumType.ordinal());
    }

    public static ItemStack getBlockIS(final Enum<? extends IBlockEnum> enumType, final int amount)
    {
        return new ItemStack(((IBlockEnum) enumType).getBaseBlock(), amount, enumType.ordinal());
    }
}