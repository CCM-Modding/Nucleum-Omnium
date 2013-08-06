/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block.sub;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.interfaces.ITextureHelper;
import ccm.nucleum_omnium.block.sub.tick.CropGrowth;
import ccm.nucleum_omnium.block.texture.CropTexture;
import ccm.nucleum_omnium.block.tile.NoTile;
import ccm.nucleum_omnium.utils.handler.TextureHandler;
import ccm.nucleum_omnium.utils.helper.enums.IBlockEnum;

public class SubCrop extends SubBlock implements IPlantable
{

    /*
     * Static Factory's
     */
    public static SubCrop createAndSetUp(final Enum<? extends IBlockEnum> blockEnum,
                                         final int id,
                                         final String textureLoc,
                                         final int stages,
                                         final float growthRate)
    {

        final String texture = TextureHandler.getTextureFromName(blockEnum.name(), textureLoc);

        final SubBlock block = setUp(blockEnum, new SubCrop(id,
                                                            blockEnum.ordinal(),
                                                            texture,
                                                            stages,
                                                            growthRate).setUnlocalizedName(blockEnum));
        block.addDisplayListener(new CropGrowth());
        return (SubCrop) block;
    }

    /*
     * DATA
     */
    private int         stage;

    private final int   stages;

    private final float growthRate;

    public SubCrop(final Class<? extends MainBlock> block,
                   final int id,
                   final int meta,
                   final ITextureHelper texture,
                   final int stages,
                   final float growthRate)
    {
        this(block, id, meta, Material.plants, texture, stages, growthRate);
    }

    /*
     * Constructors
     */
    /**
     * @param stages
     *            Amount of stages that this crop should have
     */
    public SubCrop(final Class<? extends MainBlock> block,
                   final int id,
                   final int meta,
                   final Material material,
                   final ITextureHelper texture,
                   final int stages,
                   final float growthRate)
    {
        super(block, id, meta, material, texture, new NoTile());
        this.stages = stages;
        this.growthRate = growthRate;
    }

    public SubCrop(final int id,
                   final int meta,
                   final ITextureHelper texture,
                   final int stages,
                   final float growthRate)
    {
        this(id, meta, Material.plants, texture, stages, growthRate);
    }

    public SubCrop(final int id,
                   final int meta,
                   final Material material,
                   final ITextureHelper texture,
                   final int stages,
                   final float growthRate)
    {
        this(MainBlock.class, id, meta, material, texture, stages, growthRate);
    }

    public SubCrop(final int id,
                   final int meta,
                   final Material material,
                   final String iconName,
                   final int stages,
                   final float growthRate)
    {
        this(id, meta, material, new CropTexture(iconName, stages), stages, growthRate);
    }

    public SubCrop(final int id,
                   final int meta,
                   final String iconName,
                   final int stages,
                   final float growthRate)
    {
        this(id, meta, Material.plants, iconName, stages, growthRate);
    }

    /*
     * Methods
     */
    public int getCurrentStage()
    {
        return stage;
    }

    public float getGrowthRate()
    {
        return growthRate;
    }

    @Override
    public int getPlantID(final World world, final int x, final int y, final int z)
    {
        return getBlock().blockID;
    }

    @Override
    public int getPlantMetadata(final World world, final int x, final int y, final int z)
    {
        return getDamageValue(world, x, y, z);
    }

    /*
     * IPlantable
     */
    @Override
    public EnumPlantType getPlantType(final World world, final int x, final int y, final int z)
    {
        return EnumPlantType.Crop;
    }

    public int getTotalStages()
    {
        return stages;
    }

    public void grow()
    {
        if (stage != stages)
        {
            stage += 1;
        }
    }

    public void growToStage(final int stage)
    {
        if (this.stage != stages)
        {
            if ((this.stage + stage) <= stages)
            {
                this.stage = stage;
            }
        }
    }
}