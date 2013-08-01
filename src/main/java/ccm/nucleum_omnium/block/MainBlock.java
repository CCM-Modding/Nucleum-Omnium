/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.block;

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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum_omnium.block.sub.SubBlock;
import ccm.nucleum_omnium.helper.FunctionHelper;
import ccm.nucleum_omnium.item.block.MainItemBlock;

/**
 * MainBlock
 * <p>
 * A Block that redirects all of it's data to a specific meta data value
 * <p>
 * This class SHOULD NOT be extended unless it is something Block wide in which case one MUST Check that there
 * is NO meta data way of doing the action
 */
public class MainBlock extends Block
{

    SubBlock[]                  subBlocks;
    List<Integer>               tickList;
    ArrayList<CreativeTabs>     tabs;

    public static List<Integer> registeredIDs;

    public static void registerID(final int id)
    {
        if (registeredIDs == null)
        {
            registeredIDs = new ArrayList<Integer>();
        }

        if (registeredIDs.contains(id))
        {
            return;
        }

        final Block block = Block.blocksList[id];
        if (block instanceof MainBlock)
        {
            GameRegistry.registerBlock(block, MainItemBlock.class, block.getUnlocalizedName());
            registeredIDs.add(id);
        }
    }

    public MainBlock(final int id)
    {
        this(id, Material.rock);
    }

    public MainBlock(final int id, final Material material)
    {
        super(id, material);

        subBlocks = new SubBlock[16];

        tickList = new ArrayList<Integer>();
    }

    public void addSubBlock(final SubBlock block, final int meta)
    {
        if (subBlocks[meta] == null)
        {
            subBlocks[meta] = block;
        } else
        {
            throw new IllegalArgumentException(String.format("Block %s with id: %d and metadata: %d is already occupied by %s when adding  %s",
                                                             this,
                                                             blockID,
                                                             meta,
                                                             subBlocks[meta],
                                                             block));
        }
    }

    @Override
    public void breakBlock(final World world,
                           final int x,
                           final int y,
                           final int z,
                           final int id,
                           final int meta)
    {
        FunctionHelper.dropInventory(world, x, y, z);
        if (subBlocks[meta] != null)
        {
            subBlocks[meta].breakBlock(world, x, y, z, id, meta);
        }
        super.breakBlock(world, x, y, z, id, meta);
    }

