package ccm.nucleum_omnium.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

import ccm.nucleum_omnium.blocks.BaseBlock;

/**
 * WIP(Work In Progress) DO NOT USE!
 */
public class ItemBaseBlock extends ItemBlock
{

    public ItemBaseBlock(final int id)
    {
        super(id);
        this.setHasSubtypes(true);
    }

    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        return ((BaseBlock) Block.blocksList[this.getBlockID()]).getCreativeTabArray();
    }

    @Override
    public String getItemDisplayName(final ItemStack itemStack)
    {
        return ("" + StringTranslate.getInstance().translateNamedKey(this.getLocalizedName(itemStack))).trim();
    }

    @Override
    public int getMetadata(final int metadata)
    {
        return metadata;
    }

    @Override
    public String getUnlocalizedName(final ItemStack itemstack)
    {
        final int meta = itemstack.getItemDamage();
        return this.getUnlocalizedName() + "." + meta;
    }
}