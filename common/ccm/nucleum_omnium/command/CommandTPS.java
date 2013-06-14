package ccm.nucleum_omnium.command;

import java.text.DecimalFormat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.world.World;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.helper.JavaHelper;
import ccm.nucleum_omnium.utils.lib.Commands;

public class CommandTPS extends CommandBase {
    
    public static CommandTPS     instance = new CommandTPS();
    
    private static DecimalFormat floatfmt = new DecimalFormat("##0.00");
    
    private double getTickTimeSum(final long[] times) {
        long timesum = 0L;
        if (times == null) {
            return 0.0D;
        }
        for (final long time : times) {
            timesum += time;
        }
        
        return timesum / times.length;
    }
    
    private double getTickMs(final World world) {
        return getTickTimeSum(world == null ? NucleumOmnium.server.tickTimeArray
                : (long[]) NucleumOmnium.server.worldTickTimes.get(Integer
                        .valueOf(world.provider.dimensionId))) * 1.0E-006D;
    }
    
    private double getTps(final World world) {
        final double tps = 1000.0D / getTickMs(world);
        return tps > 20.0D ? 20.0D : tps;
    }
    
    @Override
    public String getCommandName() {
        return Commands.COMMAND_TPS;
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length < 1) {
            double tps = getTps(null);
            double tickms = getTickMs(null);
            
            sender.sendChatToPlayer("--------------------------------------------------");
            
            sender.sendChatToPlayer("Overall: " + CommandTPS.floatfmt.format(tps) + " TPS/"
                    + CommandTPS.floatfmt.format(tickms) + "MS (" + (int) ((tps / 20.0D) * 100.0D)
                    + "%)");
            
            sender.sendChatToPlayer("");
            
            for (final World world : NucleumOmnium.server.worldServers) {
                tps = getTps(world);
                tickms = getTickMs(world);
                sender.sendChatToPlayer(world.provider.getDimensionName() + " ["
                        + world.provider.dimensionId + "]: " + CommandTPS.floatfmt.format(tps)
                        + " TPS/" + CommandTPS.floatfmt.format(tickms) + "MS ("
                        + (int) ((tps / 20.0D) * 100.0D) + "%)");
            }
            
            sender.sendChatToPlayer("--------------------------------------------------");
        } else if (args[0].toLowerCase().charAt(0) == 'o') {
            final double tickms = getTickMs(null);
            final double tps = getTps(null);
            
            sender.sendChatToPlayer("--------------------------------------------------");
            
            sender.sendChatToPlayer("Overall server tick:");
            sender.sendChatToPlayer("TPS: " + CommandTPS.floatfmt.format(tps) + " TPS of "
                    + CommandTPS.floatfmt.format(20L) + " TPS (" + (int) ((tps / 20.0D) * 100.0D)
                    + "%)");
            sender.sendChatToPlayer("Tick time: " + CommandTPS.floatfmt.format(tickms) + " ms of "
                    + CommandTPS.floatfmt.format(50L) + " ms");
            
            sender.sendChatToPlayer("--------------------------------------------------");
        } else if (args[0].toLowerCase().charAt(0) == 'a') {
            final double tickms = getTickMs(null);
            final double tps = getTps(null);
            sender.sendChatToPlayer("--------------------------------------------------");
            sender.sendChatToPlayer("Overall server tick:");
            sender.sendChatToPlayer("TPS: " + CommandTPS.floatfmt.format(tps) + " TPS of "
                    + CommandTPS.floatfmt.format(20L) + " TPS (" + (int) ((tps / 20.0D) * 100.0D)
                    + "%)");
            sender.sendChatToPlayer("Tick time: " + CommandTPS.floatfmt.format(tickms) + " ms of "
                    + CommandTPS.floatfmt.format(50L) + " ms");
            sender.sendChatToPlayer("");
            int loadedChunks = 0;
            int entities = 0;
            int te = 0;
            int worlds = 0;
            for (final World world : NucleumOmnium.server.worldServers) {
                loadedChunks += world.getChunkProvider().getLoadedChunkCount();
                entities += world.loadedEntityList.size();
                te += world.loadedTileEntityList.size();
                worlds++;
            }
            sender.sendChatToPlayer("Total Loaded Worlds/Chunks: " + worlds + "/" + loadedChunks);
            sender.sendChatToPlayer("");
            sender.sendChatToPlayer("Total Entities/TileEntities: " + entities + "/" + te);
            sender.sendChatToPlayer("--------------------------------------------------");
        } else if (JavaHelper.isNumeric(args[0])) {
            
            int dim = Integer.parseInt(args[0]);
            
            final World world = NucleumOmnium.server.worldServerForDimension(dim);
            if (world == null) {
                throw new PlayerNotFoundException("World not found", new Object[0]);
            }
            
            final double tickms = getTickMs(world);
            final double tps = getTps(world);
            sender.sendChatToPlayer("--------------------------------------------------");
            sender.sendChatToPlayer(world.provider.getDimensionName() + " ["
                    + world.provider.dimensionId + "]");
            sender.sendChatToPlayer("Loaded chunks: "
                    + world.getChunkProvider().getLoadedChunkCount());
            sender.sendChatToPlayer("");
            sender.sendChatToPlayer("TPS: " + CommandTPS.floatfmt.format(tps) + "/"
                    + CommandTPS.floatfmt.format(20L) + " TPS (" + (int) ((tps / 20.0D) * 100.0D)
                    + "%)");
            sender.sendChatToPlayer("Tick: " + CommandTPS.floatfmt.format(tickms) + " ms of "
                    + CommandTPS.floatfmt.format(50L) + " ms");
            sender.sendChatToPlayer("");
            sender.sendChatToPlayer("Entities: " + world.loadedEntityList.size());
            sender.sendChatToPlayer("Tile entities: " + world.loadedTileEntityList.size());
            sender.sendChatToPlayer("--------------------------------------------------");
        }
    }
    
    /**
     * Makes sure anyone can use it
     */
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
}