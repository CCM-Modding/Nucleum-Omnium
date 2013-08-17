/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.utils.lib.NBTConstants;

/**
 * ActiveTE
 * <p>
 * Default Implementation for a Tile Entity with an Inventory, Logic and an Active State
 * 
 * @author Captain_Shadows
 */
public class ActiveTE extends LogicTE
{

    /**
     * The current State of the {@link TileEntity}
     */
    private boolean state = false;

    /**
     * Gets the {@link TileEntity}'s State.
     * 
     * @return The {@link TileEntity}'s State.
     */
    public boolean getState()
    {
        return state;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey(NBTConstants.NBT_TE_STATE))
        {
            state = nbtTagCompound.getBoolean(NBTConstants.NBT_TE_STATE);
        }
    }

    /**
     * Sets the {@link TileEntity}'s State to a {@code boolean} value.
     * 
     * @param state
     *            The {@code boolean} State value, that you want the {@link TileEntity} to have.
     * @return state The {@code boolean} State value.
     */
    public void setState(final boolean state)
    {
        this.state = state;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(NBTConstants.NBT_TE_STATE, state);
    }
}