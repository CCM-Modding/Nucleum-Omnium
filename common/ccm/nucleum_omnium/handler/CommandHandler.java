package ccm.nucleum_omnium.handler;

import ccm.nucleum_omnium.BaseNIClass;
import ccm.nucleum_omnium.command.CommandKill;
import ccm.nucleum_omnium.command.CommandNO;
import ccm.nucleum_omnium.command.CommandTPS;
import ccm.nucleum_omnium.command.CommandTPX;
import ccm.nucleum_omnium.utils.lib.Properties;
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