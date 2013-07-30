package ccm.nucleum_omnium.block.sub;

import net.minecraft.block.material.Material;

import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.ModelBlock;
import ccm.nucleum_omnium.block.interfaces.ITextureHelper;
import ccm.nucleum_omnium.block.texture.ModelTexture;

public class SubModelled extends SubBlock {

    public SubModelled(final Class<? extends MainBlock> block,
                       final int id,
                       final int meta,
                       final Material material,
                       final ITextureHelper texture) {
        super(block, id, meta, material, texture);
    }

    public SubModelled(final Class<? extends MainBlock> block,
                       final int id,
                       final int meta,
                       final ITextureHelper texture) {
        this(block, id, meta, Material.rock, texture);
    }

    public SubModelled(final int id, final int meta, final Material material) {
        this(ModelBlock.class, id, meta, material, new ModelTexture());
    }

    public SubModelled(final int id, final int meta) {
        this(ModelBlock.class, id, meta, new ModelTexture());
    }
}