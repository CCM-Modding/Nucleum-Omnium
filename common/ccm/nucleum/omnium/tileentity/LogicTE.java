/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity;

import java.lang.reflect.Constructor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum.omnium.utils.handler.LogHandler;
import ccm.nucleum.omnium.utils.lib.NBTConstants;

/**
 * LogicTE
 * <p>
 * Default Implementation for a Tile Entity with an Inventory, and Logic
 * 
 * @author Captain_Shadows
 */
public class LogicTE extends InventoryTE
{

    /**
     * Weather the {@link TileEntity} has logic or not
     */
    private boolean hasLogic = false;

    /**
     * The Source of the logic behind the {@link TileEntity}
     */
    private Class<? extends ITileLogic> srclogic = null;

    /**
     * The logic behind the {@link TileEntity}
     */
    private ITileLogic logic = null;

    /**
     * Gets the {@link TileEntity}'s {@link ITileLogic}
     * 
     * @return The {@link TileEntity}'s {@link ITileLogic}
     */
    private void loadLogic()
    {
        if (srclogic != null)
        {
            Constructor<? extends ITileLogic> c = null;

            String fail = "Logic loader has failed to get the Constructor for: \n %s With error: \n %s";

            try
            {
                c = srclogic.getConstructor(TileEntity.class);
            } catch (final Exception e)
            {
                LogHandler.printCatch(e, fail, toString(), e);
            }

            fail = "Logic loader has failed to create a new Instance of: \n %s With error: \n %s";

            try
            {
                logic = c.newInstance(this);
            } catch (final Exception e)
            {
                LogHandler.printCatch(e, fail, toString(), e);
            }
        } else
        {
            throw new RuntimeException("\n srclogic WAS NULL!!!! \n");
        }
    }

    /**
     * Gets the {@link TileEntity}'s {@code Class<? extends ITileLogic>}
     * 
     * @return The {@link TileEntity}'s {@code Class<? extends ITileLogic>}
     */
    public Class<? extends ITileLogic> getSrcLogic()
    {
        return srclogic;
    }

    /**
     * @return The {@link TileEntity}'s Logic
     */
    public ITileLogic getTileLogic()
    {
        if (!hasLogic())
        {
            loadLogic();
            LogHandler.debug("Somthing went wrong while seting the Tile Entity's Logic!!!\n It was reloaded");
        }
        return logic;
    }

    /**
     * @return true if the {@link TileEntity} has logic
     */
    public boolean hasLogic()
    {
        return hasLogic;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt)
    {
        if (nbt.hasKey(NBTConstants.NBT_TE_SRC_LOGIC))
        {
            final String tmp = nbt.getString(NBTConstants.NBT_TE_SRC_LOGIC);
            LogHandler.debug(tmp);
            try
            {
                setLogic((Class<? extends ITileLogic>) Class.forName(tmp));
            } catch (final ClassNotFoundException e)
            {
                final String fail = "Logic loader has failed to find a class named: \n %s With error: \n %s";
                LogHandler.printCatch(e, fail, tmp, e);
            }
        }
        if (hasLogic())
        {
            if (logic != null)
            {
                logic.readFromNBT(nbt);
            } else
            {
                loadLogic();
                logic.readFromNBT(nbt);
            }
        } else
        {
            LogHandler.debug("%s DOES NOT HAVE LOGIC, BUT SUBCLASSED LogicTE!!", this);
            LogHandler.debug(nbt.toString());
        }
    }

    /**
     * Sets the {@link ITileLogic} of the {@link TileEntity}.
     * 
     * @param logic
     *            A class that implements {@link ITileLogic}
     * @return
     */
    public LogicTE setLogic(final Class<? extends ITileLogic> logic)
    {
        hasLogic = true;
        srclogic = logic;
        loadLogic();
        return this;
    }

    @Override
    public void updateEntity()
    {
        if (hasLogic())
        {
            getTileLogic().runLogic();
        }
        super.updateEntity();
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt)
    {
        if (hasLogic())
        {
            if (srclogic != null)
            {
                nbt.setString(NBTConstants.NBT_TE_SRC_LOGIC, srclogic.getName());
                LogHandler.debug(srclogic.getName());
            } else
            {
                LogHandler.severe("%s Source Logic was null when tring to save to NBT!", this);
            }
            if (logic != null)
            {
                logic.writeToNBT(nbt);
            }
        }
    }
}