    @Override
    public TileEntity createTileEntity(final World world, final int meta)
    {
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].createTileEntity(world, meta);
        } else
        {
            return null;
        }
    }

    @Override
    public int damageDropped(final int meta)
    {
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].damageDropped(meta);
        }
        return meta;
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(final World world,
                                                final int x,
                                                final int y,
                                                final int z,
                                                final int meta,
                                                final int fortune)
    {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        final int count = quantityDropped(meta, fortune, world.rand);
        for (int i = 0; i < count; i++)
        {
            final int id = idDropped(meta, world.rand, fortune);
            if (id > 0)
            {
                ret.add(new ItemStack(id, 1, damageDropped(meta)));
            }
        }
        return ret;
    }

    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
    public float getBlockHardness(final World world, final int x, final int y, final int z)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].getBlockHardness();
        }

        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(final IBlockAccess blockAccess,
                                final int x,
                                final int y,
                                final int z,
                                final int side)
    {
        final int meta = blockAccess.getBlockMetadata(x, y, z);

        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].getBlockTexture(blockAccess, x, y, z, side);
        } else
        {
            return super.getBlockTexture(blockAccess, x, y, z, side);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world,
                                                         final int x,
                                                         final int y,
                                                         final int z)
    {
        final int meta = world.getBlockMetadata(x, y, z);

        AxisAlignedBB ret = null;
        if (subBlocks[meta] != null)
        {
            ret = subBlocks[meta].getCollisionBoundingBoxFromPool(world, x, y, z);
        }

        if (ret != null)
        {
            return ret;
        } else
        {
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
        }
    }

    // --Block Redirect Methods--//

    public CreativeTabs[] getCreativeTabArray()
    {
        if (tabs == null)
        {
            return new CreativeTabs[0];
        }
        return tabs.toArray(new CreativeTabs[tabs.size()]);
    }

    @Override
    public int getDamageValue(final World world, final int x, final int y, final int z)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].getDamageValue(world, x, y, z);
        }
        return meta;
    }

    @Override
    public float getExplosionResistance(final Entity entity,
                                        final World world,
                                        final int x,
                                        final int y,
                                        final int z,
                                        final double explosionX,
                                        final double explosionY,
                                        final double explosionZ)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].getExplosionResistance(entity,
                                                          world,
                                                          x,
                                                          y,
                                                          z,
                                                          explosionX,
                                                          explosionY,
                                                          explosionZ);
        }
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int meta)
    {
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].getIcon(side, meta);
        } else
        {
            return super.getIcon(side, meta);
        }
    }

    public SubBlock[] getSubBlocks()
    {
        return subBlocks;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final int meta, final CreativeTabs creativeTab, final List list)
    {
        for (int n = 0; n < 16; n++)
        {
            if ((subBlocks[n] != null) && (creativeTab == subBlocks[n].getCreativeTab()))
            {
                list.add(new ItemStack(this, 1, n));
            }
        }
    }

    @Override
    public boolean hasTileEntity(final int meta)
    {
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].hasTileEntity(meta);
        } else
        {
            return false;
        }
    }

    @Override
    public int idDropped(final int meta, final Random rand, final int par3)
    {
        return subBlocks[meta].idDropped(rand, par3);
    }

    @Override
    public boolean onBlockActivated(final World world,
                                    final int x,
                                    final int y,
                                    final int z,
                                    final EntityPlayer player,
                                    final int wut,
                                    final float clickX,
                                    final float clickY,
                                    final float clockZ)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null)
        {
            return subBlocks[meta].onBlockActivated(world, x, y, z, player, wut, clickX, clickY, clockZ);
        } else
        {
            return false;
        }
    }

    @Override
    public void onBlockAdded(final World world, final int x, final int y, final int z)
    {
        super.onBlockAdded(world, x, y, z);

        final int meta = world.getBlockMetadata(x, y, z);

        if (subBlocks[meta] != null)
        {
            subBlocks[meta].onBlockAdded(world, x, y, z);
        }
    }

    /**
     * Sets the direction of the block when placed
     */
    @Override
    public void onBlockPlacedBy(final World world,
                                final int x,
                                final int y,
                                final int z,
                                final EntityLivingBase living,
                                final ItemStack itemStack)
    {

        final int meta = world.getBlockMetadata(x, y, z);

        if (subBlocks[meta] != null)
        {
            subBlocks[meta].onBlockPlacedBy(world, x, y, z, living, itemStack);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(final World world,
                                          final int x,
                                          final int y,
                                          final int z,
                                          final Entity entity)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null)
        {
            subBlocks[meta].onEntityCollidedWithBlock(world, x, y, z, entity);
        }
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random rand)
    {
        return subBlocks[meta].quantityDroppedWithBonus(fortune, rand);
    }

    @Override
    public void
            randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand)
    {
        final int meta = world.getBlockMetadata(x, y, z);

        if (subBlocks[meta] != null)
        {
            subBlocks[meta].randomDisplayTick(world, x, y, z, rand);
        } else
        {
            super.randomDisplayTick(world, x, y, z, rand);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister)
    {
        for (int i = 0; i < 16; ++i)
        {
            if (subBlocks[i] != null)
            {
                subBlocks[i].registerIcons(iconRegister);
            }
        }
    }

    /**
     * Sets the CreativeTab to display this block on.
     */
    @Override
    public Block setCreativeTab(final CreativeTabs par1CreativeTabs)
    {
        if (tabs == null)
        {
            tabs = new ArrayList<CreativeTabs>();
        }
        if (!tabs.contains(par1CreativeTabs))
        {
            tabs.add(par1CreativeTabs);
        }
        return this;
    }

    // --Listeners--//
    public void setTickRandomly(final int meta)
    {
        if (!tickList.contains(meta))
        {
            this.setTickRandomly(true);
            tickList.add(meta);
        }
    }
}