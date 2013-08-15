/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.texture;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import ccm.nucleum.omnium.block.MainBlock;
import ccm.nucleum.omnium.block.sub.SubCrop;

public class CropTexture extends BasicTexture
{

    private final Icon[] icons;
    private final int stages;

    /**
     * @param stages
     *            Amount of stages that this crop should have
     */
    public CropTexture(final String iconName, final int stages)
    {
        super(iconName);
        this.stages = stages;
        icons = new Icon[stages];
    }

    @Override
    public Icon getBlockTexture(final IBlockAccess world, final int x, final int y, final int z, final int side)
    {
        final SubCrop block = (SubCrop) ((MainBlock) Block.blocksList[world.getBlockId(x, y, z)]).getSubBlocks()[world.getBlockMetadata(x, y, z)];
        return icons[block.getCurrentStage()];
    }

    @Override
    public Icon getIcon(final int side, final int meta)
    {
        return icons[stages];
    }

    @Override
    public void registerIcons(final IconRegister register)
    {
        for (int currentStage = 0; currentStage < stages; currentStage++)
        {
            icons[currentStage] = register.registerIcon(iconName + "_" + currentStage);
        }
    }
}
