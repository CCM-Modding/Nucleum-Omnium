package ccm.nucleum_omnium.block.sub;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import ccm.nucleum_omnium.tileentity.ActiveTE;
import ccm.nucleum_omnium.utils.lib.BlockFacings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SBActiveMachine extends SBWithTile {
    
    public SBActiveMachine(int id, int meta, String iconName, List<BlockFacings> goodSides) {
        super(id, meta, iconName, goodSides);
    }
    
    public SBActiveMachine(int id, int meta, Material material, String iconName, List<BlockFacings> goodSides) {
        super(id, meta, material, iconName, goodSides);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister) {
        for (BlockFacings direction : goodSides) {
            if (direction == BlockFacings.Front) {
                icons[direction.ordinal()] = iconRegister.registerIcon(iconName + direction.name());
                icons[7] = iconRegister.registerIcon(iconName + direction.name() + "On");
            } else {
                icons[direction.ordinal()] = iconRegister.registerIcon(iconName + direction.name());
            }
        }
    }
    
    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side) {
        
        ActiveTE te = (ActiveTE) blockAccess.getBlockTileEntity(x, y, z);
        
        if (te != null) {
            if (goodSides.contains(BlockFacings.Front) && side == te.getOrientation().ordinal()) {
                if (te.getState()) {
                    return icons[7];
                } else {
                    return icons[BlockFacings.Front.ordinal()];
                }
            }
        }
        return getIcon(side, blockAccess.getBlockMetadata(x, y, z));
    }
}