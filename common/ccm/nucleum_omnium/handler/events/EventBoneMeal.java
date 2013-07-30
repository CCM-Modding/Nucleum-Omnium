package ccm.nucleum_omnium.handler.events;

import net.minecraft.block.Block;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

import ccm.nucleum_omnium.block.MainBlock;
import ccm.nucleum_omnium.block.sub.SubBlock;
import ccm.nucleum_omnium.block.sub.SubCrop;
import ccm.nucleum_omnium.helper.MathHelper;

public final class EventBoneMeal {

    @ForgeSubscribe
    public void fertilize(final BonemealEvent event) {
        final SubBlock tmp = ((MainBlock) Block.blocksList[event.world.getBlockId(event.X, event.Y, event.Z)]).getSubBlocks()[event.world.getBlockMetadata(event.X,
                                                                                                                                                           event.Y,
                                                                                                                                                           event.Z)];
        if (tmp instanceof SubCrop) {
            final SubCrop block = (SubCrop) tmp;

            int growth = block.getCurrentStage() + MathHelper.clampInt(2,
                                                                       block.getTotalStages(),
                                                                       event.world.rand.nextInt(block.getTotalStages()));

            if (growth > block.getTotalStages()) {
                growth = block.getTotalStages();
            }

            block.growToStage(growth);
        }

    }
}