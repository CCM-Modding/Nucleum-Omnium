/*
 * Copyright (c) 2014 CCM modding crew.
 * View members of the CCM modding crew on https://github.com/orgs/CCM-Modding/members
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ccm.nucleumOmnium.commands;

import ccm.nucleumOmnium.helpers.JavaHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.List;

/**
 * Copy from N-O 1
 */
public class CommandTpx extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "tpx";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/tpx <target player> <destination player> OR /tp <target player> [Dimension ID] [x] [y] [z]";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public boolean isUsernameIndex(final String[] args, final int userIndex)
    {
        return userIndex == 0 || userIndex == 1;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length == 1)
        {
            if (JavaHelper.isNumeric(args[args.length - 1]))
            {
                final World dimension = DimensionManager.getWorld(Integer.parseInt(args[args.length - 1]));
                if (dimension != null)
                {
                    final ChunkCoordinates spawn = dimension.getSpawnPoint();
                    teleportPlayer(sender, getCommandSenderAsPlayer(sender), dimension.provider.dimensionId, spawn.posX, spawn.posY, spawn.posZ);
                }
            }
            else
            {
                teleportPlayer(sender, getCommandSenderAsPlayer(sender), getPlayer(sender, args[args.length - 1]));
            }
        }
        else if (args.length == 2)
        {
            if (JavaHelper.isNumeric(args[args.length - 1]))
            {
                final World dimension = DimensionManager.getWorld(Integer.parseInt(args[args.length - 1]));
                if (dimension != null)
                {
                    final ChunkCoordinates spawn = dimension.getSpawnPoint();
                    teleportPlayer(sender, getPlayer(sender, args[args.length - 2]), dimension.provider.dimensionId, spawn.posX, spawn.posY, spawn.posZ);
                }
            }
            else
            {
                teleportPlayer(sender, getPlayer(sender, args[args.length - 2]), getPlayer(sender, args[args.length - 1]));
            }
        }
        else if (args.length == 4)
        {
            final EntityPlayerMP player = getCommandSenderAsPlayer(sender);
            if (player.worldObj != null)
            {
                int i = args.length - 4;
                final int dimension = CommandBase.parseInt(sender, args[i++]);
                final double x = checkPosition(sender, player.posX, args[i++]);
                final double y = checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                final double z = checkPosition(sender, player.posZ, args[i++]);
                teleportPlayer(sender, player, dimension, x, y, z);
            }
        }
        else if (args.length == 5)
        {
            final EntityPlayerMP player = getPlayer(sender, args[args.length - 5]);
            if (player.worldObj != null)
            {
                int i = args.length - 4;
                final int dimension = CommandBase.parseInt(sender, args[i++]);
                final double x = checkPosition(sender, player.posX, args[i++]);
                final double y = checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                final double z = checkPosition(sender, player.posZ, args[i++]);
                teleportPlayer(sender, player, dimension, x, y, z);
            }
        }
        else
        {
            throw new WrongUsageException("/tpx <target player> <destination player> OR /tp <target player> {Dimension ID} {x} {y} {z}");
        }
    }

    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args)
    {
        if ((args.length != 1) && (args.length != 2))
        {
            return null;
        }
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }

    public static double checkPosition(final ICommandSender sender, final double postion, final String argPos)
    {
        return checkPositionWithBounds(sender, postion, argPos, -30000000, 30000000);
    }

    public static double checkPositionWithBounds(final ICommandSender sender, final double postion, String argPos, final int min, final int max)
    {
        final boolean flag = argPos.startsWith("~");
        double d1 = flag ? postion : 0.0D;
        if (!flag || (argPos.length() > 1))
        {
            final boolean flag1 = argPos.contains(".");
            if (flag)
            {
                argPos = argPos.substring(1);
            }
            d1 += CommandBase.parseDouble(sender, argPos);
            if (!flag1 && !flag)
            {
                d1 += 0.5D;
            }
        }
        if ((min != 0) || (max != 0))
        {
            if (d1 < min)
            {
                if ((d1 < -30000000) && (d1 >= -300000000))
                {
                    throw new NumberInvalidException("commands.generic.double.tooSmall", d1, max);
                }
            }
            if (d1 > max)
            {
                if ((d1 > 30000000) && (d1 <= 300000000))
                {
                    throw new NumberInvalidException("commands.generic.double.tooBig", d1, max);
                }
            }
        }
        return d1;
    }

    /**
     * Teleports a player to another
     *
     * @param player  Player to send
     * @param player1 Player that gets send
     */
    public static void teleportPlayer(final ICommandSender sender, final EntityPlayerMP player, final EntityPlayerMP player1)
    {
        player.mountEntity(null);
        if (player.dimension != player1.dimension)
        {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, player1.dimension);
        }
        player.playerNetServerHandler.setPlayerLocation(player1.posX, player1.posY, player1.posZ, player1.rotationYaw, player1.rotationPitch);
        player.prevPosX = player.posX = player1.posX;
        player.prevPosY = player.posY = player1.posY;
        player.prevPosZ = player.posZ = player1.posZ;
        tellAdmins(sender, "Teleported %s to %s", player.getEntityName(), player1.getEntityName());
    }

    /**
     * Teleports a player to coordinates
     */
    public static void teleportPlayer(final ICommandSender sender, final EntityPlayerMP player, final int dimension, final double x, final double y, final double z)
    {
        player.mountEntity(null);
        if (player.dimension != dimension)
        {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, dimension);
        }
        player.setPositionAndUpdate(x, y, z);
        player.prevPosX = player.posX = x;
        player.prevPosY = player.posY = y;
        player.prevPosZ = player.posZ = z;
        tellAdmins(sender, "Teleported %s to %s, %.2f, %.2f, %.2f", player.getEntityName(), dimension, x, y, z);
    }

    public static void tellAdmins(final ICommandSender sender, final String message, final Object... objects)
    {
        CommandBase.notifyAdmins(sender, message, objects);
    }
}
