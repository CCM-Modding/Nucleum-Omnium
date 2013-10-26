package lib.cofh.util;

import java.io.Serializable;

import net.minecraft.world.chunk.Chunk;

/**
 * Standardized implementation for representing and manipulating Chunk Coordinates. Provides standard Java Collection interaction.
 * 
 * @author King Lemming
 */
public final class ChunkCoord implements Comparable<ChunkCoord>, Serializable
{

    /**
     * long, serialVersionUID
     */
    private static final long serialVersionUID = -1483033546339863533L;
    public int chunkX;
    public int chunkZ;

    public ChunkCoord(Chunk chunk)
    {

        chunkX = chunk.xPosition;
        chunkZ = chunk.zPosition;
    }

    public ChunkCoord(BlockCoord c)
    {

        this(c.x >> 4, c.z >> 4);
    }

    public ChunkCoord(int x, int z)
    {

        chunkX = x;
        chunkZ = z;
    }

    public int getCenterX()
    {

        return (chunkX << 4) + 8;
    }

    public int getCenterZ()
    {

        return (chunkZ << 4) + 8;
    }

    public void step(int dir)
    {

        chunkX = BlockHelper.SIDE_COORD_MOD[dir][0];
        chunkZ = BlockHelper.SIDE_COORD_MOD[dir][2];
    }

    public void step(int dir, int dist)
    {

        switch (dir)
        {
            case 2:
                chunkZ -= dist;
                break;
            case 3:
                chunkZ += dist;
                break;
            case 4:
                chunkX -= dist;
                break;
            case 5:
                chunkX += dist;
                break;
            default:
        }
    }

    public ChunkCoord copy()
    {

        return new ChunkCoord(chunkX, chunkZ);
    }

    @Override
    public boolean equals(Object obj)
    {

        if (!(obj instanceof ChunkCoord))
        {
            return false;
        }
        ChunkCoord other = (ChunkCoord) obj;
        return (chunkX == other.chunkX) && (chunkZ == other.chunkZ);
    }

    @Override
    public int hashCode()
    {

        int hash = chunkX;
        hash *= 31 + chunkZ;
        return hash;
    }

    @Override
    public String toString()
    {

        return "[" + chunkX + ", " + chunkZ + "]";
    }

    /* Comparable */
    @Override
    public int compareTo(ChunkCoord other)
    {

        return chunkX == other.chunkX ? chunkZ - other.chunkZ : chunkX - other.chunkX;
    }
}