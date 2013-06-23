package ccm.nucleum_omnium.block.sub;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.tileentity.BaseTE;
import ccm.nucleum_omnium.utils.lib.BlockFacings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SBMutlyTexture extends SubBlock {
    
    protected List<BlockFacings> goodSides = new ArrayList<BlockFacings>();
    protected Icon[]             icons     = new Icon[8];
    
    public SBMutlyTexture(int id, int meta, String iconName, List<BlockFacings> goodSides) {
        super(id, meta, iconName);
        this.goodSides = goodSides;
    }
    
    public SBMutlyTexture(final int id, final int meta, final Material material, final String iconName, List<BlockFacings> goodSides) {
        super(id, meta, material, iconName);
        this.goodSides = goodSides;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister) {
        for (BlockFacings direction : goodSides) {
            Handler.log(iconName + direction.name());
            icons[direction.ordinal()] = iconRegister.registerIcon(iconName + direction.name());
        }
    }
    
    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side) {
        
        BaseTE te = (BaseTE) blockAccess.getBlockTileEntity(x, y, z);
        
        if (te != null) {
            if (goodSides.contains(BlockFacings.Front) && side == te.getOrientation().ordinal()) {
                return icons[BlockFacings.Front.ordinal()];
            }
        }
        return getIcon(side, blockAccess.getBlockMetadata(x, y, z));
    }
    
    @Override
    public Icon getIcon(int side, int meta) {
        if (goodSides.contains(BlockFacings.Top) && side == ForgeDirection.UP.ordinal()) {
            return icons[BlockFacings.Top.ordinal()];
        } else if (goodSides.contains(BlockFacings.Bottom) && side == ForgeDirection.DOWN.ordinal()) {
            return icons[BlockFacings.Bottom.ordinal()];
        } else if (goodSides.contains(BlockFacings.Sides)) {
            return icons[BlockFacings.Sides.ordinal()];
        } else {
            throw new RuntimeException("A CCM Member has derped, To the member: DO NOT FORGET TO ADD THINGS TO THE goodSides ARRAY!!!");
        }
    }
}