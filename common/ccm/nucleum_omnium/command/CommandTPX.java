package ccm.nucleum_omnium.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ccm.nucleum_omnium.helper.FunctionHelper;

public class CommandTPX extends CommandBase
{

    public String getCommandName()
    {
        return "tpx";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return sender.translateString("commands.tpx.usage", new Object[0]);
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length < 1){
            throw new WrongUsageException("commands.tpx.usage", new Object[0]);
        }else{
            EntityPlayerMP player;

            if (args.length != 2 && args.length != 5){
                player = getCommandSenderAsPlayer(sender);
            }else{
                player = getPlayer(sender, args[0]);

                if (player == null){
                    throw new PlayerNotFoundException();
                }
            }

            if (args.length != 3 && args.length != 5){
                if (args.length == 1 || args.length == 2){
                    EntityPlayerMP player1 = getPlayer(sender, args[args.length - 1]);

                    if (player1 == null){
                        throw new PlayerNotFoundException();
                    }

                    player.mountEntity((Entity) null);
                    FunctionHelper.teleportPlayer(player, player1);
                }
            }else if (player.worldObj != null){
                int i = args.length - 4;
                int d1 = parseInt(sender, args[i++]);
                double d2 = this.checkPosition(sender, player.posX, args[i++]);
                double d3 = this.checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                double d4 = this.checkPosition(sender, player.posZ, args[i++]);
                player.mountEntity((Entity) null);
                FunctionHelper.teleportPlayer(player, d1, d2, d3, d4);
                notifyAdmins(sender, "commands.tpx.success.coordinates", new Object[]
                { player.getEntityName(), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3), Double.valueOf(d4) });
            }
        }
    }

    private double checkPosition(ICommandSender sender, double postion, String argPos)
    {
        return this.checkPositionWithBounds(sender, postion, argPos, -30000000, 30000000);
    }

    private double checkPositionWithBounds(ICommandSender sender, double postion, String argPos, int min, int max)
    {
        boolean flag = argPos.startsWith("~");
        double d1 = flag ? postion : 0.0D;

        if (!flag || argPos.length() > 1){
            boolean flag1 = argPos.contains(".");

            if (flag){
                argPos = argPos.substring(1);
            }

            d1 += parseDouble(sender, argPos);

            if (!flag1 && !flag){
                d1 += 0.5D;
            }
        }

        if (min != 0 || max != 0){
            if (d1 < (double) min){
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]
                { Double.valueOf(d1), Integer.valueOf(min) });
            }

            if (d1 > (double) max){
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]
                { Double.valueOf(d1), Integer.valueOf(max) });
            }
        }

        return d1;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        return args.length != 1 && args.length != 2 ? null : getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int userIndex)
    {
        return userIndex == 0;
    }
}