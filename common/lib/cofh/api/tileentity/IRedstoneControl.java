package lib.cofh.api.tileentity;

/**
 * Implement this interface on Tile Entities which have redstone control functionality.
 * 
 * @author King Lemming
 * 
 */
public interface IRedstoneControl {

	public boolean getControlDisable();

	public boolean getControlSetting();

	public boolean setControlDisable(boolean disable);

	public boolean setControlSetting(boolean state);

	public boolean setRedstoneConfig(boolean disable, boolean state);
}