package lib.cofh.util.fluid;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import lib.cofh.util.BlockHelper;
import lib.cofh.util.ItemHelper;
import lib.cofh.util.ServerHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Contains various helper functions to assist with {@link Fluid} and Fluid-related manipulation and interaction.
 * 
 * @author King Lemming
 */
public class FluidHelper
{
    public static final FluidStack WATER = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
    public static final FluidStack LAVA = new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);

    private FluidHelper()
    {}

    public static boolean fillTankWithContainer(World world, IFluidHandler handler, EntityPlayer player)
    {
        ItemStack container = player.getCurrentEquippedItem();
        FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(container);
        if (fluid != null)
        {
            if ((handler.fill(ForgeDirection.UNKNOWN, fluid, false) == fluid.amount) || player.capabilities.isCreativeMode)
            {
                if (ServerHelper.isClientWorld(world))
                {
                    return true;
                }
                handler.fill(ForgeDirection.UNKNOWN, fluid, true);
                if (!player.capabilities.isCreativeMode)
                {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemHelper.consumeItem(container));
                }
                return true;
            }
        }
        return false;
    }

    public static boolean fillContainerFromTank(World world, IFluidHandler handler, EntityPlayer player, FluidStack tankFluid)
    {
        ItemStack container = player.getCurrentEquippedItem();
        if (FluidContainerRegistry.isEmptyContainer(container))
        {
            ItemStack returnStack = FluidContainerRegistry.fillFluidContainer(tankFluid, container);
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(returnStack);
            if ((fluid == null) || (returnStack == null))
            {
                return false;
            }
            if (!player.capabilities.isCreativeMode)
            {
                if (container.stackSize == 1)
                {
                    container = container.copy();
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, returnStack);
                } else if (!player.inventory.addItemStackToInventory(returnStack))
                {
                    return false;
                }
                handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
                container.stackSize--;
                if (container.stackSize <= 0)
                {
                    container = null;
                }
            } else
            {
                handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
            }
            return true;
        }
        return false;
    }

    public static int fillAdjacentFluidHandler(TileEntity tile, int from, FluidStack fluid, boolean doFill)
    {
        TileEntity handler = BlockHelper.getAdjacentTileEntity(tile, from);
        if (handler instanceof IFluidHandler)
        {
            return ((IFluidHandler) handler).fill(ForgeDirection.VALID_DIRECTIONS[from].getOpposite(), fluid, doFill);
        }
        return 0;
    }

    public static boolean isFluidContainerItem(ItemStack container)
    {
        return (container != null) && (container.getItem() instanceof IFluidContainerItem);
    }

    public static boolean isAdjacentFluidHandler(TileEntity tile, int from)
    {
        return BlockHelper.getAdjacentTileEntity(tile, from) instanceof IFluidHandler;
    }

    public static boolean isFluidHandler(TileEntity tile)
    {
        return tile instanceof IFluidHandler;
    }

    public static int getFluidLuminosity(Fluid fluid)
    {
        return fluid == null ? 0 : fluid.getLuminosity();
    }

    public static int getFluidLuminosity(int fluidId)
    {
        return getFluidLuminosity(FluidRegistry.getFluid(fluidId));
    }

    public static int getFluidLuminosity(FluidStack fluid)
    {
        return fluid == null ? 0 : getFluidLuminosity(fluid.getFluid());
    }

    public static FluidStack getFluidFromCoords(World worldObj, int x, int y, int z)
    {
        int bId = worldObj.getBlockId(x, y, z);
        int bMeta = worldObj.getBlockMetadata(x, y, z);
        if ((bId == 9) || (bId == 8))
        {
            if (bMeta == 0)
            {
                return new FluidStack(FluidRegistry.WATER, 1000);
            }
            return null;
        } else if ((bId == 10) || (bId == 11))
        {
            if (bMeta == 0)
            {
                return new FluidStack(FluidRegistry.LAVA, 1000);
            }
            return null;
        } else if ((Block.blocksList[bId] != null) && (Block.blocksList[bId] instanceof IFluidBlock))
        {
            IFluidBlock block = (IFluidBlock) Block.blocksList[bId];
            return block.drain(worldObj, x, y, z, true);
        }
        return null;
    }

    public static void writeFluidStackToPacket(FluidStack theFluid, DataOutput data) throws IOException
    {
        if ((theFluid == null) || (theFluid.fluidID == 0))
        {
            data.writeShort(-1);
        } else
        {
            byte[] abyte = CompressedStreamTools.compress(theFluid.writeToNBT(new NBTTagCompound()));
            data.writeShort((short) abyte.length);
            data.write(abyte);
        }
    }

    public static FluidStack readFluidStackFromPacket(DataInput data) throws IOException
    {
        short length = data.readShort();
        if (length < 0)
        {
            return null;
        }
        byte[] abyte = new byte[length];
        data.readFully(abyte);
        return FluidStack.loadFluidStackFromNBT(CompressedStreamTools.decompress(abyte));
    }
}