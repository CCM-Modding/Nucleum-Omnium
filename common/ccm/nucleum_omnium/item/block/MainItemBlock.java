package ccm.nucleum_omnium.item.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ccm.nucleum_omnium.helper.BlockHelper;
import ccm.nucleum_omnium.helper.LanguageHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MainItemBlock extends ItemBlock {

	public MainItemBlock(final int id) {
		super(id);
		setHasSubtypes(true);
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return BlockHelper.getBlock(getBlockID()).getCreativeTabArray();
	}

	@Override
	public String getItemDisplayName(final ItemStack itemStack) {
		return LanguageHelper.getLocalizedString(getUnlocalizedName(itemStack));
	}

	@Override
	public int getMetadata(final int metadata) {
		return metadata;
	}

	/**
	 * Gets an icon index based on an item's damage value
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(final int meta) {
		return BlockHelper.getBlock(getBlockID()).getIcon(0, meta);
	}

	@Override
	public String getUnlocalizedName(final ItemStack itemstack) {
		return BlockHelper.getSubBlock(getBlockID(), itemstack).getUnlocalizedName();
	}
}