package lib.cofh.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot {

    public SlotLocked(final IInventory inventory, final int x, final int y, final int z) {

        super(inventory, x, y, z);
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
