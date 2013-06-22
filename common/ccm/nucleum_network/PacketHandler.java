package ccm.nucleum_network;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.nucleum_network.packet.BasePacket;
import ccm.nucleum_network.packet.TilePkt;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.utils.lib.Archive;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
    
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        if (!packet.channel.equals(Archive.MOD_CHANNEL))
            return;
        
        try {
            ByteArrayInputStream array = new ByteArrayInputStream(packet.data);
            ObjectInputStream stream = new ObjectInputStream(array);
            int id = stream.readInt();
            
            BasePacket parsedPacket = null;
            
            switch (id) {
                case 0:
                    parsedPacket = new TilePkt(stream);
                    break;
            }
            
            // doAction((EntityPlayer) player, parsedPacket);
            stream.close();
            array.close();
        } catch (Throwable t) {
            Handler.log("Error receiving CCM packet! " + toString(), t);
        }
        
    }
    
    // protected abstract void doAction(EntityPlayer player, PacketSRMBase packet);
}