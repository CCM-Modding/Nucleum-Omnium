package ccm.nucleum.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import ccm.nucleum.utils.lib.Commands;

public class CommandKill extends CommandBase {
    
    @Override
    public String getCommandName() {
        return Commands.COMMAND_KILL;
    }
    
    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return sender.translateString("commands.kill.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) {
        final EntityPlayerMP entityplayermp = CommandBase.func_82359_c(sender, args[0]);
        entityplayermp.attackEntityFrom(DamageSource.outOfWorld, 1000);
        sender.sendChatToPlayer("Ouch. That looks like it hurt.");
    }
    
    /**
     * Adds the strings available in this command to the given list of tab
     * completion options.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        return (args.length != 1) && (args.length != 2) ? null : CommandBase
                .getListOfStringsMatchingLastWord(args, MinecraftServer.getServer()
                        .getAllUsernames());
    }
    
    /**
     * Return whether the specified command parameter index is a username
     * parameter.
     */
    @Override
    public boolean isUsernameIndex(final String[] args, final int userIndex) {
        return userIndex == 0;
    }
}