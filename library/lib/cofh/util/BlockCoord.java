package lib.cofh.util;

import java.io.Serializable;

import net.minecraft.tileentity.TileEntity;

/**
 * Standardized implementation for representing and manipulating Block Coordinates. Provides
 * standard Java Collection interaction.
 * 
 * @author King Lemming
 */
public final class BlockCoord implements Comparable<BlockCoord>, Serializable {

	/**
     * 
     */
	private static final long	serialVersionUID	= -6806068729928163065L;
	public int					x;
	public int					y;
	public int					z;

	public BlockCoord(final int x, final int y, final int z) {

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockCoord(final TileEntity tile) {

		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
	}

	public void step(final int dir) {

		x += BlockHelper.SIDE_COORD_MOD[dir][0];
		y += BlockHelper.SIDE_COORD_MOD[dir][1];
		z += BlockHelper.SIDE_COORD_MOD[dir][2];
	}

	public void step(final int dir, final int dist) {

		switch (dir) {
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

	public BlockCoord copy() {

		return new BlockCoord(x, y, z);
	}

	@Override
	public boolean equals(final Object obj) {

		if (!(obj instanceof BlockCoord)) {
			return false;
		}
		final BlockCoord other = (BlockCoord) obj;
		return (x == other.x) && (y == other.y) && (z == other.z);
	}

	@Override
	public int hashCode() {

		int hash = x;
		hash *= 31 + y;
		hash *= 31 + z;
		return hash;
	}

	@Override
	public String toString() {

		return "[" + x + ", " + y + ", " + z + "]";
	}

	/* Comparable */
	@Override
	public int compareTo(final BlockCoord other) {

		return x == other.x ? y == other.y ? z - other.z : y - other.y : x - other.x;
	}

}
