/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum.omnium.inventory.container.element.TimedElement;
import ccm.nucleum.omnium.tileentity.ProgressTE;

public abstract class ProgressContainer extends BaseContainer
{
    protected final ProgressTE tile;
    protected int lastCookTime;

    public ProgressContainer(final ProgressTE tile)
    {
        super(tile);
        this.tile = tile;
    }

    public ProgressContainer(final ProgressTE tile, final InventoryPlayer player, final int x, final int y)
    {
        this(tile);
        drawPlayerFullInv(player, x, y);
    }

    @Override
    public void addCraftingToCrafters(final ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        for (int t = 0; t < tile.getTimedElements().length; ++t)
        {
            crafting.sendProgressBarUpdate(this, t, tile.getTimedElements()[t].getTimeLeft());
        }
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int t = 0; t < tile.getTimedElements().length; ++t)
        {
            if (tile.canRun())
            {
                for (int i = 0; i < crafters.size(); ++i)
                {
                    final ICrafting icrafting = (ICrafting) crafters.get(i);
                    if (!tile.getTimedElements()[t].isUpdated())
                    {
                        icrafting.sendProgressBarUpdate(this, t, tile.getTimedElements()[t].getTimeLeft());
                    }
                }
                tile.getTimedElements()[t].updateRecord();
            } else
            {
                tile.getTimedElements()[t].destroyRecord();
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int progressIndex, final int progress)
    {
        tile.getTimedElements()[progressIndex].setTimeLeft(progress);
    }
}