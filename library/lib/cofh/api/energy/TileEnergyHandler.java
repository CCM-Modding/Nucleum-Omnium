package lib.cofh.api.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEnergyHandler extends TileEntity implements IEnergyHandler {

    protected EnergyStorage storage = new EnergyStorage(32000);

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        this.storage.writeToNBT(nbt);
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {

        super.writeToNBT(nbt);
        this.storage.readFromNBT(nbt);
    }

    /* IEnergyHandler */
    @Override
    public int receiveEnergy(final ForgeDirection from, final int maxReceive,
            final boolean doReceive) {

        return this.storage.receiveEnergy(maxReceive, doReceive);
    }

    @Override
    public boolean canReceiveEnergy(final ForgeDirection from) {

        return true;
    }

    @Override
    public boolean canSendEnergy(final ForgeDirection from) {

        return false;
    }

    @Override
    public int getEnergyStored(final ForgeDirection from) {

        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(final ForgeDirection from) {

        return this.storage.getMaxEnergyStored();
    }

}
