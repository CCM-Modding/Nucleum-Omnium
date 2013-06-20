package ccm.nucleum_network;

import java.util.logging.Level;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.asm.OmniumModContainer;
import ccm.nucleum_omnium.handler.Handler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.ITinyPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkModHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class OmniumNMH extends NetworkModHandler {
    private ITinyPacketHandler tinyPacketHandler;
    
    public OmniumNMH(ModContainer mod) {
        super(mod, (NetworkMod) OmniumModContainer.class.getAnnotation(NetworkMod.class));
        try {
            this.tinyPacketHandler = ((ITinyPacketHandler) getMod().tinyPacketHandler().newInstance());
            NetworkRegistry.instance().registerChannel((IPacketHandler) getMod().packetHandler().newInstance(), getMod().channels()[0]);
        } catch (Exception e) {
            Handler.log(NucleumOmnium.instance, Level.SEVERE, "Failed to register packet handlers with FML!");
        }
    }
    
    public boolean hasTinyPacketHandler() {
        return this.tinyPacketHandler != null;
    }
    
    public ITinyPacketHandler getTinyPacketHandler() {
        return this.tinyPacketHandler;
    }
}