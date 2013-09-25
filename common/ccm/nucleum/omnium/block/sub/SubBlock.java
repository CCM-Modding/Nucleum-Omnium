/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.sub;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum.omnium.block.MainBlock;
import ccm.nucleum.omnium.block.interfaces.ICollisionListener;
import ccm.nucleum.omnium.block.interfaces.IDisplayListener;
import ccm.nucleum.omnium.block.interfaces.ITextureHelper;
import ccm.nucleum.omnium.block.interfaces.ITileHelper;
import ccm.nucleum.omnium.block.loader.texture.BasicTexture;
import ccm.nucleum.omnium.block.loader.tile.NoTile;
import ccm.nucleum.omnium.tileentity.BaseTE;
import ccm.nucleum.omnium.utils.handler.gui.GuiHandler;
import ccm.nucleum.omnium.utils.helper.BlockHelper;
import ccm.nucleum.omnium.utils.helper.FunctionHelper;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import ccm.nucleum.omnium.utils.helper.TextureHelper;
import ccm.nucleum.omnium.utils.helper.enums.IBlockEnum;

public class SubBlock
{
    // /////////////////////////////
    // DATA
    // /////////////////////////////
    private MainBlock mainBlock;

    private final int meta;
    private CreativeTabs tab;

    private final ITileHelper tile;
    private final ITextureHelper texture;
    private ItemStack drop;

    private int dropMin;
    private int dropMax;

    private float hardness;

    private float blockResistance;

    private boolean collisionEffect;
    private String unlocName;

    public final List<IDisplayListener> displayList = new ArrayList<IDisplayListener>();

    public final List<ICollisionListener> collisionList = new ArrayList<ICollisionListener>();

    // /////////////////////////////
    // Constructors
    // /////////////////////////////
    /**
     * Creates a new SubBlock instance
     * 
     * @param id
     *            The ID of the MainBlock to use (Put the same for multiple instances if you want them to all correspond to the same block ID)
     * @param meta
     *            The metadata to use (Typically the enum.ordinal())
     * @param material
     *            The Material that the block should be made of
     * @param iconName
     *            The full path of the Icon, ex: harvestry:coal
     */
    public SubBlock(final Class<? extends MainBlock> block, final int id, final int meta, final Material material, final ITextureHelper texture, final ITileHelper tile)
    {
        if (!(Block.blocksList[id] instanceof MainBlock))
        {
            Constructor<? extends MainBlock> c = null;
            try
            {
                c = block.getConstructor(int.class, Material.class);
            } catch (final Exception e)
            {
                CCMLogger.DEFAULT_LOGGER.printCatch(e, "FAILED TO GET CONSTRUCTOR FOR %s", block);
            }
            try
            {
                mainBlock = c.newInstance(id, material);
            } catch (final Exception e)
            {
                CCMLogger.DEFAULT_LOGGER.printCatch(e, "FAILED TO CREATE A NEW INSTANCE OF %s", block);
            }
            mainBlock.addSubBlock(this, meta);
        } else
        {
            mainBlock = (MainBlock) Block.blocksList[id];
            mainBlock.addSubBlock(this, meta);
        }

        this.meta = meta;
        this.texture = texture;
        this.tile = tile;
    }

    public SubBlock(final Class<? extends MainBlock> block, final int id, final int meta, final ITextureHelper texture, final ITileHelper tile)
    {
        this(block, id, meta, Material.rock, texture, tile);
    }

    public SubBlock(final int id, final int meta, final Material material, final ITextureHelper texture, final ITileHelper tile)
    {
        this(MainBlock.class, id, meta, material, texture, tile);
    }

    public SubBlock(final int id, final int meta, final ITextureHelper texture, final ITileHelper tile)
    {
        this(id, meta, Material.rock, texture, tile);
    }

    public SubBlock(final int id, final int meta, final Material material, final String iconName)
    {
        this(id, meta, material, new BasicTexture(iconName), new NoTile());
    }

    public SubBlock(final int id, final int meta, final String iconName)
    {
        this(id, meta, Material.rock, iconName);
    }

    // /////////////////////////////
    // Listeners
    // /////////////////////////////
    /**
     * @param collisionL
     *            the {@link ICollisionListener} who's collide method will be called for this instance
     */
    public SubBlock addCollisionListener(final ICollisionListener collisionL)
    {
        collisionEffect = true;
        collisionList.add(collisionL);
        return this;
    }

    /**
     * @param displayL
     *            the {@link IDisplayListener} who's randomDisplayTick method will be called for this instance
     */
    public SubBlock addDisplayListener(final IDisplayListener displayL)
    {
        mainBlock.setTickRandomly(getMeta());
        displayList.add(displayL);
        return this;
    }

    // /////////////////////////////
    // Block Redirect Methods
    // /////////////////////////////
    public void breakBlock(final World world, final int x, final int y, final int z, final int id, final int meta)
    {}

    public TileEntity createTileEntity(final World world, final int meta)
    {
        return tile.createTileEntity(world, meta);
    }

