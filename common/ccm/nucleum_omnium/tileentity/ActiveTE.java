package ccm.nucleum_omnium.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum_omnium.utils.lib.TileConstants;

public class ActiveTE extends LogicTE {

    private boolean state = false;

    /**
     * Gets the {@link TileEntity}'s State.
     * 
     * @return The {@link TileEntity}'s State.
     */
    public boolean getState() {
        return state;
    }

    /**
     * Sets the {@link TileEntity}'s State to a {@code boolean} value.
     * 
     * @param state
     *            The {@code boolean} State value, that you want the {@link TileEntity} to have.
     * @return state The {@code boolean} State value.
     */
    public void setState(final boolean state) {
        this.state = state;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(TileConstants.NBT_TE_STATE, state);
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey(TileConstants.NBT_TE_STATE)) {
            state = nbtTagCompound.getBoolean(TileConstants.NBT_TE_STATE);
        }
    }
}