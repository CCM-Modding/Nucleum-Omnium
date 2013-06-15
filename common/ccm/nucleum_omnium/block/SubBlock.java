package ccm.nucleum_omnium.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlock {
    
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
    private boolean                 collisionEffect;
    
    public Icon                     icon;
    public String                   iconName;
    
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
    
    // Listeners
    public void addDisplayListener(IDisplayListener displayL) {
        mainBlock.setTickRandomly(meta);
        displayList.add(displayL);
    }
    
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
            return AxisAlignedBB.getAABBPool().getAABB((double) x,
                                                       (double) y,
                                                       (double) z,
                                                       (double) (x + 1),
                                                       (double) ((float) (y + 1) - var5),
                                                       (double) (z + 1));
        }
        
        return null;
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity par5Entity) {
        for (ICollisionListener cl : collisionList)
            cl.collide(world, x, y, z, par5Entity, meta);
    }
    
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
    
    public SubBlock setCreativeTab(CreativeTabs tab) {
        mainBlock.setCreativeTab(tab);
        this.tab = tab;
        return this;
    }
    
    public CreativeTabs getCreativeTab() {
        return tab;
    }
    
    public SubBlock setUnlocalizedName(String string) {
        mainBlock.setUnlocalizedName(string);
        return this;
    }
    
    public Block getBlock() {
        return mainBlock;
    }
    
    public void setBlockDrops(ItemStack item, int min, int max) {
        drop = item.copy();
        dropMin = min;
        dropMax = max;
    }
    
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        if (drop != null && dropMax > 1)
            return dropMin + rand.nextInt(dropMax + fortune) + fortune;
        else
            return 1;
    }
    
    public int idDropped(Random rand, int par3) {
        if (drop != null)
            return drop.itemID;
        else
            return mainBlock.blockID;
    }
    
    public SubBlock setHardness(float hardness) {
        this.hardness = hardness;
        
        if (blockResistance < hardness * 5.0F) {
            blockResistance = hardness * 5.0F;
        }
        
        return this;
    }
    
    public float getBlockHardness() {
        return hardness;
    }
    
    public SubBlock setResistance(float ressistance) {
        blockResistance = ressistance * 3;
        return this;
    }
    
    public float getExplosionResistance(Entity entity) {
        return blockResistance / 5.0F;
    }
    
    public String toString() {
        return super.toString() + mainBlock.getUnlocalizedName();
    }
    
    public int damageDropped(int meta) {
        if (idDropped(new Random(), meta) == mainBlock.blockID)
            return meta;
        else
            return 0;
    }
    
    public int getDamageValue(World world, int x, int y, int z) {
        System.out.println(meta);
        return meta;
    }
    
}