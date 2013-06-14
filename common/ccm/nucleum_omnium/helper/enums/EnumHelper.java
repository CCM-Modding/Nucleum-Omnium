package ccm.nucleum_omnium.helper.enums;

import net.minecraft.item.ItemStack;
import ccm.nucleum_omnium.BaseNIClass;

/**
 * This is The class used to get a new {@link ItemStack} instance from any
 * {@link Enum} implementing {@link IItemEnum} or {@link IBlockEnum}
 * 
 * @author Captain_Shadows
 */
public final class EnumHelper extends BaseNIClass {
    
    /**
     * @param itemEnum
     *            The Enum constant who's Enum extends {@link IItemEnum} to get
     *            the instance from
     * @return a new {@link ItemStack} instance
     */
    public static ItemStack getItemIS(final Enum<? extends IItemEnum> itemEnum) {
        return new ItemStack(((IItemEnum) itemEnum).getBaseItem().itemID, 1, itemEnum.ordinal());
    }
    
    /**
     * @param itemEnum
     *            The Enum constant who's Enum extends {@link IItemEnum} to get
     *            the instance from
     * @param amount
     *            The amount of items that the ItemStack instance should contain
     * @return a new {@link ItemStack} instance
     */
    public static ItemStack getItemIS(final Enum<? extends IItemEnum> itemEnum, final int amount) {
        return new ItemStack(((IItemEnum) itemEnum).getBaseItem().itemID, amount,
                itemEnum.ordinal());
    }
    
    /**
     * @param blockEnum
     *            The Enum constant who's Enum extends {@link IBlockEnum} to get
     *            the instance from
     * @return a new {@link ItemStack} instance
     */
    public static ItemStack getBlockIS(final Enum<? extends IBlockEnum> blockEnum) {
        return new ItemStack(((IBlockEnum) blockEnum).getBaseBlock().blockID, 1,
                blockEnum.ordinal());
    }
    
    /**
     * @param blockEnum
     *            The Enum constant who's Enum extends {@link IBlockEnum} to get
     *            the instance from
     * @param amount
     *            The amount of items that the ItemStack instance should contain
     * @return a new {@link ItemStack} instance
     */
    public static ItemStack getBlockIS(final Enum<? extends IBlockEnum> blockEnum, final int amount) {
        return new ItemStack(((IBlockEnum) blockEnum).getBaseBlock().blockID, amount,
                blockEnum.ordinal());
    }
}