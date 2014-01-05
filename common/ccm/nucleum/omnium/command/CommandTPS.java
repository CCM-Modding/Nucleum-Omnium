/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.command;

import static ccm.nucleum.omnium.utils.helper.CommandHelper.sendChat;

import java.text.DecimalFormat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.world.World;
import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.utils.helper.JavaHelper;
import ccm.nucleum.omnium.utils.lib.Commands;

public class CommandTPS extends CommandBase
{
    public static CommandTPS instance = new CommandTPS();
    private static DecimalFormat floatfmt = new DecimalFormat("##0.00");

    private double getTickMs(final World world)
    {
        return getTickTimeSum(world == null ? NucleumOmnium.server.tickTimeArray
                                           : (long[]) NucleumOmnium.server.worldTickTimes.get(Integer.valueOf(world.provider.dimensionId))) * 1.0E-006D;
    }

    private double getTickTimeSum(final long[] times)
    {
        long timesum = 0L;
        if (times == null)
        {
            return 0.0D;
        }
        for (final long time : times)
        {
            timesum += time;
        }
        return timesum / times.length;
    }

    private double getTps(final World world)
    {
        final double tps = 1000.0D / getTickMs(world);
        return tps > 20.0D ? 20.0D : tps;
    }

    /**
     * Makes sure anyone can use it
     */
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender)
    {
        return true;
    }

    @Override
    public String getCommandName()
    {
        return Commands.COMMAND_TPS;
    }

    @Override
    public String getCommandUsage(final ICommandSender icommandsender)
    {
        return null;
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if (args.length < 1)
        {
            double tps = getTps(null);
            double tickms = getTickMs(null);
            sendChat(sender, "--------------------------------------------------");
            sendChat(sender, "Overall: %s TPS/%sMS (%s%%)", floatfmt.format(tps), floatfmt.format(tickms), ((int) ((tps / 20.0D) * 100.0D)));
            sendChat(sender);
            for (final World world : NucleumOmnium.server.worldServers)
            {
                tps = getTps(world);
                tickms = getTickMs(world);
                sendChat(sender,
                         "%s [%s]: TPS/%sMS (%s%%)",
                         world.provider.getDimensionName(),
                         world.provider.dimensionId,
                         floatfmt.format(tps),
                         floatfmt.format(tickms),
                         ((int) ((tps / 20.0D) * 100.0D)));
            }
            sendChat(sender, "--------------------------------------------------");
        } else if (args[0].toLowerCase().charAt(0) == 'o')
        {
            final double tickms = getTickMs(null);
            final double tps = getTps(null);
            sendChat(sender, "--------------------------------------------------");
            sendChat(sender, "Overall server tick:");
            sendChat(sender);
            sendChat(sender, "TPS: %s TPS of %s TPS (%s%%)\n", floatfmt.format(tps), floatfmt.format(20L), ((int) ((tps / 20.0D) * 100.0D)));
            sendChat(sender);
            sendChat(sender, "Tick Time: %s ms of %s ms", floatfmt.format(tickms), floatfmt.format(50L));
            sendChat(sender, "--------------------------------------------------");
        } else if (args[0].toLowerCase().charAt(0) == 'a')
        {
            final double tickms = getTickMs(null);
            final double tps = getTps(null);
            sendChat(sender, "--------------------------------------------------");
            sendChat(sender, "Overall server tick:");
            sendChat(sender);
            sendChat(sender, "TPS: %s TPS of %s TPS (%s%%)\n", floatfmt.format(tps), floatfmt.format(20L), ((int) ((tps / 20.0D) * 100.0D)));
            sendChat(sender);
            sendChat(sender, "Tick Time: %s ms of %s ms", floatfmt.format(tickms), floatfmt.format(50L));
            sendChat(sender);
            int loadedChunks = 0;
            int entities = 0;
            int te = 0;
            int worlds = 0;
            for (final World world : NucleumOmnium.server.worldServers)
            {
                loadedChunks += world.getChunkProvider().getLoadedChunkCount();
                entities += world.loadedEntityList.size();
                te += world.loadedTileEntityList.size();
                worlds++;
            }
            sendChat(sender, String.format("Total Loaded Worlds/Chunks: %s/%s", worlds, loadedChunks));
            sendChat(sender);
            sendChat(sender, String.format("Total Entities/TileEntities: %s/%s", entities, te));
            sendChat(sender, "--------------------------------------------------");
        } else if (JavaHelper.isNumeric(args[0]))
        {
            final int dim = Integer.parseInt(args[0]);
            final World world = NucleumOmnium.server.worldServerForDimension(dim);
            if (world == null)
            {
                throw new PlayerNotFoundException("World not found", new Object[0]);
            }
            final double tickms = getTickMs(world);
            final double tps = getTps(world);
            sendChat(sender, "--------------------------------------------------\n");
            sendChat(sender, "%s [%s]\n", world.provider.getDimensionName(), world.provider.dimensionId);
            sendChat(sender, "Loaded chunks: %s\n", world.getChunkProvider().getLoadedChunkCount());
            sendChat(sender, "TPS: %s/%s TPS (%s%%)\n", floatfmt.format(tps), floatfmt.format(20L), ((int) ((tps / 20.0D) * 100.0D)));
            sendChat(sender, "Tick Time: %s ms of %s ms\n", floatfmt.format(tickms), floatfmt.format(50L));
            sendChat(sender, "Entities: %s\n", world.loadedEntityList.size());
            sendChat(sender, "Tile entities: %s\n", world.loadedTileEntityList.size());
            sendChat(sender, "--------------------------------------------------");
        }
    }

    @Override
    public int compareTo(Object o)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}