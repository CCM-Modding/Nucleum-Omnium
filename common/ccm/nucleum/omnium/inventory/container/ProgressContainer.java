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
        for (int elementID = 0; elementID < tile.getTimedElements().length; ++elementID)
        {
            crafting.sendProgressBarUpdate(this, elementID, tile.getTimeLeft(elementID));
        }
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int elementID = 0; elementID < tile.getTimedElements().length; ++elementID)
        {
            if (tile.canRun())
            {
                for (int crafterID = 0; crafterID < crafters.size(); ++crafterID)
                {
                    final ICrafting crafter = (ICrafting) crafters.get(crafterID);
                    if (!tile.isUpdated(elementID))
                    {
                        crafter.sendProgressBarUpdate(this, elementID, tile.getTimeLeft(elementID));
                    }
                }
                tile.updateRecord(elementID);
            } else
            {
                tile.destroyRecord(elementID);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int elementID, final int progress)
    {
        tile.setTimeLeft(elementID, progress);
    }
}