package ccm.nucleum_omnium.item.block;

import ccm.nucleum_omnium.block.MainBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StringTranslate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MainItemBlock extends ItemBlock {
    
    public MainItemBlock(final int id) {
        super(id);
        setHasSubtypes(true);
    }
    
    @Override
    public CreativeTabs[] getCreativeTabs() {
        return ((MainBlock) Block.blocksList[getBlockID()]).getCreativeTabArray();
    }
    
    @Override
    public String getItemDisplayName(final ItemStack itemStack) {
        return StringTranslate.getInstance().translateNamedKey(this.getUnlocalizedName(itemStack));
    }
    
    @Override
    public int getMetadata(final int metadata) {
        return metadata;
    }
    
    /**
     * Gets an icon index based on an item's damage value
     */
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta) {
        return Block.blocksList[getBlockID()].getIcon(0, meta);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemstack) {
        
        return ((MainBlock) Block.blocksList[getBlockID()]).getSubBlocks()[itemstack.getItemDamage()].getUnlocalizedName();
    }
}