package ccm.nucleum_omnium.block.sub;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_omnium.handler.GUIHandler;
import ccm.nucleum_omnium.handler.LogHandler;
import ccm.nucleum_omnium.helper.BlockHelper;
import ccm.nucleum_omnium.helper.FunctionHelper;
import ccm.nucleum_omnium.tileentity.BaseTE;
import ccm.nucleum_omnium.tileentity.InventoryTE;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum_omnium.utils.lib.BlockFacings;

public class SBWithTile extends SBMutlyTexture {

	private Class<? extends TileEntity>	te		= null;

	private Class<? extends ITileLogic>	logic	= null;

	private boolean						hasTE	= false;

	private boolean						hasInv	= false;

	private int							size;

	public SBWithTile(final int id, final int meta, final String iconName, final List<BlockFacings> goodSides) {
		super(id, meta, iconName, goodSides);
	}

	public SBWithTile(	final int id,
						final int meta,
						final Material material,
						final String iconName,
						final List<BlockFacings> goodSides) {
		super(id, meta, material, iconName, goodSides);
	}

	public SBWithTile setTileEntity(final TileEntity te) {
		if (te != null) {
			this.te = te.getClass();
			if (te instanceof BaseTE) {
				if (((BaseTE) te).hasLogic()) {
					logic = ((BaseTE) te).getSrcLogic();
					LogHandler.log("Block: %s Tile has Logic \n", iconName);
				}
				if (te instanceof InventoryTE) {
					hasInv = true;
					size = ((InventoryTE) te).getSizeInventory();
					LogHandler.log("Block: %s Tile is an InventoryTE \n", iconName);
				}
			}
			hasTE = true;
		} else {
			LogHandler.log("te was Null!! @ setTileEntity \n");
		}
		return this;
	}

	@Override
	public boolean hasTileEntity(final int meta) {
		return hasTE;
	}

	public boolean hasInventory() {
		return hasInv;
	}

	@Override
	public TileEntity createTileEntity(final World world, final int meta) {
		if (logic != null) {
			try {
				LogHandler.log("Creating TE: %s \nWith Logic: %s \n", te, logic);
				if (hasInventory()) {
					return ((InventoryTE) ((BaseTE) te.newInstance()).setLogic(logic)).setInventorySize(size);
				} else {
					return ((BaseTE) te.newInstance()).setLogic(logic);
				}
			} catch (final Exception e) {
				LogHandler.log("TileEntity Instance with logic could not be created during createTileEntity \n");
				e.getCause();
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				LogHandler.log("Creating TE: %s \n", te);
				if (hasInventory()) {
					return ((InventoryTE) te.newInstance()).setInventorySize(size);
				} else {
					return te.newInstance();
				}
			} catch (final Exception e) {
				LogHandler.log("TileEntity Instance could not be created during createTileEntity \n");
				e.getCause();
				e.printStackTrace();
				return null;
			}
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
		if (world.isRemote) {
			return true;
		}
		if (player.isSneaking()) {
			return false;
		}
		final BaseTE te = (BaseTE) world.getBlockTileEntity(x, y, z);
		if (te != null) {
			LogHandler.log(te);
			LogHandler.log(player.getEntityName());
			LogHandler.log(x);
			LogHandler.log(y);
			LogHandler.log(z);
			LogHandler.log(FunctionHelper.getTEName(world, x, y, z));
			LogHandler.log(getUnlocalizedName());
			GUIHandler.openGui(FunctionHelper.getTEName(world, x, y, z), player, world, x, y, z);
			return true;
		} else {
			LogHandler.log("TileEntity at %s, %s, %s, was null", x, y, z);
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(final World world,
								final int x,
								final int y,
								final int z,
								final EntityLiving living,
								final ItemStack itemStack) {
		BaseTE te = (BaseTE) world.getBlockTileEntity(x, y, z);
		int direction = 0;
		int facing = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0) {
			direction = ForgeDirection.NORTH.ordinal();
		} else if (facing == 1) {
			direction = ForgeDirection.EAST.ordinal();
		} else if (facing == 2) {
			direction = ForgeDirection.SOUTH.ordinal();
		} else if (facing == 3) {
			direction = ForgeDirection.WEST.ordinal();
		}
		BlockHelper.updateAdjacent(world, x, y, z, 3);

		if (itemStack.hasDisplayName()) {
			te.setCustomName(itemStack.getDisplayName());
		}

		te.setOrientation(direction);
	}
}