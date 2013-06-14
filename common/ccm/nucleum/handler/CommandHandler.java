package ccm.nucleum.handler;

import ccm.nucleum.BaseNIClass;
import ccm.nucleum.command.CommandKill;
import ccm.nucleum.command.CommandNO;
import ccm.nucleum.command.CommandTPS;
import ccm.nucleum.command.CommandTPX;
import ccm.nucleum.utils.lib.Properties;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public final class CommandHandler extends BaseNIClass {
    
    public static void initCommands(final FMLServerStartingEvent event) {
        
        event.registerServerCommand(new CommandNO());
        
        event.registerServerCommand(new CommandTPS());
        
        event.registerServerCommand(new CommandKill());
        
        if (!Properties.mystLoaded) {
            event.registerServerCommand(new CommandTPX());
        }
    }
}