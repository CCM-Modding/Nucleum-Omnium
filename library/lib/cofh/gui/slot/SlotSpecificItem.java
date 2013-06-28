package lib.cofh.gui.slot;

import lib.cofh.util.inventory.ComparableItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSpecificItem extends Slot {

	final ComparableItemStack	stack;
	int							slotStackLimit	= -1;

	public SlotSpecificItem(final IInventory inventory,
							final int x,
							final int y,
							final int z,
							final ItemStack stack) {

		super(inventory, x, y, z);

		this.stack = new ComparableItemStack(stack);
	}

	@Override
	public boolean isItemValid(final ItemStack stack) {

		return this.stack.isItemEqual(new ComparableItemStack(stack));
	}

	public SlotSpecificItem setSlotStackLimit(final int slotStackLimit) {

		this.slotStackLimit = slotStackLimit;
		return this;
	}

	@Override
	public int getSlotStackLimit() {

		if (slotStackLimit <= 0) {
			return inventory.getInventoryStackLimit();
		}
		return slotStackLimit;
	}

}
