package ccm.nucleum_omnium.helper.enums;

import net.minecraft.item.ItemStack;

import ccm.nucleum_omnium.helper.BaseHelper;

public final class EnumHelper extends BaseHelper
{

    public static ItemStack getItemIS(final Enum<? extends IItemEnum> enumOrdinal)
    {

        return new ItemStack(((IItemEnum) enumOrdinal).getBaseItem().itemID, 1, enumOrdinal.ordinal());
    }

    public static ItemStack getItemIS(final Enum<? extends IItemEnum> enumOrdinal, final int amount)
    {
        return new ItemStack(((IItemEnum) enumOrdinal).getBaseItem().itemID, amount, enumOrdinal.ordinal());
    }

    public static ItemStack getBlockIS(final Enum<? extends IBlockEnum> enumOrdinal)
    {
        return new ItemStack(((IBlockEnum) enumOrdinal).getBaseBlock().blockID, 1, enumOrdinal.ordinal());
    }

    public static ItemStack getBlockIS(final Enum<? extends IBlockEnum> enumOrdinal, final int amount)
    {
        return new ItemStack(((IBlockEnum) enumOrdinal).getBaseBlock().blockID, amount, enumOrdinal.ordinal());
    }
}