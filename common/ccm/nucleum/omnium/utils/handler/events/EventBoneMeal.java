/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.events;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

import ccm.nucleum.omnium.tileentity.PlantTE;

public final class EventBoneMeal
{
    @ForgeSubscribe
    public void fertilize(final BonemealEvent event)
    {
        final TileEntity tmp = event.world.getBlockTileEntity(event.X, event.Y, event.Z);
        if (tmp instanceof PlantTE)
        {
            ((PlantTE) tmp).randomGrowth();
        }
    }
}