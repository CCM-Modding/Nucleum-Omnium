/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.handler;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.command.CommandKill;
import ccm.nucleum_omnium.command.CommandNO;
import ccm.nucleum_omnium.command.CommandTPS;
import ccm.nucleum_omnium.command.CommandTPX;
import ccm.nucleum_omnium.utils.lib.Properties;

public final class CommandHandler extends BaseNIC
{

    public static void initCommands(final FMLServerStartingEvent event)
    {

        event.registerServerCommand(new CommandNO());

        event.registerServerCommand(new CommandTPS());

        event.registerServerCommand(new CommandKill());

        if (!Properties.mystLoaded)
        {
            event.registerServerCommand(new CommandTPX());
        }
    }
}