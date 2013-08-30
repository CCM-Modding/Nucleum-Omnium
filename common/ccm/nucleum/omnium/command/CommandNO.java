/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import ccm.nucleum.omnium.utils.lib.Commands;

public class CommandNO extends CommandBase
{

    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] args)
    {
        return null;
    }

    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender)
    {
        return true;
    }

    @Override
    public String getCommandName()
    {
        return Commands.COMMAND_NO;
    }

    @Override
    public String getCommandUsage(final ICommandSender icommandsender)
    {
        return null;
    }

    @Override
    public void processCommand(final ICommandSender icommandsender, final String[] astring)
    {}
}