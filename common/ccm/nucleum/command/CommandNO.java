package ccm.nucleum.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import ccm.nucleum.utils.lib.Commands;

public class CommandNO extends CommandBase {

    @Override
    public String getCommandName() {

        return Commands.COMMAND_NO;
    }

    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {

        return true;
    }

    @Override
    public void processCommand(final ICommandSender icommandsender, final String[] astring) {
        // TODO Auto-generated method stub

    }

    @Override
    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] args) {
        return null;
    }
}