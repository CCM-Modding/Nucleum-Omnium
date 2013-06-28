package lib.cofh.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotCraftingLocked extends SlotCrafting {

	public SlotCraftingLocked(	final EntityPlayer player,
								final IInventory craftMatrix,
								final IInventory inventory,
								final int x,
								final int y,
								final int z) {

		super(player, craftMatrix, inventory, x, y, z);
	}

	@Override
	public boolean isItemValid(final ItemStack stack) {

		return false;
	}

	@Override
	public boolean canTakeStack(final EntityPlayer player) {

		return false;
	}

}
