package ccm.nucleum_network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.nucleum_omnium.NucleumOmnium;
import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class BasePacket {
    protected int     packetId;
    protected boolean isChunkDataPacket = false;
    protected String  channel           = "CCM";
    
    public int getPacketId() {
        return this.packetId;
    }
    
    public Packet getPacket() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try {
            data.writeByte(getPacketId());
            writeData(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = this.channel;
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;
        packet.isChunkDataPacket = this.isChunkDataPacket;
        return packet;
    }
    
    public Packet getTinyPacket() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try {
            writeData(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return PacketDispatcher.getTinyPacket(NucleumOmnium.instance, (short) this.packetId, bytes.toByteArray());
    }
    
    public static Packet getTinyPacket(int packetId, ByteArrayOutputStream data) {
        return PacketDispatcher.getTinyPacket(NucleumOmnium.instance, (short) packetId, data.toByteArray());
    }
    
    public abstract void readData(DataInputStream paramDataInputStream) throws IOException;
    
    public abstract void writeData(DataOutputStream paramDataOutputStream) throws IOException;
}