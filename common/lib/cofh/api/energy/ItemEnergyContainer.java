package lib.cofh.api.energy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import lib.cofh.util.MathHelper;

/**
 * Reference implementation of {@link IEnergyContainerItem}. Use/extend this or implement your own.
 * 
 * @author King Lemming
 */
public class ItemEnergyContainer extends Item implements IEnergyContainerItem
{

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public ItemEnergyContainer(int itemID)
    {

        super(itemID);
    }

    public ItemEnergyContainer(int itemID, int capacity)
    {

        this(itemID, capacity, capacity, capacity);
    }

    public ItemEnergyContainer(int itemID, int capacity, int maxTransfer)
    {

        this(itemID, capacity, maxTransfer, maxTransfer);
    }

    public ItemEnergyContainer(int itemID, int capacity, int maxReceive, int maxExtract)
    {

        super(itemID);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public ItemEnergyContainer setCapacity(int capacity)
    {

        this.capacity = capacity;
        return this;
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

    /* IEnergyContainerItem */
    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean doReceive)
    {

        if (container.stackTagCompound == null)
        {
            container.stackTagCompound = new NBTTagCompound();
        }
        int energy = container.stackTagCompound.getInteger("Energy");
        int energyReceived = MathHelper.minI(capacity - energy, MathHelper.minI(this.maxReceive, maxReceive));

        if (doReceive)
        {
            energy += energyReceived;
            container.stackTagCompound.setInteger("Energy", energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean doExtract)
    {

        if ((container.stackTagCompound == null) || !container.stackTagCompound.hasKey("Energy"))
        {
            return 0;
        }
        int energy = container.stackTagCompound.getInteger("Energy");
        int energyExtracted = MathHelper.minI(energy, MathHelper.minI(this.maxExtract, maxExtract));

        if (doExtract)
        {
            energy -= energyExtracted;
            container.stackTagCompound.setInteger("Energy", energy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ItemStack container)
    {

        if ((container.stackTagCompound == null) || !container.stackTagCompound.hasKey("Energy"))
        {
            return 0;
        }
        return container.stackTagCompound.getInteger("Energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack container)
    {

        return capacity;
    }
}