package ccm.nucleum_omnium.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import ccm.nucleum_omnium.tileentity.TileBase;
import ccm.nucleum_omnium.utils.lib.BlockFacings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SBActiveMachine extends SBMutlyTexture {
    
    private boolean isActive;
    
    public SBActiveMachine(int id, int meta, String iconName, List<BlockFacings> goodSides, boolean active) {
        super(id, meta, iconName, goodSides);
        isActive = active;
    }
    
    public SBActiveMachine(int id, int meta, Material material, String iconName, List<BlockFacings> goodSides, boolean active) {
        super(id, meta, material, iconName, goodSides);
        isActive = active;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister) {
        for (BlockFacings direction : goodSides) {
            if ((isActive) && (direction == BlockFacings.Front)) {
                icons[7] = iconRegister.registerIcon(iconName + direction.name() + "On");
            } else {
                icons[direction.ordinal()] = iconRegister.registerIcon(iconName + direction.name());
            }
        }
    }
    
    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side) {
        
        TileBase te = (TileBase) blockAccess.getBlockTileEntity(x, y, z);
        
        if (goodSides.contains(BlockFacings.Front) && side == te.getOrientation().ordinal()) {
            if (isActive) {
                return icons[7];
            } else {
                return super.getBlockTexture(blockAccess, x, y, z, side);
            }
        } else {
            return super.getBlockTexture(blockAccess, x, y, z, side);
        }
    }
}