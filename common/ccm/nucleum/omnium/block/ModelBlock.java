/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block;

import net.minecraft.block.material.Material;

/**
 * ModelBlock
 * <p>
 * Modeled version of MainBlock
 * 
 * @author Captain_Shadows
 */
public class ModelBlock extends BaseBlock
{

    public ModelBlock(final int id)
    {
        this(id, Material.rock);
    }

    public ModelBlock(final int id, final Material material)
    {
        super(id, material);
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}