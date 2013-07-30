package ccm.nucleum_omnium.block.texture;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import ccm.nucleum_omnium.block.interfaces.ITextureHelper;

public class ModelTexture implements ITextureHelper {

    @Override
    public void registerIcons(final IconRegister register) {}

    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess,
                                final int x,
                                final int y,
                                final int z,
                                final int side) {
        return null;
    }

    @Override
    public Icon getIcon(final int side, final int meta) {
        return null;
    }

}
