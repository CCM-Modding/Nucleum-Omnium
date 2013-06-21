package ccm.nucleum_omnium.tileentity.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Extension of {@link ITileLogic} that should only be used if your TileEntity has a GUI
 * 
 * @author Captain_Shadows
 */
public interface IGUITileLogic extends ITileLogic {
    
    /**
     * This method gets called by the GUI when deciding the progress bar
     * 
     * @param scale
     *            The scale
     * @return the process, AKA % done
     */
    @SideOnly(Side.CLIENT)
    public int getProgressScaled(final int scale);
    
    public int getTimeLeft();
    
    public void setTimeLeft(final int time);
}
