package lib.cofh.util;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Contains various helper functions to assist with {@link Block} and
 * Block-related manipulation and interaction.
 * 
 * @author King Lemming
 */
public final class BlockHelper {
    
    private BlockHelper() {
        
    }
    
    public static byte[]        rotateType     = new byte[4096];
    public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 },
            { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
    public static final int[]   SIDE_LEFT      = { 4, 5, 5, 4, 2, 3 };
    public static final int[]   SIDE_RIGHT     = { 5, 4, 4, 5, 3, 2 };
    public static final int[]   SIDE_OPPOSITE  = { 1, 0, 3, 2, 5, 4 };
    public static final int[]   SIDE_ABOVE     = { 2, 3, 1, 1, 1, 1 };
    public static final int[]   SIDE_BELOW     = { 3, 2, 0, 0, 0, 0 };
    
    public static final class RotationType {
        
        public static final int FOUR_WAY = 1;
        public static final int SIX_WAY  = 2;
        public static final int RAIL     = 3;
        public static final int PUMPKIN  = 4;
        public static final int STAIRS   = 5;
        public static final int REDSTONE = 6;
        public static final int LOG      = 7;
        public static final int SLAB     = 8;
        public static final int CHEST    = 9;
        public static final int LEVER    = 10;
        public static final int SIGN     = 11;
    }
    
    static {
        rotateType[Block.wood.blockID] = RotationType.LOG;
        rotateType[Block.dispenser.blockID] = RotationType.SIX_WAY;
        rotateType[Block.railPowered.blockID] = RotationType.RAIL;
        rotateType[Block.railDetector.blockID] = RotationType.RAIL;
        rotateType[Block.pistonStickyBase.blockID] = RotationType.SIX_WAY;
        rotateType[Block.pistonBase.blockID] = RotationType.SIX_WAY;
        rotateType[Block.stoneSingleSlab.blockID] = RotationType.SLAB;
        rotateType[Block.stairsWoodOak.blockID] = RotationType.STAIRS;
        rotateType[Block.chest.blockID] = RotationType.CHEST;
        rotateType[Block.furnaceIdle.blockID] = RotationType.FOUR_WAY;
        rotateType[Block.furnaceBurning.blockID] = RotationType.FOUR_WAY;
        rotateType[Block.signPost.blockID] = RotationType.SIGN;
        rotateType[Block.rail.blockID] = RotationType.RAIL;
        rotateType[Block.stairsCobblestone.blockID] = RotationType.STAIRS;
        rotateType[Block.lever.blockID] = RotationType.LEVER;
        rotateType[Block.pumpkin.blockID] = RotationType.PUMPKIN;
        rotateType[Block.pumpkinLantern.blockID] = RotationType.PUMPKIN;
        rotateType[Block.redstoneRepeaterIdle.blockID] = RotationType.REDSTONE;
        rotateType[Block.redstoneRepeaterActive.blockID] = RotationType.REDSTONE;
        rotateType[Block.stairsBrick.blockID] = RotationType.STAIRS;
        rotateType[Block.stairsStoneBrick.blockID] = RotationType.STAIRS;
        rotateType[Block.stairsNetherBrick.blockID] = RotationType.STAIRS;
        rotateType[Block.woodSingleSlab.blockID] = RotationType.SLAB;
        rotateType[Block.stairsSandStone.blockID] = RotationType.STAIRS;
        rotateType[Block.enderChest.blockID] = RotationType.FOUR_WAY;
        rotateType[Block.stairsWoodSpruce.blockID] = RotationType.STAIRS;
        rotateType[Block.stairsWoodBirch.blockID] = RotationType.STAIRS;
        rotateType[Block.stairsWoodJungle.blockID] = RotationType.STAIRS;
        rotateType[Block.stairsNetherQuartz.blockID] = RotationType.STAIRS;
        rotateType[Block.hopperBlock.blockID] = RotationType.SIX_WAY;
        rotateType[Block.railActivator.blockID] = RotationType.RAIL;
        rotateType[Block.dropper.blockID] = RotationType.SIX_WAY;
    }
    
    public static int getCurrentMousedOverSide(final World world, final EntityPlayer player) {
        
        final double distance = player.capabilities.isCreativeMode ? 5.0F : 4.5F;
        final Vec3 posVec = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
        Vec3 lookVec = player.getLook(1);
        posVec.yCoord += player.getEyeHeight();
        lookVec = posVec.addVector(lookVec.xCoord * distance,
                                   lookVec.yCoord * distance,
                                   lookVec.zCoord * distance);
        final MovingObjectPosition mouseOver = player.worldObj.rayTraceBlocks(posVec, lookVec);
        
        if (mouseOver != null) {
            return mouseOver.sideHit;
        }
        return 0;
    }
    
    public static TileEntity getAdjacentTileEntity(final World world, final int x, final int y,
            final int z, final ForgeDirection dir) {
        
        return world.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
    }
    
    public static TileEntity getAdjacentTileEntity(final World world, final int x, final int y,
            final int z, final int side) {
        
        return getAdjacentTileEntity(world, x, y, z, ForgeDirection.values()[side]);
    }
    
    public static TileEntity getAdjacentTileEntity(final TileEntity refTile,
            final ForgeDirection dir) {
        
        return getAdjacentTileEntity(refTile.worldObj,
                                     refTile.xCoord,
                                     refTile.yCoord,
                                     refTile.zCoord,
                                     dir);
    }
    
    public static TileEntity getAdjacentTileEntity(final TileEntity refTile, final int side) {
        
        return getAdjacentTileEntity(refTile.worldObj,
                                     refTile.xCoord,
                                     refTile.yCoord,
                                     refTile.zCoord,
                                     ForgeDirection.values()[side]);
    }
    
    /* COORDINATE TRANSFORM */
    public static int[] getAdjacentCoordinatesForSide(final int x, final int y, final int z,
            final int side) {
        
        return new int[] { x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1],
                z + SIDE_COORD_MOD[side][2] };
    }
    
    public static int[] getAdjacentCoordinatesForSide(final TileEntity tile, final int side) {
        
        return new int[] { tile.xCoord + SIDE_COORD_MOD[side][0],
                tile.yCoord + SIDE_COORD_MOD[side][1], tile.zCoord + SIDE_COORD_MOD[side][2] };
    }
    
    public static int getLeftSide(final int side) {
        
        return SIDE_LEFT[side];
    }
    
    public static int getRightSide(final int side) {
        
        return SIDE_RIGHT[side];
    }
    
    public static int getOppositeSide(final int side) {
        
        return SIDE_OPPOSITE[side];
    }
    
    public static int getAboveSide(final int side) {
        
        return SIDE_ABOVE[side];
    }
    
    public static int getBelowSide(final int side) {
        
        return SIDE_BELOW[side];
    }
    
    /* BLOCK ROTATION */
    public static boolean canRotate(final int blockId) {
        
        return rotateType[blockId] != 0;
    }
    
    public static int rotateVanillaBlock(final World world, final int bId, int bMeta, final int x,
            final int y, final int z) {
        
        switch (rotateType[bId]) {
            case RotationType.FOUR_WAY:
                return SIDE_LEFT[bMeta];
            case RotationType.SIX_WAY:
                if (bMeta < 6) {
                    return ++bMeta % 6;
                }
                return bMeta;
            case RotationType.RAIL:
                if (bMeta < 2) {
                    return ++bMeta % 2;
                }
                return bMeta;
            case RotationType.PUMPKIN:
                return ++bMeta % 4;
            case RotationType.STAIRS:
                return ++bMeta % 8;
            case RotationType.REDSTONE:
                final int upper = bMeta & 0xC;
                int lower = bMeta & 0x3;
                return upper + (++lower % 4);
            case RotationType.LOG:
                return (bMeta + 4) % 12;
            case RotationType.SLAB:
                return (bMeta + 8) % 16;
            case RotationType.CHEST:
                int coords[] = new int[3];
                for (int i = 2; i < 6; ++i) {
                    coords = getAdjacentCoordinatesForSide(x, y, z, i);
                    if (world.getBlockId(coords[0], coords[1], coords[2]) == Block.chest.blockID) {
                        world.setBlockMetadataWithNotify(coords[0],
                                                         coords[1],
                                                         coords[2],
                                                         SIDE_OPPOSITE[bMeta],
                                                         1);
                        return SIDE_OPPOSITE[bMeta];
                    }
                }
                return SIDE_LEFT[bMeta];
            case RotationType.LEVER:
                int shift = 0;
                if (bMeta > 7) {
                    bMeta -= 8;
                    shift = 8;
                }
                if (bMeta == 5) {
                    return 6 + shift;
                } else if (bMeta == 6) {
                    return 5 + shift;
                } else if (bMeta == 7) {
                    return 0 + shift;
                } else if (bMeta == 0) {
                    return 7 + shift;
                }
                return bMeta + shift;
            case RotationType.SIGN:
                return ++bMeta % 16;
            default:
                return bMeta;
        }
    }
    
    public static int rotateVanillaBlockAlt(final World world, final int bId, int bMeta,
            final int x, final int y, final int z) {
        
        switch (rotateType[bId]) {
            case RotationType.FOUR_WAY:
                return SIDE_RIGHT[bMeta];
            case RotationType.SIX_WAY:
                if (bMeta < 6) {
                    return (bMeta + 5) % 6;
                }
                return bMeta;
            case RotationType.RAIL:
                if (bMeta < 2) {
                    return ++bMeta % 2;
                }
                return bMeta;
            case RotationType.PUMPKIN:
                return (bMeta + 3) % 4;
            case RotationType.STAIRS:
                return (bMeta + 7) % 8;
            case RotationType.REDSTONE:
                final int upper = bMeta & 0xC;
                final int lower = bMeta & 0x3;
                return upper + ((lower + 3) % 4);
            case RotationType.LOG:
                return (bMeta + 8) % 12;
            case RotationType.SLAB:
                return (bMeta + 8) % 16;
            case RotationType.CHEST:
                int coords[] = new int[3];
                for (int i = 2; i < 6; ++i) {
                    coords = getAdjacentCoordinatesForSide(x, y, z, i);
                    if (world.getBlockId(coords[0], coords[1], coords[2]) == Block.chest.blockID) {
                        world.setBlockMetadataWithNotify(coords[0],
                                                         coords[1],
                                                         coords[2],
                                                         SIDE_OPPOSITE[bMeta],
                                                         1);
                        return SIDE_OPPOSITE[bMeta];
                    }
                }
                return SIDE_RIGHT[bMeta];
            case RotationType.LEVER:
                int shift = 0;
                if (bMeta > 7) {
                    bMeta -= 8;
                    shift = 8;
                }
                if (bMeta == 5) {
                    return 6 + shift;
                } else if (bMeta == 6) {
                    return 5 + shift;
                } else if (bMeta == 7) {
                    return 0 + shift;
                } else if (bMeta == 0) {
                    return 7 + shift;
                }
            case RotationType.SIGN:
                return ++bMeta % 16;
            default:
                return bMeta;
        }
    }
    
    public static boolean isBlock(final int bId) {
        
        return (bId < 0) || (bId >= Block.blocksList.length) ? null : Block.blocksList[bId] != null;
    }
    
    public static List<ItemStack> breakBlock(final World worldObj, final int x, final int y,
            final int z, final int blockId, final int fortune, final boolean doBreak) {
        
        if (Block.blocksList[blockId].getBlockHardness(worldObj, x, y, z) == -1) {
            return new LinkedList<ItemStack>();
        }
        final int meta = worldObj.getBlockMetadata(x, y, z);
        final List<ItemStack> stacks = Block.blocksList[blockId].getBlockDropped(worldObj,
                                                                                 x,
                                                                                 y,
                                                                                 z,
                                                                                 meta,
                                                                                 fortune);
        
        if (!doBreak) {
            return stacks;
        }
        worldObj.playAuxSFXAtEntity(null, 2001, x, y, z, blockId + (meta << 12));
        worldObj.setBlock(x, y, z, 0);
        
        final List<?> result = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB
                .getBoundingBox(x - 2, y - 2, z - 2, x + 3, y + 3, z + 3));
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) instanceof EntityItem) {
                final EntityItem entity = (EntityItem) result.get(i);
                if (entity.isDead || (entity.getEntityItem().stackSize <= 0)) {
                    continue;
                }
                stacks.add(entity.getEntityItem());
                entity.worldObj.removeEntity(entity);
            }
        }
        return stacks;
    }
    
}
