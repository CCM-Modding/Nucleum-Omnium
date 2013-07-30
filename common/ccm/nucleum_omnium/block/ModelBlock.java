package ccm.nucleum_omnium.block;

import net.minecraft.block.material.Material;

public class ModelBlock extends MainBlock {

    public ModelBlock(final int id, final Material material) {
        super(id, material);
    }

    public ModelBlock(final int id) {
        this(id, Material.rock);
    }

    @Override
    public boolean renderAsNormalBlock() {

        return false;
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public int getRenderType() {

        return -1;
    }
}