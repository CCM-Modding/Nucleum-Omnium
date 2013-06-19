package ccm.nucleum_omnium.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

public class MainItemBlock extends ItemBlock {
    
    public MainItemBlock(int id) {
        super(id);
        setHasSubtypes(true);
    }
    
    public CreativeTabs[] getCreativeTabs() {
        return ((MainBlock) Block.blocksList[this.getBlockID()]).getCreativeTabArray();
    }
    
    @Override
    public String getItemDisplayName(ItemStack itemStack) {
        return StringTranslate.getInstance().translateNamedKey(this.getUnlocalizedName(itemStack));
    }
    
    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        
        return ((MainBlock) Block.blocksList[this.getBlockID()]).getSubBlocks()[itemstack
                .getItemDamage()].getUnlocalizedName();
    }
}