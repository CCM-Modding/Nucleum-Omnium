package ccm.nucleum_omnium.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

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
    
    @Override
    public String getUnlocalizedName(final ItemStack itemstack) {
        
        return ((MainBlock) Block.blocksList[getBlockID()]).getSubBlocks()[itemstack.getItemDamage()].getUnlocalizedName();
    }
}