    public int damageDropped(final int meta)
    {
        if (idDropped(new Random(), meta) == mainBlock.blockID)
        {
            return meta;
        } else
        {
            return 0;
        }
    }

    public Block getBlock()
    {
        return mainBlock;
    }

    public float getBlockHardness()
    {
        return hardness;
    }

    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side)
    {
        return texture.getBlockTexture(blockAccess, x, y, z, side);
    }

    public Icon getBlockTextureFromSide(final int side)
    {
        return texture.getIcon(side, getMeta());
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z)
    {
        if (collisionEffect)
        {
            final float var5 = 0.025F;
            return AxisAlignedBB.getAABBPool().getAABB(x, y, z, (x + 1), ((y + 1.0F) - var5), (z + 1));
        }

        return null;
    }

    public CreativeTabs getCreativeTab()
    {
        return tab;
    }

    public int getDamageValue(final World world, final int x, final int y, final int z)
    {
        return getMeta();
    }

    public float getExplosionResistance(final Entity entity, final World world, final int x, final int y, final int z, final double explosionX, final double explosionY,
            final double explosionZ)
    {
        return blockResistance / 5.0F;
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int meta)
    {
        return texture.getIcon(side, meta);
    }

    /**
     * @return the texture
     */
    public ITextureHelper getTextureHelper()
    {
        return texture;
    }

    /**
     * @return the tile
     */
    public ITileHelper getTileHelper()
    {
        return tile;
    }

    public String getUnlocalizedName()
    {
        return unlocName;
    }

    public boolean hasTileEntity(final int meta)
    {
        return tile.hasTileEntity(meta);
    }

    public int idDropped(final Random rand, final int par3)
    {
        if (drop != null)
        {
            return drop.itemID;
        } else
        {
            return mainBlock.blockID;
        }
    }

    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int wut, final float clickX, final float clickY,
            final float clockZ)
    {
        if (world.isRemote)
        {
            return true;
        }
        if (player.isSneaking())
        {
            return false;
        }
        if (hasTileEntity(world.getBlockMetadata(x, y, z)))
        {
            final BaseTE te = (BaseTE) world.getBlockTileEntity(x, y, z);
            if (te != null)
            {
                GuiHandler.openGui(FunctionHelper.getTEName(world, x, y, z), player, x, y, z);
                return true;
            } else
            {
                CCMLogger.DEFAULT_LOGGER.debug("TileEntity at %s, %s, %s, was null", x, y, z);
            }
        }
        return false;
    }

    public void onBlockAdded(final World world, final int x, final int y, final int z)
    {}

    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase living, final ItemStack itemStack)
    {
        if (hasTileEntity(world.getBlockMetadata(x, y, z)))
        {

            final BaseTE te = (BaseTE) world.getBlockTileEntity(x, y, z);
            final int direction;
            final int facing = MathHelper.floor_double(((living.rotationYaw * 4.0F) / 360.0F) + 0.5D) & 3;

            switch (facing)
            {
                case 0:
                    direction = ForgeDirection.NORTH.ordinal();
                    break;
                case 1:
                    direction = ForgeDirection.EAST.ordinal();
                    break;
                case 2:
                    direction = ForgeDirection.SOUTH.ordinal();
                    break;
                case 3:
                    direction = ForgeDirection.WEST.ordinal();
                    break;
                default:
                    direction = ForgeDirection.NORTH.ordinal();
                    break;
            }

            BlockHelper.updateAdjacent(world, x, y, z, 3);

            if (itemStack.hasDisplayName())
            {
                te.setCustomName(itemStack.getDisplayName());
            }
            te.setOwner(living.getEntityName());
            te.setOrientation(direction);
        }
    }

    public void onEntityCollidedWithBlock(final World world, final int x, final int y, final int z, final Entity par5Entity)
    {
        for (final ICollisionListener cl : collisionList)
        {
            cl.collide(world, x, y, z, par5Entity, getMeta());
        }
    }

    public int quantityDroppedWithBonus(final int fortune, final Random rand)
    {
        return quantityDroppedWithBonus(dropMin, dropMax, fortune, rand);
    }

    public int quantityDroppedWithBonus(int dropMin, int dropMax, final int fortune, final Random rand)
    {
        if (dropMax > 1)
        {
            return dropMin + rand.nextInt(dropMax + fortune) + fortune;
        } else
        {
            return 1;
        }
    }

    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand)
    {
        for (final IDisplayListener dl : displayList)
        {
            dl.randomDisplayTick(world, x, y, z, rand);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister register)
    {
        texture.registerIcons(register);
    }

    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int fortune)
    {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        final int count = quantityDroppedWithBonus(fortune, world.rand);
        for (int i = 0; i < count; i++)
        {
            final int id = idDropped(world.rand, fortune);
            if (id > 0)
            {
                ret.add(new ItemStack(id, 1, damageDropped(getMeta())));
            }
        }
        return ret;
    }

    // /////////////////////////////
    // Instance Modifiers
    // /////////////////////////////

    public int getMeta()
    {
        return meta;
    }

    public SubBlock setBlockDrops(final ItemStack item, final int min, final int max)
    {
        drop = item.copy();
        dropMin = min;
        dropMax = max;
        return this;
    }

    public SubBlock setCreativeTab(final CreativeTabs tab)
    {
        mainBlock.setCreativeTab(tab);
        this.tab = tab;
        return this;
    }

    public SubBlock setHardness(final float hardness)
    {
        this.hardness = hardness;

        if (blockResistance < (hardness * 5.0F))
        {
            blockResistance = hardness * 5.0F;
        }

        return this;
    }

    public SubBlock setResistance(final float ressistance)
    {
        blockResistance = ressistance * 3;
        return this;
    }

    public SubBlock setSlipperiness(final float slipperiness)
    {
        mainBlock.slipperiness = slipperiness;
        return this;
    }

    public SubBlock setTileEntity(final TileEntity tile)
    {
        this.tile.setTileEntity(tile);
        return this;
    }

    public SubBlock setUnlocalizedName(final Enum<?> enu)
    {
        return setUnlocalizedName(enu.name());
    }

    public SubBlock setUnlocalizedName(final String string)
    {
        unlocName = string + ".name";
        mainBlock.setUnlocalizedName(unlocName);
        return this;
    }

    // /////////////////////////////
    // Object Redirect Methods
    // /////////////////////////////
    @Override
    public String toString()
    {
        return super.toString() + mainBlock.getUnlocalizedName();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + Float.floatToIntBits(blockResistance);
        result = (prime * result) + (collisionEffect ? 1231 : 1237);
        result = (prime * result) + ((collisionList == null) ? 0 : collisionList.hashCode());
        result = (prime * result) + ((displayList == null) ? 0 : displayList.hashCode());
        result = (prime * result) + ((drop == null) ? 0 : drop.hashCode());
        result = (prime * result) + dropMax;
        result = (prime * result) + dropMin;
        result = (prime * result) + Float.floatToIntBits(hardness);
        result = (prime * result) + ((mainBlock == null) ? 0 : mainBlock.hashCode());
        result = (prime * result) + getMeta();
        result = (prime * result) + ((tab == null) ? 0 : tab.hashCode());
        result = (prime * result) + ((texture == null) ? 0 : texture.hashCode());
        result = (prime * result) + ((tile == null) ? 0 : tile.hashCode());
        result = (prime * result) + ((unlocName == null) ? 0 : unlocName.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof SubBlock))
        {
            return false;
        }
        final SubBlock other = (SubBlock) obj;
        if (Float.floatToIntBits(blockResistance) != Float.floatToIntBits(other.blockResistance))
        {
            return false;
        }
        if (collisionEffect != other.collisionEffect)
        {
            return false;
        }
        if (collisionList == null)
        {
            if (other.collisionList != null)
            {
                return false;
            }
        } else if (!collisionList.equals(other.collisionList))
        {
            return false;
        }
        if (displayList == null)
        {
            if (other.displayList != null)
            {
                return false;
            }
        } else if (!displayList.equals(other.displayList))
        {
            return false;
        }
        if (drop == null)
        {
            if (other.drop != null)
            {
                return false;
            }
        } else if (!drop.equals(other.drop))
        {
            return false;
        }
        if (dropMax != other.dropMax)
        {
            return false;
        }
        if (dropMin != other.dropMin)
        {
            return false;
        }
        if (Float.floatToIntBits(hardness) != Float.floatToIntBits(other.hardness))
        {
            return false;
        }
        if (mainBlock == null)
        {
            if (other.mainBlock != null)
            {
                return false;
            }
        } else if (!mainBlock.equals(other.mainBlock))
        {
            return false;
        }
        if (getMeta() != other.getMeta())
        {
            return false;
        }
        if (tab == null)
        {
            if (other.tab != null)
            {
                return false;
            }
        } else if (!tab.equals(other.tab))
        {
            return false;
        }
        if (texture == null)
        {
            if (other.texture != null)
            {
                return false;
            }
        } else if (!texture.equals(other.texture))
        {
            return false;
        }
        if (tile == null)
        {
            if (other.tile != null)
            {
                return false;
            }
        } else if (!tile.equals(other.tile))
        {
            return false;
        }
        if (unlocName == null)
        {
            if (other.unlocName != null)
            {
                return false;
            }
        } else if (!unlocName.equals(other.unlocName))
        {
            return false;
        }
        return true;
    }

    // /////////////////////////////
    // Static Factory's
    // /////////////////////////////
    public static SubBlock createAndSetUp(final Enum<? extends IBlockEnum> blockEnum, final int id, final String textureLoc)
    {

        final String texture = TextureHelper.getTexture(blockEnum.name(), textureLoc);
        return setUp(blockEnum, new SubBlock(id, blockEnum.ordinal(), texture).setUnlocalizedName(blockEnum));
    }

    public static SubBlock setUp(final Enum<? extends IBlockEnum> blockEnum, final SubBlock instance)
    {

        final MainBlock block = (MainBlock) instance.getBlock();

        ((IBlockEnum) blockEnum).setBaseBlock(block);

        MainBlock.registerID(block.blockID);

        return instance;
    }
}