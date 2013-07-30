package ccm.nucleum_omnium.tileentity;

import java.lang.reflect.Constructor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.LogHandler;
import ccm.nucleum_omnium.tileentity.interfaces.ITileLogic;
import ccm.nucleum_omnium.utils.lib.TileConstants;

public class LogicTE extends InventoryTE {

    /**
     * Weather the Tile has logic or not
     */
    private boolean                     hasLogic = false;

    /**
     * The Source of the logic behind the TileEntity
     */
    private Class<? extends ITileLogic> srclogic = null;

    /**
     * The logic behind the TileEntity
     */
    private ITileLogic                  logic    = null;

    @Override
    public void updateEntity() {
        if (hasLogic()) {
            getTileLogic().runLogic();
        }
        super.updateEntity();
    }

    /**
     * Gets the {@link TileEntity}'s {@link ITileLogic}
     * 
     * @return The {@link TileEntity}'s {@link ITileLogic}
     */
    private void loadLogic() {
        if (srclogic != null) {
            Constructor<? extends ITileLogic> c = null;

            final String failConst = "Logic loader has failed to get the Constructor for: \n %s With error: \n %s";

            try {
                c = srclogic.getConstructor(TileEntity.class);
            } catch (final Exception e) {
                LogHandler.severe(NucleumOmnium.instance, failConst, toString(), e.toString());
                if (e.getCause() != null) {
                    LogHandler.severe(NucleumOmnium.instance, "\nAnd Cause: %s", e.getCause());
                }
                e.printStackTrace();
            }

            final String failInst = "Logic loader has failed to create a new Instance of: \n %s With error: \n %s";

            try {
                logic = c.newInstance(this);
            } catch (final Exception e) {
                LogHandler.severe(NucleumOmnium.instance, failInst, toString(), e.toString());
                if (e.getCause() != null) {
                    LogHandler.severe(NucleumOmnium.instance, "\nAnd Cause: %s", e.getCause());
                }
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("\nsrclogic WAS NULL!!!! \n");
        }
    }

    public ITileLogic getTileLogic() {
        if (logic == null) {
            loadLogic();
            LogHandler.severe(NucleumOmnium.instance,
                              "Somthing went wrong while seting the Tile Entity's Logic!!!\n It was reloaded");
        }
        return logic;
    }

    /**
     * Gets the {@link TileEntity}'s {@code Class<? extends ITileLogic>}
     * 
     * @return The {@link TileEntity}'s {@code Class<? extends ITileLogic>}
     */
    public Class<? extends ITileLogic> getSrcLogic() {
        return srclogic;
    }

    /**
     * @return true if the Tile has logic
     */
    public boolean hasLogic() {
        return hasLogic;
    }

    /**
     * Sets the {@link ITileLogic} of the {@link TileEntity}.
     * 
     * @param logic
     *            A class that implements {@link ITileLogic}
     * @return
     */
    public LogicTE setLogic(final Class<? extends ITileLogic> logic) {
        hasLogic = true;
        srclogic = logic;
        loadLogic();
        return this;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        if (hasLogic()) {
            if (srclogic != null) {
                nbt.setString(TileConstants.NBT_TE_SRC_LOGIC, srclogic.getName());
                LogHandler.debug(srclogic.getName());
            } else {
                LogHandler.severe(NucleumOmnium.instance,
                                  "%sSource Logic was null when tring to save to NBT!",
                                  this);
            }
            if (logic != null) {
                logic.writeToNBT(nbt);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readFromNBT(final NBTTagCompound nbt) {
        if (nbt.hasKey(TileConstants.NBT_TE_SRC_LOGIC)) {
            final String tmp = nbt.getString(TileConstants.NBT_TE_SRC_LOGIC);
            LogHandler.debug(tmp);
            try {
                setLogic((Class<? extends ITileLogic>) Class.forName(tmp));
            } catch (final ClassNotFoundException e) {
                final String failNBT = "Logic loader has failed to find a class named: \n %s With error: \n %s";
                LogHandler.severe(NucleumOmnium.instance, failNBT, tmp, e.toString());
                e.printStackTrace();
            }
        }
        if (hasLogic()) {
            if (logic != null) {
                logic.readFromNBT(nbt);
            } else {
                loadLogic();
                logic.readFromNBT(nbt);
            }
        } else {
            LogHandler.severe(NucleumOmnium.instance, "Dosn't have logic!");
            LogHandler.debug(nbt.toString());
        }
    }
}