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
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_omnium.helper.FunctionHelper;
import ccm.nucleum_omnium.tileentity.TileBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlock {
    /*
     * DATA
     */
    private MainBlock               mainBlock;
    private CreativeTabs            tab;
    int                             meta;
    
    private ItemStack               drop;
    private int                     dropMin;
    private int                     dropMax;
    
    private float                   hardness;
    private float                   blockResistance;
    
    public List<IDisplayListener>   displayList   = new ArrayList<IDisplayListener>();
    public List<ICollisionListener> collisionList = new ArrayList<ICollisionListener>();
    
    private TileEntity              te            = null;
    private boolean                 hasTE         = false;
    private boolean                 collisionEffect;
    
    private String                  unlocName;
    
    public Icon                     icon;
    public String                   iconName;
    
    /*
     * Constructors
     */
    /**
     * Creates a new SubBlock instance
     * 
     * @param id
     *            The ID of the MainBlock to use (Put the same for multiple
     *            instances if you want them to all correspond to the same block
     *            ID)
     * @param meta
     *            The metadata to use (Typically the enum.ordinal())
     * @param iconName
     *            The full path of the Icon, ex: harvestry:coal
     */
    public SubBlock(int id, int meta, String iconName) {
        if (Block.blocksList[id] == null) {
            mainBlock = new MainBlock(id);
            mainBlock.addSubBlock(this, meta);
        } else {
            mainBlock = (MainBlock) Block.blocksList[id];
            mainBlock.addSubBlock(this, meta);
        }
        
        this.meta = meta;
        this.iconName = iconName;
    }
    
    /**
     * Creates a new SubBlock instance
     * 
     * @param id
     *            The ID of the MainBlock to use (Put the same for multiple
     *            instances if you want them to all correspond to the same block
     *            ID)
     * @param meta
     *            The metadata to use (Typically the enum.ordinal())
     * @param material
     *            The Material that the block should be made of
     * @param iconName
     *            The full path of the Icon, ex: harvestry:coal
     */
    public SubBlock(int id, int meta, Material material, String iconName) {
        if (Block.blocksList[id] == null) {
            mainBlock = new MainBlock(id, material);
            mainBlock.addSubBlock(this, meta);
        } else {
            mainBlock = (MainBlock) Block.blocksList[id];
            mainBlock.addSubBlock(this, meta);
        }
        
        this.meta = meta;
        this.iconName = iconName;
    }
    
    /*
     * Listeners
     */
    /**
     * @param displayL
     *            the {@link IDisplayListener} who's randomDisplayTick method
     *            will be called for this instance
     */
    public void addDisplayListener(IDisplayListener displayL) {
        mainBlock.setTickRandomly(meta);
        displayList.add(displayL);
    }
    
    /**
     * @param collisionL
     *            the {@link ICollisionListener} who's collide method will be
     *            called for this instance
     */
    public void addCollisionListener(ICollisionListener collisionL) {
        collisionEffect = true;
        collisionList.add(collisionL);
    }
    
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        for (IDisplayListener dl : displayList) {
            dl.randomDisplayTick(world, x, y, z, rand);
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        if (collisionEffect) {
            float var5 = 0.025F;
            return AxisAlignedBB.getAABBPool().getAABB(x,
                                                       y,
                                                       z,
                                                       (x + 1),
                                                       ((y + 1.0F) - var5),
                                                       (z + 1));
        }
        
        return null;
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity par5Entity) {
        for (ICollisionListener cl : collisionList)
            cl.collide(world, x, y, z, par5Entity, meta);
    }
    
    /*
     * Instance Modifiers
     */
    public SubBlock setCreativeTab(CreativeTabs tab) {
        mainBlock.setCreativeTab(tab);
        this.tab = tab;
        return this;
    }
    
    public SubBlock setUnlocalizedName(String string) {
        unlocName = string;
        mainBlock.setUnlocalizedName(unlocName);
        return this;
    }
    
    public SubBlock setBlockDrops(ItemStack item, int min, int max) {
        drop = item.copy();
        dropMin = min;
        dropMax = max;
        return this;
    }
    
    public SubBlock setHardness(float hardness) {
        this.hardness = hardness;
        
        if (blockResistance < hardness * 5.0F) {
            blockResistance = hardness * 5.0F;
        }
        
        return this;
    }
    
    public SubBlock setResistance(float ressistance) {
        blockResistance = ressistance * 3;
        return this;
    }
    
    public SubBlock setSlipperiness(float slipperiness) {
        this.mainBlock.slipperiness = slipperiness;
        return this;
    }
    
    public SubBlock setTileEntity(TileEntity te) {
        this.te = te;
        hasTE = true;
        return this;
    }
    
    /*
     * Block Redirect Methods
     */
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return icon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.icon = iconRegister.registerIcon(iconName);
    }
    
    public Icon getBlockTextureFromSide(int side) {
        return icon;
    }
    
    public CreativeTabs getCreativeTab() {
        return tab;
    }
    
    public String getUnlocalizedName() {
        return unlocName;
    }
    
    public Block getBlock() {
        return mainBlock;
    }
    
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        if (drop != null && dropMax > 1) {
            return dropMin + rand.nextInt(dropMax + fortune) + fortune;
        } else {
            return 1;
        }
    }
    
    public int idDropped(Random rand, int par3) {
        if (drop != null) {
            return drop.itemID;
        } else {
            return mainBlock.blockID;
        }
    }
    
    public float getBlockHardness() {
        return hardness;
    }
    
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z,
            double explosionX, double explosionY, double explosionZ) {
        return blockResistance / 5.0F;
    }
    
    public int damageDropped(int meta) {
        if (idDropped(new Random(), meta) == mainBlock.blockID) {
            return meta;
        } else {
            return 0;
        }
    }
    
    public int getDamageValue(World world, int x, int y, int z) {
        return meta;
    }
    
    public TileEntity createTileEntity(World world, int meta) {
        return te;
    }
    
    public boolean hasTileEntity() {
        return hasTE;
    }
    
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        if (hasTE == true) {
            if (te instanceof IInventory) {
                FunctionHelper.dropInventory(world, x, y, z);
            }
        }
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int wut,
            float clickX, float clickY, float clockZ) {
        if (world.isRemote)
            return true;
        if (player.isSneaking())
            return false;
        else
            return false;
    }
    
    public void onBlockAdded(World world, int x, int y, int z) {
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving living,
            ItemStack itemStack) {
        
        if (hasTE == true) {
            int direction = 0;
            final int facing = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
            
            switch (facing) {
                case 0:
                    direction = ForgeDirection.NORTH.ordinal();
                    world.notifyBlockChange(x, y, z, mainBlock.blockID);
                    world.markBlockForUpdate(x, y, z);
                    break;
                case 1:
                    direction = ForgeDirection.EAST.ordinal();
                    world.notifyBlockChange(x, y, z, mainBlock.blockID);
                    world.markBlockForUpdate(x, y, z);
                    break;
                case 2:
                    direction = ForgeDirection.SOUTH.ordinal();
                    world.notifyBlockChange(x, y, z, mainBlock.blockID);
                    world.markBlockForUpdate(x, y, z);
                    break;
                case 3:
                    direction = ForgeDirection.WEST.ordinal();
                    world.notifyBlockChange(x, y, z, mainBlock.blockID);
                    world.markBlockForUpdate(x, y, z);
                    break;
                default:
                    direction = ForgeDirection.NORTH.ordinal();
                    world.notifyBlockChange(x, y, z, mainBlock.blockID);
                    world.markBlockForUpdate(x, y, z);
                    break;
            }
            
            if (itemStack.hasDisplayName()) {
                ((TileBase) world.getBlockTileEntity(x, y, z)).setCustomName(itemStack
                        .getDisplayName());
            }
            ((TileBase) world.getBlockTileEntity(x, y, z)).setOwner(living.getEntityName());
            ((TileBase) world.getBlockTileEntity(x, y, z)).setOrientation(direction);
        }
    }
    
    /*
     * Object Redirect Methods
     */
    @Override
    public String toString() {
        return super.toString() + mainBlock.getUnlocalizedName();
    }
}