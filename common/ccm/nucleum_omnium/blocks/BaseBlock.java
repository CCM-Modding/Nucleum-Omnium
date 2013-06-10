package ccm.nucleum_omnium.blocks;

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

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum_omnium.items.ItemBaseBlock;

/**
 * WIP(Work In Progress) DO NOT USE!
 */
public class BaseBlock extends Block
{

    SubBlock[]                  subBlocks;

    List<Integer>               tickList;

    ArrayList<CreativeTabs>     tabs;

    public static List<Integer> registeredIDs;

    // public static SubBlock air = new SubBlock(0, 0, "").setHardness(0).setResistance(0);

    @SuppressWarnings("deprecation")
    public static void registerID(final int id)
    {
        if (registeredIDs == null){
            registeredIDs = new ArrayList<Integer>();
        }

        if (registeredIDs.contains(id)){
            return;
        }

        final Block block = Block.blocksList[id];
        if (block instanceof BaseBlock){
            GameRegistry.registerBlock(block, ItemBaseBlock.class);
            registeredIDs.add(id);
        }
    }

    public BaseBlock(final int id)
    {
        super(id, Material.rock);
        System.out.println("test");
        this.subBlocks = new SubBlock[16];
        // for(int i = 0; i > 16; i++)
        // subBlocks[i] = air;

        this.tickList = new ArrayList<Integer>();
    }

    public void addSubBlock(final SubBlock block, final int meta)
    {
        if (this.subBlocks[meta] == null){
            this.subBlocks[meta] = block;
        }else{
            throw new IllegalArgumentException("[BaseBlock] In block "
                                               + this.blockID
                                               + " "
                                               + this
                                               + " metadata "
                                               + meta
                                               + " is already occupied by "
                                               + this.subBlocks[meta]
                                               + " when adding "
                                               + block);
        }
    }

    @Override
    public int damageDropped(final int meta)
    {
        if (this.subBlocks[meta] != null){
            return this.subBlocks[meta].damageDropped(meta);
        }
        return meta;
    }

    @Override
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List)
    {
        for (int n = 0; n < 16; n++){
            if ((this.subBlocks[n] != null) && (par2CreativeTabs == this.subBlocks[n].getCreativeTab())){
                par3List.add(new ItemStack(this, 1, n));
            }
        }
    }

    // --Listeners--//
    public void setTickRandomly(final int meta)
    {
        if (!this.tickList.contains(meta)){
            this.setTickRandomly(true);
            this.tickList.add(meta);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4)
    {
        final int meta = par1World.getBlockMetadata(par2, par3, par4);

        AxisAlignedBB ret = null;
        if (this.subBlocks[meta] != null){
            ret = this.subBlocks[meta].getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
        }

        if (ret != null){
            return ret;
        }else{
            return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity)
    {
        final int meta = par1World.getBlockMetadata(par2, par3, par4);
        this.subBlocks[meta].onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
    }

    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random)
    {
        final int meta = par1World.getBlockMetadata(par2, par3, par4);

        if (this.subBlocks[meta] != null){
            this.subBlocks[meta].randomDisplayTick(par1World, par2, par3, par4, par5Random);
        }else{
            super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
        }
    }

    // --Block Redirect Methods--//

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(final IBlockAccess par1IBlockAccess, final int x, final int y, final int z, final int side)
    {
        final int meta = par1IBlockAccess.getBlockMetadata(x, y, z);

        if (this.subBlocks[meta] != null){
            return this.subBlocks[meta].getBlockTexture(par1IBlockAccess, x, y, z, side);
        }else{
            return super.getBlockTexture(par1IBlockAccess, x, y, z, side);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister par1IconRegister)
    {
        for (int i = 0; i < 16; ++i){
            if (this.subBlocks[i] != null){
                this.subBlocks[i].registerIcons(par1IconRegister);
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */

    @Override
    public Icon getIcon(final int par1, final int par2)
    {
        if (this.subBlocks[par2] != null){
            return this.subBlocks[par2].getBlockTextureFromSide(par1);
        }

        return null;
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(final World world, final int x, final int y, final int z, final int metadata, final int fortune)
    {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        final int count = this.quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++){
            final int id = this.idDropped(metadata, world.rand, fortune);
            if (id > 0){
                ret.add(new ItemStack(id, 1, this.damageDropped(metadata)));
            }
        }
        return ret;
    }

    @Override
    public int quantityDropped(final int meta, final int fortune, final Random random)
    {
        return this.subBlocks[meta].quantityDroppedWithBonus(fortune, random);
    }

    @Override
    public int idDropped(final int meta, final Random par2Random, final int par3)
    {
        return this.subBlocks[meta].idDropped(par2Random, par3);
    }

    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
    public float getBlockHardness(final World world, final int x, final int y, final int z)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (this.subBlocks[meta] != null){
            return this.subBlocks[meta].getBlockHardness();
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
                                        final double explosionZ)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (this.subBlocks[meta] != null){
            return this.subBlocks[meta].getExplosionResistance(entity);
        }

        return 0;
    }

    @Override
    public int getDamageValue(final World world, final int x, final int y, final int z)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        if (this.subBlocks[meta] != null){
            System.out.println("returning " + this.subBlocks[meta].getDamageValue(world, x, y, z));
            return this.subBlocks[meta].getDamageValue(world, x, y, z);
        }
        return meta;
    }

    /**
     * Sets the CreativeTab to display this block on.
     */
    @Override
    public Block setCreativeTab(final CreativeTabs par1CreativeTabs)
    {
        if (this.tabs == null){
            this.tabs = new ArrayList<CreativeTabs>();
        }

        if (!this.tabs.contains(par1CreativeTabs)){
            this.tabs.add(par1CreativeTabs);
        }

        return this;
    }

    public CreativeTabs[] getCreativeTabArray()
    {
        if (this.tabs == null){
            return new CreativeTabs[0];
        }

        return this.tabs.toArray(new CreativeTabs[this.tabs.size()]);
    }
}