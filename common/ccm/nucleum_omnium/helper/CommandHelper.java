package ccm.nucleum_omnium.helper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ccm.nucleum_omnium.BaseNIClass;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.utils.exeptions.WTFExeption;

public final class CommandHelper extends BaseNIClass {
    
    /**
     * Gets a player
     * 
     * @param sender
     * @return The EntityPlayerMP corresponding to who send it
     */
    public static EntityPlayerMP getPlayer(final ICommandSender sender) {
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
    public static EntityPlayerMP getPlayer(final ICommandSender sender, final String name) {
        final EntityPlayerMP player = CommandBase.func_82359_c(sender, name);
        
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return player;
    }
    
    public static void tellAdmins(final ICommandSender sender, final String message, final Object... objects) {
        CommandBase.notifyAdmins(sender, message, objects);
    }
    
    /**
     * Teleports a player to another
     * 
     * @param player
     *            Player to send
     * @param player1
     *            Player that gets send
     */
    public static void teleportPlayer(final ICommandSender sender, final EntityPlayerMP player, final EntityPlayerMP player1) {
        player.mountEntity((Entity) null);
        if (player.dimension != player1.dimension) {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, player1.dimension);
        }
        player.playerNetServerHandler.setPlayerLocation(player1.posX, player1.posY, player1.posZ, player1.rotationYaw, player1.rotationPitch);
        player.prevPosX = player.posX = player1.posX;
        player.prevPosY = player.posY = player1.posY;
        player.prevPosZ = player.posZ = player1.posZ;
        tellAdmins(sender, "commands.tpx.success", new Object[] { player.getEntityName(), player1.getEntityName() });
    }
    
    /**
     * Teleports a player to coordinates
     * 
     * @param player
     *            Player to send
     * @param dimension
     *            The dimension to send the player to
     * @param x
     *            The X coordinate to send the player to
     * @param y
     *            The Y coordinate to send the player to
     * @param z
     *            The Z coordinate to send the player to
     */
    public static void teleportPlayer(final ICommandSender sender, final EntityPlayerMP player, final int dimension, final double x, final double y, final double z) {
        player.mountEntity((Entity) null);
        if (player.dimension != dimension) {
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, dimension);
        }
        player.setPositionAndUpdate(x, y, z);
        player.prevPosX = player.posX = x;
        player.prevPosY = player.posY = y;
        player.prevPosZ = player.posZ = z;
        tellAdmins(sender,
                   "commands.tpx.success.coordinates",
                   new Object[] { player.getEntityName(), Integer.valueOf(dimension), Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
    }
    
    public static double checkPosition(final ICommandSender sender, final double postion, final String argPos) {
        return CommandHelper.checkPositionWithBounds(sender, postion, argPos, -30000000, 30000000);
    }
    
    public static double checkPositionWithBounds(final ICommandSender sender, final double postion, String argPos, final int min, final int max) {
        final boolean flag = argPos.startsWith("~");
        double d1 = flag ? postion : 0.0D;
        
        if (!flag || (argPos.length() > 1)) {
            final boolean flag1 = argPos.contains(".");
            
            if (flag) {
                argPos = argPos.substring(1);
            }
            
            d1 += CommandBase.parseDouble(sender, argPos);
            
            if (!flag1 && !flag) {
                d1 += 0.5D;
            }
        }
        
        if ((min != 0) || (max != 0)) {
            if (d1 < min) {
                if ((d1 < -30000000) && (d1 >= -300000000)) {
                    throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d1), Integer.valueOf(max) });
                } else if ((d1 < -300000000) && (d1 >= Integer.MIN_VALUE)) {
                    throw new WTFExeption(NucleumOmnium.instance);
                }
            }
            
            if (d1 > max) {
                if ((d1 > 30000000) && (d1 <= 300000000)) {
                    throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d1), Integer.valueOf(max) });
                } else if ((d1 > 300000000) && (d1 <= Integer.MAX_VALUE)) {
                    throw new WTFExeption(NucleumOmnium.instance);
                }
            }
        }
        
        return d1;
    }
}