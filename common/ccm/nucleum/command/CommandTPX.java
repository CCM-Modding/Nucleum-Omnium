package ccm.nucleum.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import ccm.nucleum.helper.FunctionHelper;
import ccm.nucleum.helper.JavaHelper;
import ccm.nucleum.helper.TeleportHelper;
import ccm.nucleum.utils.lib.Commands;

public class CommandTPX extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return Commands.COMMAND_TPX;
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandUsage(final ICommandSender sender)
    {
        return sender.translateString("commands.tpx.usage", new Object[0]);
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if (args.length == 1){
            if (JavaHelper.isNumeric(args[args.length - 1])){
                final World dimension = FunctionHelper.getDimensions().get(Integer.parseInt(args[args.length - 1]));
                final ChunkCoordinates spawn = dimension.getSpawnPoint();
                TeleportHelper.teleportPlayer(sender, TeleportHelper.getPlayer(sender), dimension.provider.dimensionId, spawn.posX, spawn.posY, spawn.posZ);
            }else{
                TeleportHelper.teleportPlayer(sender, TeleportHelper.getPlayer(sender), TeleportHelper.getPlayer(sender, args[args.length - 1]));
            }
        }else if (args.length == 2){
            if (JavaHelper.isNumeric(args[args.length - 1])){
                final World dimension = FunctionHelper.getDimensions().get(Integer.parseInt(args[args.length - 1]));
                final ChunkCoordinates spawn = dimension.getSpawnPoint();
                TeleportHelper.teleportPlayer(sender, TeleportHelper.getPlayer(sender, args[args.length - 2]), dimension.provider.dimensionId, spawn.posX, spawn.posY, spawn.posZ);
            }else{
                TeleportHelper.teleportPlayer(sender, TeleportHelper.getPlayer(sender, args[args.length - 2]), TeleportHelper.getPlayer(sender, args[args.length - 1]));
            }
        }else if (args.length == 4){
            final EntityPlayerMP player = TeleportHelper.getPlayer(sender, args[args.length - 4]);
            if (player.worldObj != null){
                int i = args.length - 4;
                final int d1 = parseInt(sender, args[i++]);
                final double d2 = TeleportHelper.checkPosition(sender, player.posX, args[i++]);
                final double d3 = TeleportHelper.checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                final double d4 = TeleportHelper.checkPosition(sender, player.posZ, args[i++]);
                TeleportHelper.teleportPlayer(sender, player, d1, d2, d3, d4);
            }
        }else if (args.length == 5){
            final EntityPlayerMP player = TeleportHelper.getPlayer(sender, args[args.length - 4]);
            if (player.worldObj != null){
                int i = args.length - 4;
                final int d1 = parseInt(sender, args[i++]);
                final double d2 = TeleportHelper.checkPosition(sender, player.posX, args[i++]);
                final double d3 = TeleportHelper.checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                final double d4 = TeleportHelper.checkPosition(sender, player.posZ, args[i++]);
                TeleportHelper.teleportPlayer(sender, player, d1, d2, d3, d4);
            }
        }else{
            throw new WrongUsageException("commands.tpx.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args)
    {
        if ((args.length != 1) && (args.length != 2)){
            return null;
        }else{
            return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    @Override
    public boolean isUsernameIndex(final String[] args, final int userIndex)
    {
        return userIndex == 0;
    }
}