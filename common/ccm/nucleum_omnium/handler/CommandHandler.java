package ccm.nucleum_omnium.handler;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.command.CommandKill;
import ccm.nucleum_omnium.command.CommandNO;
import ccm.nucleum_omnium.command.CommandTPS;
import ccm.nucleum_omnium.command.CommandTPX;

public class CommandHandler
{

    public static void initCommands(final FMLServerStartingEvent event)
    {

        event.registerServerCommand(new CommandNO());

        event.registerServerCommand(new CommandTPS());

        event.registerServerCommand(new CommandKill());

        if (!NucleumOmnium.mystLoaded){
            event.registerServerCommand(new CommandTPX());
        }
    }
}