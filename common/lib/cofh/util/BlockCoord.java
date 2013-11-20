package lib.cofh.util;

import java.io.Serializable;

import net.minecraft.tileentity.TileEntity;

/**
 * Standardized implementation for representing and manipulating Block Coordinates. Provides standard Java Collection interaction.
 * 
 * @author King Lemming
 */
public final class BlockCoord implements Comparable<BlockCoord>, Serializable
{
    /**
     * long, serialVersionUID
     */
    private static final long serialVersionUID = 1796418177054537746L;
    public int x;
    public int y;
    public int z;

    public BlockCoord(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockCoord(TileEntity tile)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }

    public void step(int dir)
    {
        x += BlockHelper.SIDE_COORD_MOD[dir][0];
        y += BlockHelper.SIDE_COORD_MOD[dir][1];
        z += BlockHelper.SIDE_COORD_MOD[dir][2];
    }

    public void step(int dir, int dist)
    {
        switch (dir)
        {
            case 0:
                y -= dist;
                break;
            case 1:
                y += dist;
                break;
            case 2:
                z -= dist;
                break;
            case 3:
                z += dist;
                break;
            case 4:
                x -= dist;
                break;
            case 5:
                x += dist;
                break;
            default:
        }
    }

    public BlockCoord copy()
    {
        return new BlockCoord(x, y, z);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BlockCoord))
        {
            return false;
        }
        BlockCoord other = (BlockCoord) obj;
        return (x == other.x) && (y == other.y) && (z == other.z);
    }

    @Override
    public int hashCode()
    {
        int hash = x;
        hash *= 31 + y;
        hash *= 31 + z;
        return hash;
    }

    @Override
    public String toString()
    {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    /* Comparable */
    @Override
    public int compareTo(BlockCoord other)
    {
        return x == other.x ? y == other.y ? z - other.z : y - other.y : x - other.x;
    }
}