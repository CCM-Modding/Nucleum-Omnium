/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenDesert;

import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.block.MainBlock;
import ccm.nucleum.omnium.utils.handler.LogHandler;

public final class FunctionHelper extends BaseNIC
{

    /**
     * Drops the Inventory that is contained in the {@link TileEntity}.
     * 
     * @param world
     *            The world in witch to drop the Items in.
     * @param x
     *            The x location
     * @param y
     *            The y location.
     * @param z
     *            The z location.
     */
    public static void dropInventory(final World world, final int x, final int y, final int z)
    {
        final TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity != null)
        {
            if (!(tileEntity instanceof IInventory))
            {
                LogHandler.warning(NucleumOmnium.instance, "Tile %s was not an instance of IInventory, and called dropInventory!!", tileEntity);
                return;
            }
            final IInventory inventory = (IInventory) tileEntity;
            for (int i = 0; i < inventory.getSizeInventory(); i++)
            {
                final ItemStack itemStack = inventory.getStackInSlot(i);
                if ((itemStack != null) && (itemStack.stackSize > 0))
                {
                    final float dX = (rand.nextFloat() * 0.8F) + 0.1F;
                    final float dY = (rand.nextFloat() * 0.8F) + 0.1F;
                    final float dZ = (rand.nextFloat() * 0.8F) + 0.1F;
                    final EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));
                    if (itemStack.hasTagCompound())
                    {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                    }
                    final float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = (rand.nextGaussian() * factor) + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(entityItem);
                    itemStack.stackSize = 0;
                }
            }
        }
    }

    /**
     * Gets all the Dimensions listed by their Dimension ID
     * 
     * @return A {@code HashMap<Integer, World>} containig all the Dimensions
     */
    public static HashMap<Integer, World> getDimensions()
    {
        final HashMap<Integer, World> dimensions = new HashMap<Integer, World>();
        for (final World world : NucleumOmnium.server.worldServers)
        {
            dimensions.put(Integer.valueOf(world.provider.dimensionId), world);
        }
        return dimensions;
    }

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static String getTEName(final World world, final int x, final int y, final int z)
    {
        return "container." + ((MainBlock) Block.blocksList[world.getBlockId(x, y, z)]).getSubBlocks()[world.getBlockMetadata(x, y, z)].getUnlocalizedName();
    }

    /**
     * Checks if there is Lava or Fire below the block
     * 
     * @param world
     *            The world to check in
     * @param xCoord
     *            The X coordinate to check in
     * @param yCoord
     *            The Y coordinate to check in - 1 so that the block doesn't interrupt the operation
     * @param zCoord
     *            The Z coordinate to check in
     * @return true if there is Fire or Lava below. Otherwise false
     */
    public static boolean isFireBelow(final World world, final int xCoord, final int yCoord, final int zCoord)
    {
        final Block block = Block.blocksList[world.getBlockId(xCoord, yCoord, zCoord)];
        if (block.blockMaterial == Material.fire)
        {
            return true;
        } else if (block.blockMaterial == Material.lava)
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Checks if the sun is visible
     * 
     * @param world
     *            The world to check in
     * @param xCoord
     *            The X coordinate to check in
     * @param yCoord
     *            The Y coordinate to check in + 1 so that the block doesn't interrupt the operation
     * @param zCoord
     *            The Z coordinate to check in
     * @return true if the sun is visible. Otherwise false
     */
    public static boolean isSunVisible(final World world, final int xCoord, final int yCoord, final int zCoord)
    {
        return world.isDaytime() && !world.provider.hasNoSky && world.canBlockSeeTheSky(xCoord, yCoord, zCoord)
                && ((world.getWorldChunkManager().getBiomeGenAt(xCoord, yCoord) instanceof BiomeGenDesert) || (!world.isRaining() && !world.isThundering()));
    }
}