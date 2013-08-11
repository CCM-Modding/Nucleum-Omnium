/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.texture;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import ccm.nucleum_omnium.block.interfaces.ITextureHelper;

public class BasicTexture implements ITextureHelper
{

    protected String iconName;
    protected Icon   icon;

    public BasicTexture(final String iconName)
    {
        this.iconName = iconName;
    }

    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess,
                                final int x,
                                final int y,
                                final int z,
                                final int side)
    {
        return icon;
    }

    @Override
    public Icon getIcon(final int side, final int meta)
    {
        return icon;
    }

    @Override
    public void registerIcons(final IconRegister register)
    {
        icon = register.registerIcon(iconName);
    }
}