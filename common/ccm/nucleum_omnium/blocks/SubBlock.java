package ccm.nucleum_omnium.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
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

/**
 * WIP(Work In Progress) DO NOT USE!
 */
public class SubBlock
{

    private BaseBlock               baseBlock;

    private CreativeTabs            tab;

    int                             meta;

    private ItemStack               drop;

    private int                     dropMin;

    private int                     dropMax;

    private float                   hardness;

    private float                   blockResistance;

    public List<IDisplayListener>   dlList = new ArrayList<IDisplayListener>();

    public List<ICollisionListener> clList = new ArrayList<ICollisionListener>();

    private boolean                 collisionEffect;

    public Icon                     icon;

    public String                   iconName;

    public SubBlock(final int id,
                    final int meta,
                    final String iconName)
    {
        if (Block.blocksList[id] == null){
            this.baseBlock = new BaseBlock(id);
            this.baseBlock.addSubBlock(this, meta);
        }else{
            this.baseBlock = (BaseBlock) Block.blocksList[id];
            this.baseBlock.addSubBlock(this, meta);
        }

        this.meta = meta;
        this.iconName = iconName;
    }

    // Listeners
    public void addDisplayListener(final IDisplayListener dl)
    {
        this.baseBlock.setTickRandomly(this.meta);
        this.dlList.add(dl);
    }

    public void addCollisionListener(final ICollisionListener cl)
    {
        this.collisionEffect = true;
        this.clList.add(cl);
    }

    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand)
    {
        for (final IDisplayListener dl : this.dlList){
            dl.randomDisplayTick(world, x, y, z, rand);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z)
    {
        if (this.collisionEffect){
            final float constant = 0.025F;
            return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1, (y + 1) - constant, z + 1);
        }

        return null;
    }

    public void onEntityCollidedWithBlock(final World world, final int x, final int y, final int z, final Entity entity)
    {
        for (final ICollisionListener cl : this.clList){
            cl.collide(world, x, y, z, entity, this.meta);
        }
    }

    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side)
    {
        return this.icon;

    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister iconRegister)
    {
        this.icon = iconRegister.registerIcon(this.iconName);
    }

    public Icon getBlockTextureFromSide(final int side)
    {
        return this.icon;
    }

    public SubBlock setCreativeTab(final CreativeTabs tab)
    {
        this.baseBlock.setCreativeTab(tab);
        this.tab = tab;
        return this;
    }

    public CreativeTabs getCreativeTab()
    {
        return this.tab;
    }

    public SubBlock setUnlocalizedName(final String name)
    {
        this.baseBlock.setUnlocalizedName(name);
        return this;
    }

    public Block getBlock()
    {
        return this.baseBlock;
    }

    public void setBlockDrops(final ItemStack item, final int min, final int max)
    {
        this.drop = item.copy();
        this.dropMin = min;
        this.dropMax = max;
    }

    public int quantityDroppedWithBonus(final int fortune, final Random rand)
    {
        if ((this.drop != null) && (this.dropMax > 1)){
            return this.dropMin + rand.nextInt(this.dropMax + fortune) + fortune;
        }else{
            return 1;
        }
    }

    public int idDropped(final Random rand, final int par3)
    {
        if (this.drop != null){
            return this.drop.itemID;
        }else{
            return this.baseBlock.blockID;
        }
    }

    public SubBlock setHardness(final float hardness)
    {
        this.hardness = hardness;

        if (this.blockResistance < (hardness * 5.0F)){
            this.blockResistance = hardness * 5.0F;
        }

        return this;
    }

    public float getBlockHardness()
    {
        return this.hardness;
    }

    public SubBlock setResistance(final float par1)
    {
        this.blockResistance = par1 * 3;
        return this;
    }

    public float getExplosionResistance(final Entity entity)
    {
        return this.blockResistance / 5.0F;
    }

    @Override
    public String toString()
    {
        return super.toString() + this.baseBlock.getUnlocalizedName();
    }

    public int damageDropped(final int meta)
    {
        if (this.idDropped(new Random(), meta) == this.baseBlock.blockID){
            return meta;
        }else{
            return 0;
        }
    }

    public int getDamageValue(final World world, final int x, final int y, final int z)
    {
        System.out.println(this.meta);
        return this.meta;
    }

}