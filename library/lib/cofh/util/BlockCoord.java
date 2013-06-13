package lib.cofh.util;

import java.io.Serializable;

import net.minecraft.tileentity.TileEntity;

/**
 * Standardized implementation for representing and manipulating Block
 * Coordinates. Provides standard Java Collection interaction.
 * 
 * @author King Lemming
 */
public final class BlockCoord implements Comparable<BlockCoord>, Serializable {

    private static final long serialVersionUID = 1796418177054537746L;

    public int                x;

    public int                y;

    public int                z;

    public BlockCoord(final int x, final int y, final int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockCoord(final TileEntity tile) {

        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
    }

    public void step(final int dir) {

        this.x += BlockHelper.SIDE_COORD_MOD[dir][0];
        this.y += BlockHelper.SIDE_COORD_MOD[dir][1];
        this.z += BlockHelper.SIDE_COORD_MOD[dir][2];
    }

    public void step(final int dir, final int dist) {

        switch (dir) {
        case 0:
            this.y -= dist;
            break;
        case 1:
            this.y += dist;
            break;
        case 2:
            this.z -= dist;
            break;
        case 3:
            this.z += dist;
            break;
        case 4:
            this.x -= dist;
            break;
        case 5:
            this.x += dist;
            break;
        default:
        }
    }

    public BlockCoord copy() {

        return new BlockCoord(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(final Object obj) {

        if (!(obj instanceof BlockCoord))
            return false;
        final BlockCoord other = (BlockCoord) obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public int hashCode() {

        int hash = this.x;
        hash *= 31 + this.y;
        hash *= 31 + this.z;
        return hash;
    }

    @Override
    public String toString() {

        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    /* Comparable */
    @Override
    public int compareTo(final BlockCoord other) {

        return this.x == other.x ? this.y == other.y ? this.z - other.z : this.y - other.y : this.x
                - other.x;
    }

}
