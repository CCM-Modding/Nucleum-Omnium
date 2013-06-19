package ccm.nucleum_omnium.helper.enums;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import ccm.nucleum_omnium.BaseNIClass;
import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.SubBlock;
import ccm.nucleum_omnium.helper.TextureHelper;

public final class EnumBlockHelper extends BaseNIClass {
    
    public static Block createBlock(final Enum<? extends IBlockEnum> blockEnum, final SubBlock subBlock) {
        
        ((IBlockEnum) blockEnum).setBaseBlock(subBlock.getBlock());
        
        MainBlock.registerID(((IBlockEnum) blockEnum).getBaseBlock().blockID);
        
        return ((IBlockEnum) blockEnum).getBaseBlock();
    }
    
    public static Block createBlock(final Enum<? extends IBlockEnum> blockEnum, final int blockID, final String textureLoc) {
        
        ((IBlockEnum) blockEnum).setBaseBlock(new SubBlock(blockID, blockEnum.ordinal(), TextureHelper.getTextureFromName(blockEnum.name(), textureLoc)).setUnlocalizedName(blockEnum.name())
                                                                                                                                                        .getBlock());
        
        MainBlock.registerID(((IBlockEnum) blockEnum).getBaseBlock().blockID);
        
        return ((IBlockEnum) blockEnum).getBaseBlock();
    }
    
    public static Block createBlock(final Enum<? extends IBlockEnum> blockEnum, final int blockID, final String textureLoc, final CreativeTabs tab) {
        
        ((IBlockEnum) blockEnum).setBaseBlock(new SubBlock(blockID, blockEnum.ordinal(), TextureHelper.getTextureFromName(blockEnum.name(), textureLoc)).setUnlocalizedName(blockEnum.name())
                                                                                                                                                        .setCreativeTab(tab)
                                                                                                                                                        .getBlock());
        
        MainBlock.registerID(((IBlockEnum) blockEnum).getBaseBlock().blockID);
        
        return ((IBlockEnum) blockEnum).getBaseBlock();
    }
    
    public static Block createBlock(final Enum<? extends IBlockEnum> blockEnum, final int blockID, final String textureLoc, final CreativeTabs tab, final float hardness) {
        
        ((IBlockEnum) blockEnum).setBaseBlock(new SubBlock(blockID, blockEnum.ordinal(), TextureHelper.getTextureFromName(blockEnum.name(), textureLoc)).setUnlocalizedName(blockEnum.name())
                                                                                                                                                        .setCreativeTab(tab)
                                                                                                                                                        .setHardness(hardness)
                                                                                                                                                        .getBlock());
        
        MainBlock.registerID(((IBlockEnum) blockEnum).getBaseBlock().blockID);
        
        return ((IBlockEnum) blockEnum).getBaseBlock();
    }
}