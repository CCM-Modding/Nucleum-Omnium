/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.tileentity.helpers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import ccm.nucleum_omnium.tileentity.BaseTE;
import ccm.nucleum_omnium.utils.lib.TileConstants;

public final class BaseTEData
{

    /**
     * The orientation of the {@link TileEntity}
     */
    private ForgeDirection orientation;

    /**
     * The owner of the {@link TileEntity}
     */
    private String         owner;

    /**
     * The custom name of the {@link TileEntity}
     */
    private String         customName;

    private final BaseTE   tile;

    public BaseTEData(final BaseTE tile)
    {
        orientation = ForgeDirection.SOUTH;
        owner = "";
        customName = "";
        this.tile = tile;
    }

    @Override
    public BaseTEData clone()
    {
        final BaseTEData tmp = new BaseTEData(tile);
        tmp.setCustomName(getCustomName());
        tmp.setOrientation(getOrientation());
        tmp.setOwner(getOwner());
        return tmp;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof BaseTEData))
        {
            return false;
        }
        final BaseTEData other = (BaseTEData) obj;
        if (customName == null)
        {
            if (other.customName != null)
            {
                return false;
            }
        } else if (!customName.equals(other.customName))
        {
            return false;
        }
        if (orientation != other.orientation)
        {
            return false;
        }
        if (owner == null)
        {
            if (other.owner != null)
            {
                return false;
            }
        } else if (!owner.equals(other.owner))
        {
            return false;
        }
        if (tile == null)
        {
            if (other.tile != null)
            {
                return false;
            }
        } else if (!tile.equals(other.tile))
        {
            return false;
        }
        return true;
    }

    /**
     * Gets the {@link TileEntity}'s Custom Name
     * 
     * @return the {@link TileEntity}'s Custom Name
     */
    public String getCustomName()
    {
        return customName;
    }

    /**
     * Gets the {@link TileEntity}'s Orientation
     * 
     * @return The {@link TileEntity}'s Orientation
     */
    public ForgeDirection getOrientation()
    {
        return orientation;
    }

    /**
     * Gets the {@link TileEntity}'s Owner
     * 
     * @return The {@link TileEntity}'s Owner
     */
    public String getOwner()
    {
        return owner;
    }

    /**
     * Checks if the {@link TileEntity} has a Custom Name
     * 
     * @return true if the {@link TileEntity} has a Custom Name
     */
    public boolean hasCustomName()
    {
        return (customName != null) && (customName.length() > 0);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((customName == null) ? 0 : customName.hashCode());
        result = (prime * result) + ((orientation == null) ? 0 : orientation.hashCode());
        result = (prime * result) + ((owner == null) ? 0 : owner.hashCode());
        result = (prime * result) + ((tile == null) ? 0 : tile.hashCode());
        return result;
    }

    /**
     * Checks if the {@link TileEntity} has a Owner
     * 
     * @return true if the {@link TileEntity} has a Owner
     */
    public boolean hasOwner()
    {
        return (owner != null) && (owner.length() > 0);
    }

    /**
     * Reads the variables from NBT
     */
    public void readFromNBT(final NBTTagCompound nbt)
    {
        if (nbt.hasKey(TileConstants.NBT_TE_DIRECTION))
        {
            orientation = ForgeDirection.getOrientation(nbt.getByte(TileConstants.NBT_TE_DIRECTION));
        }
        if (nbt.hasKey(TileConstants.NBT_TE_OWNER))
        {
            owner = nbt.getString(TileConstants.NBT_TE_OWNER);
        }
        if (nbt.hasKey(TileConstants.NBT_TE_CUSTOM_NAME))
        {
            customName = nbt.getString(TileConstants.NBT_TE_CUSTOM_NAME);
        }
    }

    /**
     * Sets the {@link TileEntity}'s Custom Name
     * 
     * @param customName
     *            A {@link String} with the {@link TileEntity}'s Custom Name
     */
    public void setCustomName(final String customName)
    {
        this.customName = customName;
    }

    /**
     * Sets the {@link TileEntity}'s Orientation
     */
    public void setOrientation(final ForgeDirection orientation)
    {
        this.orientation = orientation;
    }

    /**
     * Sets the {@link TileEntity}'s Orientation to a {@link ForgeDirection} Direction
     * 
     * @param direction
     *            The {@link ForgeDirection}.ordinal()
     */
    public void setOrientation(final int direction)
    {
        orientation = ForgeDirection.getOrientation(direction);
    }

    /**
     * Sets the Owner of the {@link TileEntity}.
     * 
     * @param owner
     *            A {@link String} with the Owner's Name
     */
    public void setOwner(final String owner)
    {
        this.owner = owner;
    }

    /**
     * Writes the variables to NBT
     */
    public void writeToNBT(final NBTTagCompound nbt)
    {
        nbt.setByte(TileConstants.NBT_TE_DIRECTION, (byte) orientation.ordinal());
        if (hasOwner())
        {
            nbt.setString(TileConstants.NBT_TE_OWNER, owner);
        }
        if (hasCustomName())
        {
            nbt.setString(TileConstants.NBT_TE_CUSTOM_NAME, customName);
        }
    }
}