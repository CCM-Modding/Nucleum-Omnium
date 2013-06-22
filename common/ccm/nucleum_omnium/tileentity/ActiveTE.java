package ccm.nucleum_omnium.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ccm.nucleum_omnium.utils.lib.TileConstant;

public class ActiveTE extends InventoryTE {
    
    private boolean state;
    private boolean pastState;
    
    public ActiveTE(int invSize, String name) {
        super(invSize, name);
        state = false;
        pastState = false;
    }
    
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
        this.pastState = this.state;
        this.state = state;
    }
    
   /**
     * @param curActive
     *            The current state of the TileEntity
     */
    public void updateIfChanged(boolean curActive) {
        if (curActive != this.state) {
            pastState = !pastState;
            state = !state;
        }
    }
    
  /*@Override
    public Packet getDescriptionPacket() {
        
        Payload payload = new Payload(1, 0, 0, 0, 0);
        
        payload.boolPayload[0] = this.state;
        
        TilePkt packet = new TilePkt(descPacketId, this.xCoord, this.yCoord, this.zCoord, payload);
        return packet.getPacket();
    }*/
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(TileConstant.NBT_TE_PAST_State, pastState);
        nbtTagCompound.setBoolean(TileConstant.NBT_TE_State, state);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_PAST_State)) {
            pastState = nbtTagCompound.getBoolean(TileConstant.NBT_TE_PAST_State);
        }
        if (nbtTagCompound.hasKey(TileConstant.NBT_TE_State)) {
            state = nbtTagCompound.getBoolean(TileConstant.NBT_TE_State);
        }
    }
}