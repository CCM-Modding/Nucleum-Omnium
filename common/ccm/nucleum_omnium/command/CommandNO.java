package ccm.nucleum_omnium.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import ccm.nucleum_omnium.utils.lib.Commands;

public class CommandNO extends CommandBase
{

    @Override
    public String getCommandName()
    {

        return Commands.COMMAND_NO;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender commandSender)
    {

        return true;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        // TODO Auto-generated method stub

    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        return null;
    }
}