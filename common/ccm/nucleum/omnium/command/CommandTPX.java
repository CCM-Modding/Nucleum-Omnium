/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.command;

import static ccm.nucleum.omnium.utils.helper.CommandHelper.getPlayer;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import ccm.nucleum.omnium.utils.helper.JavaHelper;
import ccm.nucleum.omnium.utils.helper.TeleportHelper;
import ccm.nucleum.omnium.utils.lib.Commands;

public class CommandTPX extends CommandBase
{

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args)
    {
        if ((args.length != 1) && (args.length != 2))
        {
            return null;
        } else
        {
            return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
    }

    @Override
    public String getCommandName()
    {
        return Commands.COMMAND_TPX;
    }

    @Override
    public String getCommandUsage(final ICommandSender sender)
    {
        return "commands.tpx.usage";
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    @Override
    public boolean isUsernameIndex(final String[] args, final int userIndex)
    {
        return userIndex == 0;
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if (args.length == 1)
        {
            if (JavaHelper.isNumeric(args[args.length - 1]))
            {
                final World dimension = DimensionManager.getWorld(Integer.parseInt(args[args.length - 1]));
                if (dimension != null)
                {
                    final ChunkCoordinates spawn = dimension.getSpawnPoint();
                    TeleportHelper.teleportPlayer(sender, getPlayer(sender), dimension.provider.dimensionId, spawn.posX, spawn.posY, spawn.posZ);
                } else
                {
                    CommandBase.notifyAdmins(sender, "error.invalid.dim", new Object[]
                    { sender.getCommandSenderName(), args[args.length - 1] });
                }
            } else
            {
                TeleportHelper.teleportPlayer(sender, getPlayer(sender), getPlayer(sender, args[args.length - 1]));
            }
        } else if (args.length == 2)
        {
            if (JavaHelper.isNumeric(args[args.length - 1]))
            {
                final World dimension = DimensionManager.getWorld(Integer.parseInt(args[args.length - 1]));
                if (dimension != null)
                {
                    final ChunkCoordinates spawn = dimension.getSpawnPoint();
                    TeleportHelper.teleportPlayer(sender, getPlayer(sender, args[args.length - 2]), dimension.provider.dimensionId, spawn.posX, spawn.posY, spawn.posZ);
                } else
                {
                    CommandBase.notifyAdmins(sender, "error.invalid.dim", new Object[]
                    { sender.getCommandSenderName(), args[args.length - 1] });
                }
            } else
            {
                TeleportHelper.teleportPlayer(sender, getPlayer(sender, args[args.length - 2]), getPlayer(sender, args[args.length - 1]));
            }
        } else if (args.length == 4)
        {
            final EntityPlayerMP player = getPlayer(sender);
            if (player.worldObj != null)
            {
                int i = args.length - 4;
                final int dimension = CommandBase.parseInt(sender, args[i++]);
                final double x = TeleportHelper.checkPosition(sender, player.posX, args[i++]);
                final double y = TeleportHelper.checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                final double z = TeleportHelper.checkPosition(sender, player.posZ, args[i++]);
                TeleportHelper.teleportPlayer(sender, player, dimension, x, y, z);
            }
        } else if (args.length == 5)
        {
            final EntityPlayerMP player = getPlayer(sender, args[args.length - 5]);
            if (player.worldObj != null)
            {
                int i = args.length - 4;
                final int dimension = CommandBase.parseInt(sender, args[i++]);
                final double x = TeleportHelper.checkPosition(sender, player.posX, args[i++]);
                final double y = TeleportHelper.checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                final double z = TeleportHelper.checkPosition(sender, player.posZ, args[i++]);
                TeleportHelper.teleportPlayer(sender, player, dimension, x, y, z);
            }
        } else
        {
            throw new WrongUsageException("commands.tpx.usage", new Object[0]);
        }
    }
}