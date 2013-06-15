package ccm.nucleum_omnium.helper.enums;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import ccm.nucleum_omnium.BaseNIClass;
import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.SubBlock;
import ccm.nucleum_omnium.helper.TextureHelper;

public final class EnumBlockHelper extends BaseNIClass {
    
    public static Block createBlock(Enum<? extends IBlockEnum> blockEnum, int blockID,
            String textureLoc, CreativeTabs tab) {
        
        Block temp = new SubBlock(blockID, blockEnum.ordinal(),
                TextureHelper.getTextureFromName(blockEnum.name(), textureLoc))
                .setUnlocalizedName(blockEnum.name()).setCreativeTab(tab).getBlock();
        
        ((IBlockEnum) blockEnum).setBaseBlock(temp);
        
        MainBlock.registerID(((IBlockEnum) blockEnum).getBaseBlock().blockID);
        
        return ((IBlockEnum) blockEnum).getBaseBlock();
    }
}