/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum_omnium.tileentity.interfaces.IGUITileLogic;

public abstract class ProgressContainer extends BaseContainer
{

    protected final IGUITileLogic guiLogic;
    protected int                 lastCookTime;

    public ProgressContainer(final IInventory inventory, final IGUITileLogic guiLogic)
    {
        super(inventory);
        this.guiLogic = guiLogic;
    }

    public ProgressContainer(final IInventory inventory,
                             final IGUITileLogic guiLogic,
                             final InventoryPlayer player,
                             final int x,
                             final int y)
    {
        this(inventory, guiLogic);
        drawPlayerFullInv(player, x, y);
    }

    @Override
    public void addCraftingToCrafters(final ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, guiLogic.getTimeLeft());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        if (guiLogic.canRun())
        {
            for (int i = 0; i < crafters.size(); ++i)
            {
                final ICrafting icrafting = (ICrafting) crafters.get(i);
                if (lastCookTime != guiLogic.getTimeLeft())
                {
                    icrafting.sendProgressBarUpdate(this, 0, guiLogic.getTimeLeft());
                }
            }
            lastCookTime = guiLogic.getTimeLeft();
        }
        else
        {
            lastCookTime = 0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int progressIndex, final int progress)
    {
        if (progressIndex == 0)
        {
            guiLogic.setTimeLeft(progress);
        }
    }
}