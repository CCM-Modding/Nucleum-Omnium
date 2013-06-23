package ccm.nucleum_omnium.block.sub;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ccm.nucleum_omnium.handler.GUIHandler;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.helper.FunctionHelper;
import ccm.nucleum_omnium.tileentity.BaseTE;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum_omnium.utils.lib.BlockFacings;

public class SBWithTile extends SBMutlyTexture {
    
    private Class<? extends TileEntity> te    = null;
    private Class<? extends ITileLogic> logic = null;
    private boolean                     hasTE = false;
    
    public SBWithTile(final int id, final int meta, final String iconName, final List<BlockFacings> goodSides) {
        super(id, meta, iconName, goodSides);
    }
    
    public SBWithTile(final int id, final int meta, final Material material, final String iconName, List<BlockFacings> goodSides) {
        super(id, meta, material, iconName, goodSides);
    }
    
    public SubBlock setTileEntity(final Class<? extends TileEntity> te) {
        if (te != null) {
            this.te = te;
            hasTE = true;
        }
        return this;
    }
    
    public SubBlock setTileEntity(final Class<? extends TileEntity> te, final Class<? extends ITileLogic> logic) {
        if (te != null) {
            this.te = te;
            this.logic = logic;
            hasTE = true;
        }
        return this;
    }
    
    @Override
    public TileEntity createTileEntity(final World world, final int meta) {
        if (logic != null) {
            try {
                return ((BaseTE) te.newInstance()).setLogic(logic);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                return ((BaseTE) te.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    
    public boolean hasTileEntity() {
        return hasTE;
    }
    
    @Override
    public void breakBlock(final World world, final int x, final int y, final int z, final int id, final int meta) {
        if (hasTileEntity()) {
            try {
                if (te.newInstance() instanceof IInventory) {
                    FunctionHelper.dropInventory(world, x, y, z);
                }
            } catch (InstantiationException | IllegalAccessException e) {
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
            System.out.println(te);
            System.out.println(player.getEntityName());
            System.out.println(x);
            System.out.println(y);
            System.out.println(z);
            GUIHandler.openGui(FunctionHelper.getTEName(world, x, y, z), player, world, x, y, z);
            return true;
        } else {
            Handler.log(String.format("TileEntity at %s, %s, %s, was null", x, y, z));
            return false;
        }
    }
}