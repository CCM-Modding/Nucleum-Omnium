package ccm.nucleum_omnium.world.utils;

import java.io.Serializable;

import net.minecraft.world.chunk.Chunk;

public class ChunkCoord implements Serializable
{

    /**
     * Auto Generated Serial ID
     */
    private static final long serialVersionUID = -1112303230500382403L;

    public final int          chunkX;

    public final int          chunkZ;

    public ChunkCoord(final Chunk chunk)
    {
        this.chunkX = chunk.xPosition;
        this.chunkZ = chunk.zPosition;
    }

    public ChunkCoord(final int x,
                      final int z)
    {
        this.chunkX = x;
        this.chunkZ = z;
    }

    public boolean equals(final ChunkCoord chunk)
    {
        return (chunk.chunkX == this.chunkX) && (chunk.chunkZ == this.chunkZ);
    }

    public int getCenterX()
    {
        return (this.chunkX << 4) + 8;
    }

    public int getCenterZ()
    {
        return (this.chunkZ << 4) + 8;
    }

    @Override
    public String toString()
    {
        return "[" + this.chunkX + ", " + this.chunkZ + "]";
    }
}