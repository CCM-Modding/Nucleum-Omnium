package ccm.nucleum_omnium.helper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public final class TeleportHelper extends BaseHelper
{

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
        if (player.dimension != player1.dimension){
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, player1.dimension);
        }
        player.playerNetServerHandler.setPlayerLocation(player1.posX, player1.posY, player1.posZ, player1.rotationYaw, player1.rotationPitch);
        player.prevPosX = player.posX = player1.posX;
        player.prevPosY = player.posY = player1.posY;
        player.prevPosZ = player.posZ = player1.posZ;
        CommandBase.notifyAdmins(sender, "commands.tpx.success", new Object[]
        { player.getEntityName(), player1.getEntityName() });
    }

    /**
     * Teleports a player to coordinates
     * 
     * @param player
     *            Player to send
     * @param d1
     *            The dimension to send the player to
     * @param d2
     *            The X coordinate to send the player to
     * @param d3
     *            The Y coordinate to send the player to
     * @param d4
     *            The Z coordinate to send the player to
     */
    public static void teleportPlayer(final ICommandSender sender, final EntityPlayerMP player, final int d1, final double d2, final double d3, final double d4)
    {
        player.mountEntity((Entity) null);
        if (player.dimension != d1){
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, d1);
        }
        player.setPositionAndUpdate(d2, d3, d4);
        player.prevPosX = player.posX = d2;
        player.prevPosY = player.posY = d3;
        player.prevPosZ = player.posZ = d4;
        CommandBase.notifyAdmins(sender, "commands.tpx.success.coordinates", new Object[]
        { player.getEntityName(), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3), Double.valueOf(d4) });
    }

    /**
     * Gets a player
     * 
     * @param sender
     * @return The EntityPlayerMP corresponding to who send it
     */
    public static EntityPlayerMP getPlayer(final ICommandSender sender)
    {
        return CommandBase.getCommandSenderAsPlayer(sender);
    }

    /**
     * Gets a player name form a specified string
     * 
     * @param name
     *            The name of the User
     * @param sender
     * @return The EntityPlayerMP corresponding to the name
     */
    public static EntityPlayerMP getPlayer(final ICommandSender sender, final String name)
    {
        final EntityPlayerMP player = CommandBase.func_82359_c(sender, name);

        if (player == null){
            throw new PlayerNotFoundException();
        }
        return player;
    }

    public static double checkPosition(final ICommandSender sender, final double postion, final String argPos)
    {
        return checkPositionWithBounds(sender, postion, argPos, -30000000, 30000000);
    }

    public static double checkPositionWithBounds(final ICommandSender sender, final double postion, String argPos, final int min, final int max)
    {
        final boolean flag = argPos.startsWith("~");
        double d1 = flag ? postion : 0.0D;

        if (!flag || (argPos.length() > 1)){
            final boolean flag1 = argPos.contains(".");

            if (flag){
                argPos = argPos.substring(1);
            }

            d1 += CommandBase.parseDouble(sender, argPos);

            if (!flag1 && !flag){
                d1 += 0.5D;
            }
        }

        if ((min != 0) || (max != 0)){
            if (d1 < min){
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]
                { Double.valueOf(d1), Integer.valueOf(min) });
            }

            if (d1 > max){
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]
                { Double.valueOf(d1), Integer.valueOf(max) });
            }
        }

        return d1;
    }
}