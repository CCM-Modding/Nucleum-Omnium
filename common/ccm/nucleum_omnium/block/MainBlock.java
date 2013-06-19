package ccm.nucleum_omnium.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

public class MainBlock extends Block {
    
    SubBlock[]                  subBlocks;
    List<Integer>               tickList;
    ArrayList<CreativeTabs>     tabs;
    
    public static List<Integer> registeredIDs;
    
    public static void registerID(final int id) {
        if (registeredIDs == null) {
            registeredIDs = new ArrayList<Integer>();
        }
        
        if (registeredIDs.contains(id)) {
            return;
        }
        
        final Block block = Block.blocksList[id];
        if (block instanceof MainBlock) {
            GameRegistry.registerBlock(block, MainItemBlock.class, block.getUnlocalizedName());
            registeredIDs.add(id);
        }
    }
    
    public MainBlock(final int id) {
        super(id, Material.rock);
        
        subBlocks = new SubBlock[16];
        
        tickList = new ArrayList<Integer>();
    }
    
    public MainBlock(final int id, final Material material) {
        super(id, material);
        
        subBlocks = new SubBlock[16];
        
        tickList = new ArrayList<Integer>();
    }
    
    public void addSubBlock(final SubBlock block, final int meta) {
        if (subBlocks[meta] == null) {
            subBlocks[meta] = block;
        } else {
            throw new IllegalArgumentException("Block " + blockID + " " + this + " metadata " + meta + " is already occupied by " + subBlocks[meta] + " when adding " + block);
        }
    }
    
    @Override
    public int damageDropped(final int meta) {
        if (subBlocks[meta] != null) {
            return subBlocks[meta].damageDropped(meta);
        }
        return meta;
    }
    
    public SubBlock[] getSubBlocks() {
        return subBlocks;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(final int meta, final CreativeTabs creativeTab, final List list) {
        for (int n = 0; n < 16; n++) {
            if ((subBlocks[n] != null) && (creativeTab == subBlocks[n].getCreativeTab())) {
                list.add(new ItemStack(this, 1, n));
            }
        }
    }
    
    // --Listeners--//
    public void setTickRandomly(final int meta) {
        if (!tickList.contains(meta)) {
            this.setTickRandomly(true);
            tickList.add(meta);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        
        AxisAlignedBB ret = null;
        if (subBlocks[meta] != null) {
            ret = subBlocks[meta].getCollisionBoundingBoxFromPool(world, x, y, z);
        }
        
        if (ret != null) {
            return ret;
        } else {
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final int x, final int y, final int z, final Entity entity) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null) {
            subBlocks[meta].onEntityCollidedWithBlock(world, x, y, z, entity);
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
        final int meta = world.getBlockMetadata(x, y, z);
        
        if (subBlocks[meta] != null) {
            subBlocks[meta].randomDisplayTick(world, x, y, z, rand);
        } else {
            super.randomDisplayTick(world, x, y, z, rand);
        }
    }
    
    // --Block Redirect Methods--//
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side) {
        final int meta = blockAccess.getBlockMetadata(x, y, z);
        
        if (subBlocks[meta] != null) {
            return subBlocks[meta].getBlockTexture(blockAccess, x, y, z, side);
        } else {
            return super.getBlockTexture(blockAccess, x, y, z, side);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister) {
        for (int i = 0; i < 16; ++i) {
            if (subBlocks[i] != null) {
                subBlocks[i].registerIcons(iconRegister);
            }
        }
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getIcon(final int par1, final int par2) {
        if (subBlocks[par2] != null) {
            return subBlocks[par2].getBlockTextureFromSide(par1);
        } else {
            return null;
        }
    }
    
    @Override
    public ArrayList<ItemStack> getBlockDropped(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        
        final int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++) {
            final int id = idDropped(metadata, world.rand, fortune);
            if (id > 0) {
                ret.add(new ItemStack(id, 1, damageDropped(metadata)));
            }
        }
        return ret;
    }
    
    @Override
    public int quantityDropped(final int meta, final int fortune, final Random rand) {
        return subBlocks[meta].quantityDroppedWithBonus(fortune, rand);
    }
    
    @Override
    public int idDropped(final int meta, final Random rand, final int par3) {
        return subBlocks[meta].idDropped(rand, par3);
    }
    
    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
    public float getBlockHardness(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null) {
            return subBlocks[meta].getBlockHardness();
        }
        
        return 0;
    }
    
    @Override
    public float getExplosionResistance(final Entity entity,
                                        final World world,
                                        final int x,
                                        final int y,
                                        final int z,
                                        final double explosionX,
                                        final double explosionY,
                                        final double explosionZ) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null) {
            return subBlocks[meta].getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
        }
        return 0;
    }
    
    @Override
    public int getDamageValue(final World world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null) {
            System.out.println("returning " + subBlocks[meta].getDamageValue(world, x, y, z));
            return subBlocks[meta].getDamageValue(world, x, y, z);
        }
        return meta;
    }
    
    /**
     * Sets the CreativeTab to display this block on.
     */
    @Override
    public Block setCreativeTab(final CreativeTabs par1CreativeTabs) {
        if (tabs == null) {
            tabs = new ArrayList<CreativeTabs>();
        }
        if (!tabs.contains(par1CreativeTabs)) {
            tabs.add(par1CreativeTabs);
        }
        return this;
    }
    
    public CreativeTabs[] getCreativeTabArray() {
        if (tabs == null) {
            return new CreativeTabs[0];
        }
        return tabs.toArray(new CreativeTabs[tabs.size()]);
    }
    
    @Override
    public TileEntity createTileEntity(final World world, final int meta) {
        if (subBlocks[meta] != null) {
            return subBlocks[meta].createTileEntity(world, meta);
        } else {
            return null;
        }
    }
    
    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final int id, final int meta) {
        super.breakBlock(world, x, y, z, id, meta);
        if (subBlocks[meta] != null) {
            subBlocks[meta].breakBlock(world, x, y, z, id, meta);
        }
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
                                    final float clockZ) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (subBlocks[meta] != null) {
            if (subBlocks[meta].hasTileEntity()) {
                return subBlocks[meta].onBlockActivated(world, x, y, z, player, wut, clickX, clickY, clockZ);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final int x, final int y, final int z) {
        super.onBlockAdded(world, x, y, z);
        
        final int meta = world.getBlockMetadata(x, y, z);
        
        if (subBlocks[meta] != null) {
            subBlocks[meta].onBlockAdded(world, x, y, z);
        }
    }
    
    /**
     * Sets the direction of the block when placed
     */
    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLiving living, final ItemStack itemStack) {
        
        final int meta = world.getBlockMetadata(x, y, z);
        
        if (subBlocks[meta] != null) {
            subBlocks[meta].onBlockPlacedBy(world, x, y, z, living, itemStack);
        }
    }
}