/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.sub;

import net.minecraft.block.material.Material;

import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.ModelBlock;
import ccm.nucleum_omnium.block.interfaces.ITextureHelper;
import ccm.nucleum_omnium.block.interfaces.ITileHelper;
import ccm.nucleum_omnium.block.texture.ModelTexture;
import ccm.nucleum_omnium.block.tile.NoTile;

public class SubModelled extends SubBlock
{

    public SubModelled(final Class<? extends MainBlock> block, final int id, final int meta, final Material material, final ITextureHelper texture, final ITileHelper tile)
    {
        super(block, id, meta, material, texture, tile);
    }

    public SubModelled(final Class<? extends MainBlock> block, final int id, final int meta, final String iconName, final ITextureHelper texture, final ITileHelper tile)
    {
        this(block, id, meta, Material.rock, texture, tile);
    }

    public SubModelled(final int id, final int meta)
    {
        this(id, meta, Material.rock);
    }

    public SubModelled(final int id, final int meta, final ITextureHelper texture, final ITileHelper tile)
    {
        this(id, meta, Material.rock, texture, tile);
    }

    public SubModelled(final int id, final int meta, final ITileHelper tile)
    {
        this(id, meta, Material.rock, tile);
    }

    public SubModelled(final int id, final int meta, final Material material)
    {
        this(id, meta, material, new NoTile());
    }

    public SubModelled(final int id, final int meta, final Material material, final ITextureHelper texture, final ITileHelper tile)
    {
        this(ModelBlock.class, id, meta, material, texture, tile);
    }

    public SubModelled(final int id, final int meta, final Material material, final ITileHelper tile)
    {
        this(id, meta, material, new ModelTexture(), tile);
    }
}