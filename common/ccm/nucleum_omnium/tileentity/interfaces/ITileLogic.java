package ccm.nucleum_omnium.tileentity.interfaces;

/**
 * This Interface is used to create custom logic for TileEntitys without having to create a Custom TileEntity
 * 
 * @author Captain_Shadows
 */
public interface ITileLogic {
    
    /**
     * This method gets called by updateEntity() inside of the TileEntity
     */
    public void runLogic();
    
    public boolean canRun();
    
    public void run();
}
