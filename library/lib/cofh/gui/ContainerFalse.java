package lib.cofh.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public final class ContainerFalse extends Container {

	@Override
	public boolean canInteractWith(final EntityPlayer player) {

		return false;
	}

}
