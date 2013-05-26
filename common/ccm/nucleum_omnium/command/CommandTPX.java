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

    @Override
    public String getCommandName()
    {
        return "tpx";
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
        if (args.length < 1){
            throw new WrongUsageException("commands.tpx.usage", new Object[0]);
        }else{
            EntityPlayerMP player;

            if ((args.length != 2) && (args.length != 5)){
                player = getCommandSenderAsPlayer(sender);
            }else{
                player = func_82359_c(sender, args[0]);

                if (player == null){
                    throw new PlayerNotFoundException();
                }
            }

            if ((args.length != 3) && (args.length != 5)){
                if ((args.length == 1) || (args.length == 2)){
                    final EntityPlayerMP player1 = func_82359_c(sender, args[args.length - 1]);

                    if (player1 == null){
                        throw new PlayerNotFoundException();
                    }

                    player.mountEntity((Entity) null);
                    FunctionHelper.teleportPlayer(player, player1);
                }
            }else if (player.worldObj != null){
                int i = args.length - 4;
                final int d1 = parseInt(sender, args[i++]);
                final double d2 = this.checkPosition(sender, player.posX, args[i++]);
                final double d3 = this.checkPositionWithBounds(sender, player.posY, args[i++], 0, 0);
                final double d4 = this.checkPosition(sender, player.posZ, args[i++]);
                player.mountEntity((Entity) null);
                FunctionHelper.teleportPlayer(player, d1, d2, d3, d4);
                notifyAdmins(sender, "commands.tpx.success.coordinates", new Object[]
                { player.getEntityName(), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3), Double.valueOf(d4) });
            }
        }
    }

    private double checkPosition(final ICommandSender sender, final double postion, final String argPos)
    {
        return this.checkPositionWithBounds(sender, postion, argPos, -30000000, 30000000);
    }

    private double checkPositionWithBounds(final ICommandSender sender, final double postion, String argPos, final int min, final int max)
    {
        final boolean flag = argPos.startsWith("~");
        double d1 = flag ? postion : 0.0D;

        if (!flag || (argPos.length() > 1)){
            final boolean flag1 = argPos.contains(".");

            if (flag){
                argPos = argPos.substring(1);
            }

            d1 += parseDouble(sender, argPos);

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

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args)
    {
        return (args.length != 1) && (args.length != 2) ? null : getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
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