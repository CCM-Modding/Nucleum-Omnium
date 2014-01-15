/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import ccm.nucleum.omnium.utils.lib.Commands;

public class CommandCCM extends CommandBase
{
    /** All the stored commands */
    private static List<CommandBase> commands = new ArrayList<CommandBase>();

    @Override
    public String getCommandName()
    {
        return Commands.COMMAND_CCM;
    }

    @Override
    public String getCommandUsage(final ICommandSender sender)
    {
        return Commands.COMMAND_CCM_USAGE;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if (args.length == 0)
        {
            throw new WrongUsageException(Commands.COMMAND_CCM_USAGE);
        } else if ("help".equalsIgnoreCase(args[0]))
        {
            throw new WrongUsageException(Commands.COMMAND_CCM_USAGE);
        } else
        {
            boolean found = false;
            for (final CommandBase cmd : commands)
            {
                if (cmd.getCommandName().equalsIgnoreCase(args[0]))
                {
                    found = true;
                    cmd.processCommand(sender, ArrayUtils.remove(args, 0));
                }
            }
            if (!found)
            {
                throw new WrongUsageException(Commands.COMMAND_CCM_USAGE);
            }
        }
    }

    /** adds a sub command */
    public static void registerSub(final CommandBase cmd)
    {
        if (cmd != null)
        {
            if (!commands.contains(cmd))
            {
                commands.add(cmd);
            }
        }
    }

    @Override
    public int compareTo(Object o)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}