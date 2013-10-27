package lib.cofh.api.energy;

import net.minecraft.nbt.NBTTagCompound;

import lib.cofh.util.MathHelper;

/**
 * Reference implementation of {@link IEnergyStorage}. Use/extend this or implement your own.
 * 
 * @author King Lemming
 */
public class EnergyStorage implements IEnergyStorage
{
    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public EnergyStorage(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public EnergyStorage(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public EnergyStorage readFromNBT(NBTTagCompound nbt)
    {
        energy = nbt.getInteger("Energy");
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("Energy", energy);
        return nbt;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;

        if (energy > capacity)
        {
            energy = capacity;
        }
    }

    public void setMaxTransfer(int maxTransfer)
    {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
    }

    public void setMaxReceive(int maxReceive)
    {
        this.maxReceive = maxReceive;
    }

    public void setMaxExtract(int maxExtract)
    {
        this.maxExtract = maxExtract;
    }

    /**
     * This function is included to allow for server -> client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers are guaranteed to have
     * it.
     * 
     * @param energy
     */
    public void setEnergyStored(int energy)
    {
        this.energy = energy;

        if (this.energy > capacity)
        {
            this.energy = capacity;
        } else if (this.energy < 0)
        {
            this.energy = 0;
        }
    }

    /**
     * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the EnergyStorage. Do not rely on this externally, as not
     * all IEnergyHandlers are guaranteed to have it.
     * 
     * @param energy
     */
    public void modifyEnergyStored(int energy)
    {
        this.energy += energy;

        if (this.energy > capacity)
        {
            this.energy = capacity;
        } else if (this.energy < 0)
        {
            this.energy = 0;
        }
    }

    /* IEnergyStorage */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int energyReceived = MathHelper.minI(capacity - energy, MathHelper.minI(this.maxReceive, maxReceive));

        if (!simulate)
        {
            energy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        int energyExtracted = MathHelper.minI(energy, MathHelper.minI(this.maxExtract, maxExtract));

        if (!simulate)
        {
            energy -= energyExtracted;
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored()
    {
        return energy;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return capacity;
    }
}