package ccm.nucleum_omnium.block.sub;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ccm.nucleum_omnium.handler.GUIHandler;
import ccm.nucleum_omnium.handler.LogHandler;
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

	public SubBlock setTileEntity(final TileEntity te) {
		if (te != null) {
			this.te = te.getClass();
			if (te instanceof BaseTE) {
				if (((BaseTE) te).hasLogic()) {
					logic = ((BaseTE) te).getSrcLogic();
				}
				if (te instanceof InventoryTE) {
					hasInv = true;
					size = ((InventoryTE) te).getSizeInventory();
				}
			}
			hasTE = true;
		} else {
			LogHandler.log("te was Null!! @ setTileEntity");
		}
		return this;
	}

	@Override
	public boolean hasTileEntity(final int meta) {
		return hasTE;
	}

	@Override
	public TileEntity createTileEntity(final World world, final int meta) {
		if (logic != null) {
			try {
				LogHandler.log("Creating TE: " + te + "\n With Logic: " + logic);
				if (hasInv) {
					return ((InventoryTE) ((BaseTE) te.newInstance()).setLogic(logic)).setInventorySize(size);
				} else {
					return ((BaseTE) te.newInstance()).setLogic(logic);
				}
			} catch (final Exception e) {
				LogHandler.log("TileEntity Instance with logic could not be created during createTileEntity \n");
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				LogHandler.log("Creating TE: " + te);
				if (hasInv) {
					return ((InventoryTE) te.newInstance()).setInventorySize(size);
				} else {
					return te.newInstance();
				}
			} catch (final Exception e) {
				LogHandler.log("TileEntity Instance could not be created during createTileEntity \n");
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public void breakBlock(	final World world,
							final int x,
							final int y,
							final int z,
							final int id,
							final int meta) {
		if (hasTileEntity(meta)) {
			try {
				if (te.newInstance() instanceof IInventory) {
					FunctionHelper.dropInventory(world, x, y, z);
				}
			} catch (final Exception e) {
				LogHandler.log("TileEntity Instance could not be created during breakBlock \n");
				e.printStackTrace();
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
			GUIHandler.openGui(FunctionHelper.getTEName(world, x, y, z), player, world, x, y, z);
			return true;
		} else {
			LogHandler.log(String.format("TileEntity at %s, %s, %s, was null", x, y, z));
			return false;
		}
	}
}