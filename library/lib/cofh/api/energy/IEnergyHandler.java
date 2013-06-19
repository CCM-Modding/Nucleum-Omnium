package lib.cofh.api.energy;

import net.minecraftforge.common.ForgeDirection;

/**
 * Implement this interface on TileEntities which should handle energy,
 * generally storing it in one or more internal {@link IEnergyStorage} objects.
 * A reference implementation is provided {@link TileEnergyHandler}.
 * 
 * @author King Lemming
 */
public interface IEnergyHandler {
    
    /**
     * Add energy to an IEnergyHandler, internal distribution is left entirely
     * to the IEnergyHandler.
     * 
     * @param from
     *            Orientation the energy is received from.
     * @param maxReceive
     *            Maximum amount of energy to received.
     * @param doReceive
     *            If false, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated)
     *         received.
     */
    int receiveEnergy(ForgeDirection from, int maxReceive, boolean doReceive);
    
    /**
     * Returns true if energy can be received from the given direction.
     */
    boolean canReceiveEnergy(ForgeDirection from);
    
    /**
     * Returns true if the IEnergyHandler sends energy in the given direction.
     * If energy is provided/produced on a given side should return true for
     * that side.
     */
    boolean canSendEnergy(ForgeDirection from);
    
    /**
     * Returns the amount of energy currently stored.
     */
    int getEnergyStored(ForgeDirection from);
    
    /**
     * Returns the maximum amount of energy that can be stored.
     */
    int getMaxEnergyStored(ForgeDirection from);
    
}
