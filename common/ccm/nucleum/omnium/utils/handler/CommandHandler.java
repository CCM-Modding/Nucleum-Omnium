/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.command.CommandKillFix;
import ccm.nucleum.omnium.command.CommandNO;
import ccm.nucleum.omnium.command.CommandTPS;
import ccm.nucleum.omnium.command.CommandTPX;
import ccm.nucleum.omnium.utils.lib.Properties;

public final class CommandHandler extends BaseNIC
{

    public static void initCommands(final FMLServerStartingEvent event)
    {

        event.registerServerCommand(new CommandNO());

        event.registerServerCommand(new CommandTPS());

        event.registerServerCommand(new CommandKillFix());

        if (!Properties.mystLoaded)
        {
            event.registerServerCommand(new CommandTPX());
        }
    }
}