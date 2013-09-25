/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity;

import java.lang.reflect.Constructor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
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

            String fail = "LOGIC LOADER HAS FAILED TO GET THE CONSTRUCTOR OF:\n%s WITH ERROR:\n%s";

            try
            {
                c = srclogic.getConstructor(TileEntity.class);
            } catch (final Exception e)
            {
                CCMLogger.DEFAULT_LOGGER.printCatch(e, fail, toString(), e);
            }

            fail = "LOGIC LOADER HAS FAILED TO CREATE A NEW INSTANCE OF:\n%s WITH ERROR:\n%s";

            try
            {
                logic = c.newInstance(this);
            } catch (final Exception e)
            {
                CCMLogger.DEFAULT_LOGGER.printCatch(e, fail, toString(), e);
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
            CCMLogger.DEFAULT_LOGGER.debug("SOMETHING WENT WRONG WHILE SETTING THE TILE ENTITY'S LOGIC!!!\n IT HAS BEEN RELOADED");
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
            CCMLogger.DEFAULT_LOGGER.debug(tmp);
            try
            {
                setLogic((Class<? extends ITileLogic>) Class.forName(tmp));
            } catch (final ClassNotFoundException e)
            {
                final String fail = "LOGIC LOADER HAS FAILED TO FIND A CLASS NAMED:\n%sWITH ERROR:\n%s";
                CCMLogger.DEFAULT_LOGGER.printCatch(e, fail, tmp, e);
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
            CCMLogger.DEFAULT_LOGGER.debug("%s DOES NOT HAVE LOGIC, BUT SUBCLASSED LogicTE!!", this);
            CCMLogger.DEFAULT_LOGGER.debug(nbt.toString());
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
                CCMLogger.DEFAULT_LOGGER.debug(srclogic.getName());
            } else
            {
                CCMLogger.DEFAULT_LOGGER.severe("%s\nSOURCE LOGIC WAS NULL WHEN TRING TO SAVE TO NBT!!!", this);
            }
            if (logic != null)
            {
                logic.writeToNBT(nbt);
            }
        }
    }
}