/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import ccm.nucleum.omnium.utils.helper.JavaHelper;
import ccm.nucleum.omnium.utils.lib.Commands;
import ccm.nucleum.omnium.utils.lib.Properties;

/**
 * CommandRain
 * <p>
 * 
 * @author Captain_Shadows
 */
public class CommandRain extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return Commands.COMMAND_RAIN;
    }

    @Override
    public String getCommandUsage(final ICommandSender sender)
    {
        return Commands.COMMAND_RAIN_USAGE;
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        if (JavaHelper.toBoolean(args[0]))
        {
            Properties.RAIN = true;
        } else
        {
            Properties.RAIN = false;
        }
    }

    @Override
    public int compareTo(Object o)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}