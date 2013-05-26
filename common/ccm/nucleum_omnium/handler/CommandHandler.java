package ccm.nucleum_omnium.handler;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.command.CommandNO;
import ccm.nucleum_omnium.command.CommandTPS;
import ccm.nucleum_omnium.command.CommandTPX;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommandHandler
{

    public static void initCommands(FMLServerStartingEvent event)
    {

        event.registerServerCommand(new CommandNO());

        event.registerServerCommand(new CommandTPS());

        if (!NucleumOmnium.mystLoaded){
            event.registerServerCommand(new CommandTPX());
        }
    }
}