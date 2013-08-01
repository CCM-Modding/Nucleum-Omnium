/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.command;

import java.text.DecimalFormat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.world.World;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.helper.CommandHelper;
import ccm.nucleum_omnium.helper.JavaHelper;
import ccm.nucleum_omnium.utils.lib.Commands;

public class CommandTPS extends CommandBase
{

    public static CommandTPS     instance = new CommandTPS();

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
        String tmp;
        final StringBuilder b = new StringBuilder();
        if (args.length < 1)
        {

            double tps = getTps(null);
            double tickms = getTickMs(null);

            CommandHelper.sendChatToPlayer(sender, "--------------------------------------------------");

            tmp = String.format("Overall: %s TPS/%sMS (%s%%)",
                                floatfmt.format(tps),
                                floatfmt.format(tickms),
                                ((int) ((tps / 20.0D) * 100.0D)));
            CommandHelper.sendChatToPlayer(sender, tmp);

            CommandHelper.sendChatToPlayer(sender);

            for (final World world : NucleumOmnium.server.worldServers)
            {

                tps = getTps(world);
                tickms = getTickMs(world);

                tmp = String.format("%s [%s]: TPS/%sMS (%s%%)",
                                    world.provider.getDimensionName(),
                                    world.provider.dimensionId,
                                    floatfmt.format(tps),
                                    floatfmt.format(tickms),
                                    ((int) ((tps / 20.0D) * 100.0D)));
                CommandHelper.sendChatToPlayer(sender, tmp);
            }

            CommandHelper.sendChatToPlayer(sender, "--------------------------------------------------");

        } else if (args[0].toLowerCase().charAt(0) == 'o')
        {

            final double tickms = getTickMs(null);
            final double tps = getTps(null);

            CommandHelper.sendChatToPlayer(sender, "--------------------------------------------------");
            CommandHelper.sendChatToPlayer(sender, "Overall server tick:");
            CommandHelper.sendChatToPlayer(sender);
            b.append(String.format("TPS: %s TPS of %s TPS (%s%%)\n",
                                   floatfmt.format(tps),
                                   floatfmt.format(20L),
                                   ((int) ((tps / 20.0D) * 100.0D))));

            b.append(String.format("Tick Time: %s ms of %s ms", floatfmt.format(tickms), floatfmt.format(50L)));

            CommandHelper.sendChatToPlayer(sender, "--------------------------------------------------");

            CommandHelper.sendChatToPlayer(sender, b.toString());

        } else if (args[0].toLowerCase().charAt(0) == 'a')
        {

            final double tickms = getTickMs(null);
            final double tps = getTps(null);

            b.append("--------------------------------------------------");
            CommandHelper.sendChatToPlayer(sender);
            b.append("Overall server tick:");
            CommandHelper.sendChatToPlayer(sender);
            b.append(String.format("TPS: %s TPS of %s TPS (%s%%)\n",
                                   floatfmt.format(tps),
                                   floatfmt.format(20L),
                                   ((int) ((tps / 20.0D) * 100.0D))));

            b.append(String.format("Tick Time: %s ms of %s ms", floatfmt.format(tickms), floatfmt.format(50L)));
            CommandHelper.sendChatToPlayer(sender);
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

            b.append(String.format("Total Loaded Worlds/Chunks: %s/%s", worlds, loadedChunks));
            CommandHelper.sendChatToPlayer(sender);
            b.append(String.format("Total Entities/TileEntities: %s/%s", entities, te));
            CommandHelper.sendChatToPlayer(sender);
            b.append("--------------------------------------------------");

            CommandHelper.sendChatToPlayer(sender, b.toString());
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

            b.append("--------------------------------------------------\n");

            b.append(String.format("%s [%s]\n", world.provider.getDimensionName(), world.provider.dimensionId));

            b.append(String.format("Loaded chunks: %s\n", world.getChunkProvider().getLoadedChunkCount()));

            b.append(String.format("TPS: %s/%s TPS (%s%%)\n",
                                   floatfmt.format(tps),
                                   floatfmt.format(20L),
                                   ((int) ((tps / 20.0D) * 100.0D))));

            b.append(String.format("Tick Time: %s ms of %s ms\n",
                                   floatfmt.format(tickms),
                                   floatfmt.format(50L)));

            b.append(String.format("Entities: %s\n", world.loadedEntityList.size()));

            b.append(String.format("Tile entities: %s\n", world.loadedTileEntityList.size()));

            b.append("--------------------------------------------------");

            CommandHelper.sendChatToPlayer(sender, b.toString());
        }
    }
}