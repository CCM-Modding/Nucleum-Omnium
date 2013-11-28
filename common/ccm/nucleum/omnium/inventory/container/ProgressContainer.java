/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import ccm.nucleum.omnium.tileentity.ProgressTE;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ProgressContainer extends BaseContainer
{
    protected final ProgressTE tile;

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
            crafting.sendProgressBarUpdate(this, t, tile.getTimeLeft(t));
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
                    if (!tile.isUpdated(t))
                    {
                        icrafting.sendProgressBarUpdate(this, t, tile.getTimeLeft(t));
                    }
                }
                tile.updateRecord(t);
            } else
            {
                tile.destroyRecord(t);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int progressIndex, final int progress)
    {
        tile.setTimeLeft(progressIndex, progress);
    }
}