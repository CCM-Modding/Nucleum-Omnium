/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.loader.texture;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import ccm.nucleum.omnium.block.interfaces.ITextureHelper;

public class ModelTexture implements ITextureHelper
{

    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side)
    {
        return null;
    }

    @Override
    public Icon getIcon(final int side, final int meta)
    {
        return null;
    }

    @Override
    public void registerIcons(final IconRegister register)
    {}

}
