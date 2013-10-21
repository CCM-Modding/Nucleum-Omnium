package ccm.nucleum.omnium.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import ccm.nucleum.omnium.utils.helper.MathHelper;
import ccm.nucleum.omnium.utils.helper.NBTHelper;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

public class PlantTE extends TileEntity implements IPlantable
{
    // /////////////////////////////
    // DATA
    // /////////////////////////////
    private int stage;

    private int stages;

    private float growthRate;

    // /////////////////////////////
    // IPlantable
    // /////////////////////////////
    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getPlantID(World world, int x, int y, int z)
    {
        return blockType.blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z)
    {
        return getBlockMetadata();
    }

    // /////////////////////////////
    // Methods
    // /////////////////////////////
    public int getCurrentStage()
    {
        return stage;
    }

    public float getGrowthRate()
    {
        return growthRate;
    }

    public PlantTE setGrowthRate(float rate)
    {
        growthRate = rate;
        return this;
    }

    public int getTotalStages()
    {
        return stages;
    }

    public PlantTE setTotalStages(int total)
    {
        stages = total;
        return this;
    }

    public void grow()
    {
        if (stage != stages)
        {
            stage += 1;
        }
    }

    public void growToStage(final int stage)
    {
        if (this.stage != stages)
        {
            if ((this.stage + stage) <= stages)
            {
                this.stage = stage;
            }
        }
    }

    public void randomGrowth()
    {
        growToStage(MathHelper.getRandInt(getCurrentStage(), getTotalStages()));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        stage = NBTHelper.getInteger(nbt, NBTConstants.NBT_PLANT_STAGE);
        stages = NBTHelper.getInteger(nbt, NBTConstants.NBT_PLANT_STAGES);
        growthRate = NBTHelper.getFloat(nbt, NBTConstants.NBT_PLANT_RATE);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger(NBTConstants.NBT_PLANT_STAGE, stage);
        nbt.setInteger(NBTConstants.NBT_PLANT_STAGES, stages);
        nbt.setFloat(NBTConstants.NBT_PLANT_RATE, growthRate);
    }
}