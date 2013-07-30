package ccm.nucleum_omnium.block.interfaces;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface ITextureHelper {

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister register);

    public Icon getBlockTexture(final IBlockAccess blockAccess,
                                final int x,
                                final int y,
                                final int z,
                                final int side);

    public Icon getIcon(final int side, final int meta);
}