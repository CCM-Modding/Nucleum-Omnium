/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.tileentity.interfaces;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Extension of {@link ITileLogic} that should only be used if your TileEntity has a GUI
 * 
 * @author Captain_Shadows
 */
public interface IGUITileLogic extends ITileLogic
{

    /**
     * @param item
     *            The item in the input slot
     * @return The maximum amount of time that the item can be "cooked" for
     */
    public int getMaxTime(final ItemStack item);

    /**
     * This method gets called by the GUI when deciding the progress bar
     * 
     * @param scale
     *            The scale
     * @return the process, AKA % done
     */
    @SideOnly(Side.CLIENT)
    public int getProgressScaled(final int scale);

    /**
     * @return The amount of time left for the current process
     */
    public int getTimeLeft();

    /**
     * Sets the amount of time left in the current process
     * 
     * @param time
     */
    public void setTimeLeft(final int time);
}
