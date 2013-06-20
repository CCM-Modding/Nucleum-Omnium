package ccm.nucleum_network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ccm.nucleum_network.packet.interfaces.IGeneralPacketHandler;
import ccm.nucleum_network.packet.interfaces.ITinyTilePacketHandler;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import cpw.mods.fml.common.network.ITinyPacketHandler;

public class TinyPacketHandler implements ITinyPacketHandler {
    private static short                            packetIdCounter = 0;
    
    public static int                               PACKET_TILE     = -1;
    
    public static Map<Short, IGeneralPacketHandler> handlers        = new HashMap<Short, IGeneralPacketHandler>();
    
    public static int getAvailablePacketIdAndRegister(IGeneralPacketHandler yourHandler) {
        packetIdCounter = (short) (packetIdCounter + 1);
        handlers.put(Short.valueOf(packetIdCounter), yourHandler);
        return packetIdCounter;
    }
    
    public void handle(NetHandler handler, Packet131MapData mapData) {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(mapData.itemData));
        try {
            if (handlers.containsKey(Short.valueOf(mapData.uniqueID))) {
                ((IGeneralPacketHandler) handlers.get(Short.valueOf(mapData.uniqueID))).handlePacket(mapData.uniqueID, data, handler.getPlayer());
            } else {
                World world = handler.getPlayer().worldObj;
                
                int x = data.readInt();
                int y = data.readInt();
                int z = data.readInt();
                
                TileEntity tile = world.getBlockTileEntity(x, y, z);
                if ((tile instanceof ITinyTilePacketHandler))
                    ((ITinyTilePacketHandler) tile).handleTinyTilePacket(data);
            }
        } catch (Exception e) {
            Handler.log(NucleumOmnium.instance, Level.SEVERE, "Packet payload failure! Please check your config files! Internal: TPH");
        }
    }
}