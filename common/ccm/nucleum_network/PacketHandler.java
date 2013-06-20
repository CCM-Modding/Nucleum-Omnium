package ccm.nucleum_network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ccm.nucleum_network.packet.PacketTile;
import ccm.nucleum_network.packet.interfaces.IGeneralPacketHandler;
import ccm.nucleum_network.packet.interfaces.ITilePacketHandler;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
    private static int                                packetIdCounter = 0;
    
    public static Map<Integer, IGeneralPacketHandler> handlers        = new HashMap<Integer, IGeneralPacketHandler>();
    
    public static int getAvailablePacketId() {
        return ++packetIdCounter;
    }
    
    public static int getAvailablePacketIdAndRegister(IGeneralPacketHandler yourHandler) {
        packetIdCounter += 1;
        handlers.put(Integer.valueOf(packetIdCounter), yourHandler);
        return packetIdCounter;
    }
    
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            int id = data.read();
            if (handlers.containsKey(Integer.valueOf(id))) {
                ((IGeneralPacketHandler) handlers.get(Integer.valueOf(id))).handlePacket(id, data, (EntityPlayer) player);
            } else {
                PacketTile tilePacket = new PacketTile(id, (player instanceof EntityPlayerMP) ? (EntityPlayerMP) player : null);
                tilePacket.readData(data);
                
                World world = ((EntityPlayer) player).worldObj;
                if (!tilePacket.targetExists(world)) {
                    return;
                }
                TileEntity tile = tilePacket.getTarget(world);
                if (!(tile instanceof ITilePacketHandler)) {
                    return;
                }
                ((ITilePacketHandler) tile).handleTilePacket(tilePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Handler.log(NucleumOmnium.instance, Level.SEVERE, "Packet payload failure! Please check your config files! Internal: PH");
        }
    }
}