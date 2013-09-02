/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import ccm.nucleum.omnium.NucleumOmnium;
import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.utils.exeptions.WTFExeption;

public final class TeleportHelper extends BaseNIC
{

    public static double checkPosition(final ICommandSender sender, final double postion, final String argPos)
    {
        return TeleportHelper.checkPositionWithBounds(sender, postion, argPos, -30000000, 30000000);
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
                    throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]
                    { Double.valueOf(d1), Integer.valueOf(max) });
                } else if ((d1 < -300000000) && (d1 >= Integer.MIN_VALUE))
                {
                    throw new WTFExeption(NucleumOmnium.instance);
                }
            }
            if (d1 > max)
            {
                if ((d1 > 30000000) && (d1 <= 300000000))
                {
                    throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]
                    { Double.valueOf(d1), Integer.valueOf(max) });
                } else if ((d1 > 300000000) && (d1 <= Integer.MAX_VALUE))
                {
                    throw new WTFExeption(NucleumOmnium.instance);
                }
            }
        }

        return d1;
    }

    /**
     * Teleports a player to another
     * 
     * @param player
     *            Player to send
     * @param player1
     *            Player that gets send
     */
    public static void teleportPlayer(final ICommandSender sender, final EntityPlayerMP player, final EntityPlayerMP player1)
    {
        player.mountEntity((Entity) null);
        if (player.dimension != player1.dimension)
        {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, player1.dimension);
        }
        player.playerNetServerHandler.setPlayerLocation(player1.posX, player1.posY, player1.posZ, player1.rotationYaw, player1.rotationPitch);
        player.prevPosX = player.posX = player1.posX;
        player.prevPosY = player.posY = player1.posY;
        player.prevPosZ = player.posZ = player1.posZ;
        tellAdmins(sender, "commands.tpx.success", new Object[]
        { player.getEntityName(), player1.getEntityName() });
    }

    /**
     * Teleports a player to coordinates
     */
    public static void teleportPlayer(final ICommandSender sender, final EntityPlayerMP player, final int dimension, final double x, final double y, final double z)
    {
        player.mountEntity((Entity) null);
        if (player.dimension != dimension)
        {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, dimension);
        }
        player.setPositionAndUpdate(x, y, z);
        player.prevPosX = player.posX = x;
        player.prevPosY = player.posY = y;
        player.prevPosZ = player.posZ = z;
        tellAdmins(sender, "commands.tpx.success.coordinates", new Object[]
        { player.getEntityName(), Integer.valueOf(dimension), Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
    }

    public static void tellAdmins(final ICommandSender sender, final String message, final Object... objects)
    {
        CommandBase.notifyAdmins(sender, message, objects);
    }
}