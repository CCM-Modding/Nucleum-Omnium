package ccm.nucleum_omnium.tileentity.interfaces;

/**
 * This Interface is used to create custom logic for TileEntitys without having to create a Custom
 * TileEntity
 * 
 * @author Captain_Shadows
 */
public interface ITileLogic {

	/**
	 * This method gets called by updateEntity() inside of the TileEntity
	 */
	public void runLogic();

	/**
	 * Checks if the the TE Logic is allowed to process the Item
	 * 
	 * @return true if it can
	 */
	public boolean canRun();

	/**
	 * Processes the Item
	 */
	public void run();
}
