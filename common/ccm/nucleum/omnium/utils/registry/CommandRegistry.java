/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.registry;

import net.minecraft.command.CommandBase;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

import ccm.nucleum.omnium.command.CommandBanFix;
import ccm.nucleum.omnium.command.CommandCCM;
import ccm.nucleum.omnium.command.CommandKillFix;
import ccm.nucleum.omnium.command.CommandRain;
import ccm.nucleum.omnium.command.CommandTPS;
import ccm.nucleum.omnium.command.CommandTPX;
import ccm.nucleum.omnium.utils.lib.Properties;

public final class CommandRegistry
{
    public static void init(final FMLServerStartingEvent event)
    {
        register(event, new CommandCCM());
        register(event, new CommandTPS());
        register(event, new CommandKillFix());
        register(event, new CommandBanFix());
        if (!Properties.MYSTCARFT_LOADED)
        {
            register(event, new CommandTPX());
        } else
        {
            CommandCCM.registerSub(new CommandTPX());
        }
        CommandCCM.registerSub(new CommandRain());
    }

    static void register(final FMLServerStartingEvent event, final CommandBase cmd)
    {
        event.registerServerCommand(cmd);
    }
}