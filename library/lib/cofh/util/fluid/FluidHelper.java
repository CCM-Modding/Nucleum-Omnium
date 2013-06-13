package lib.cofh.util.fluid;

/**
 * Contains various helper functions to assist with {@link Fluid} and
 * Fluid-related manipulation and interaction.
 * 
 * @author King Lemming
 */
public class FluidHelper {

    private FluidHelper() {
    }

    /*
     * public static boolean fillTankWithContainer(World world, IFluidHandler
     * handler, EntityPlayer player) { ItemStack container =
     * player.getCurrentEquippedItem(); FluidStack fluid =
     * FluidContainerRegistry.getFluidForFilledItem(container); if (fluid !=
     * null){ if ((handler.fill(ForgeDirection.UNKNOWN, fluid, false) ==
     * fluid.amount) || player.capabilities.isCreativeMode){ if
     * (ServerHelper.isClientWorld(world)){ return true; }
     * handler.fill(ForgeDirection.UNKNOWN, fluid, true); if
     * (!player.capabilities.isCreativeMode){
     * player.inventory.setInventorySlotContents(player.inventory.currentItem,
     * ItemHelper.consumeItem(container)); } return true; } } return false; }
     * public static boolean fillContainerFromTank(World world, IFluidHandler
     * handler, EntityPlayer player, FluidStack tankFluid) { ItemStack container
     * = player.getCurrentEquippedItem(); if
     * (FluidContainerRegistry.isEmptyContainer(container)){ ItemStack
     * returnStack = FluidContainerRegistry.fillFluidContainer(tankFluid,
     * container); FluidStack fluid =
     * FluidContainerRegistry.getFluidForFilledItem(returnStack); if ((fluid ==
     * null) || (returnStack == null)){ return false; } if
     * (!player.capabilities.isCreativeMode){ if (container.stackSize == 1){
     * container = container.copy();
     * player.inventory.setInventorySlotContents(player.inventory.currentItem,
     * returnStack); }else if
     * (!player.inventory.addItemStackToInventory(returnStack)){ return false; }
     * handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
     * container.stackSize--; if (container.stackSize <= 0){ container = null; }
     * }else{ handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true); }
     * return true; } return false; } public static int
     * fillAdjacentHandler(TileEntity tile, int from, FluidStack fluid, boolean
     * doFill) { TileEntity handler = BlockHelper.getAdjacentTileEntity(tile,
     * from); if (handler instanceof IFluidHandler){ return ((IFluidHandler)
     * handler).fill(ForgeDirection.VALID_DIRECTIONS[from].getOpposite(), fluid,
     * doFill); } return 0; } public static boolean isAdjacentHandler(TileEntity
     * tile, int from) { return BlockHelper.getAdjacentTileEntity(tile, from)
     * instanceof IFluidHandler; }
     */
}