package ccm.nucleum_omnium.block.texture;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import ccm.nucleum_omnium.block.interfaces.ITextureHelper;
import ccm.nucleum_omnium.tileentity.ActiveTE;
import ccm.nucleum_omnium.utils.lib.BlockFacings;

/**
 * @author Captain_Shadows
 */
public class ActiveTexture extends MultyTexture implements ITextureHelper {

    public ActiveTexture(final String iconName, final List<BlockFacings> goodSides) {
        super(iconName, goodSides);
    }

    @Override
    public void registerIcons(final IconRegister register) {
        for (final BlockFacings direction : goodSides) {
            if (direction == BlockFacings.Front) {
                icons[direction.ordinal()] = register.registerIcon(iconName + direction.name());
                icons[7] = register.registerIcon(iconName + direction.name() + "On");
            } else {
                icons[direction.ordinal()] = register.registerIcon(iconName + direction.name());
            }
        }
    }

    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess,
                                final int x,
                                final int y,
                                final int z,
                                final int side) {

        final ActiveTE te = (ActiveTE) blockAccess.getBlockTileEntity(x, y, z);

        if (te != null) {
            if (goodSides.contains(BlockFacings.Front) && (side == te.getOrientationOrdinal())) {
                if (te.getState()) {
                    return icons[7];
                } else {
                    return icons[BlockFacings.Front.ordinal()];
                }
            }
        }
        return super.getBlockTexture(blockAccess, x, y, z, side);
    }
}