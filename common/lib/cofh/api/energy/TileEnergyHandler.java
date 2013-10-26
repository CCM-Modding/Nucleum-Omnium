package lib.cofh.api.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Reference implementation of {@link IEnergyHandler}. Use/extend this or implement your own.
 * 
 * @author King Lemming
 */
public class TileEnergyHandler extends TileEntity implements IEnergyHandler
{

    protected EnergyStorage storage = new EnergyStorage(32000);

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {

        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {

        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
    }

    /* IEnergyHandler */
    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean doReceive)
    {

        return storage.receiveEnergy(maxReceive, doReceive);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean doExtract)
    {

        return storage.extractEnergy(maxExtract, doExtract);
    }

    @Override
    public boolean canInterface(ForgeDirection from)
    {

        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {

        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {

        return storage.getMaxEnergyStored();
    }
}