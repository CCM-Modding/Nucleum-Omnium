/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.interfaces;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * ITextureHelper
 * <p>
 * Does all the texture related things for the SubBlock
 * 
 * @author Captain_Shadows
 */
public interface ITextureHelper
{

    /**
     * @return The block's Icon
     */
    public Icon getBlockTexture(final IBlockAccess world,
                                final int x,
                                final int y,
                                final int z,
                                final int side);

    /**
     * @return The block's Icon
     */
    public Icon getIcon(final int side, final int meta);

    /**
     * Registers all the Icons to be used by the Block(or SubBlock :P)
     */
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister register);
}