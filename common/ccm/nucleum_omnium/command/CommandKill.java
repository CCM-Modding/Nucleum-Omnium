package ccm.nucleum_omnium.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import ccm.nucleum_omnium.helper.CommandHelper;
import ccm.nucleum_omnium.utils.lib.Commands;

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
		if (args.length == 1) {
			final EntityPlayerMP playerDead = CommandHelper.getPlayer(sender, args[0]);
			playerDead.attackEntityFrom(DamageSource.outOfWorld, 100000);
			playerDead.sendChatToPlayer("Ouch. That looks like it hurt.");
		} else {
			final EntityPlayerMP playerDead = CommandHelper.getPlayer(sender);
			if (args.length == 0) {
				playerDead.attackEntityFrom(DamageSource.outOfWorld, 100000);
				playerDead.sendChatToPlayer("Ouch. That looks like it hurt.");
			} else {
				playerDead.sendChatToPlayer(getCommandUsage(sender));
			}
		}
	}

	/**
	 * Adds the strings available in this command to the given list of tab completion options.
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
		return (args.length != 1) && (args.length != 2)	? null
														: CommandBase.getListOfStringsMatchingLastWord(	args,
																										MinecraftServer.getServer()
																														.getAllUsernames());
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	@Override
	public boolean isUsernameIndex(final String[] args, final int userIndex) {
		return userIndex == 0;
	}
}