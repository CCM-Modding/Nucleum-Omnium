package ccm.nucleum_network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import ccm.nucleum_network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class BasePacket {
    
    public PacketTypeHandler packetType;
    
    public boolean           isChunkDataPacket;
    
    public BasePacket(final PacketTypeHandler packetType, final boolean isChunkDataPacket) {
        this.packetType = packetType;
        this.isChunkDataPacket = isChunkDataPacket;
    }
    
    public void execute(final INetworkManager network, final Player player) {}
    
    public byte[] populate() {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(bos);
        try {
            dos.writeByte(packetType.ordinal());
            writeData(dos);
        } catch (final IOException e) {
            e.printStackTrace(System.err);
        }
        return bos.toByteArray();
    }
    
    public void readData(final DataInputStream data) throws IOException {}
    
    public void readPopulate(final DataInputStream data) {
        try {
            readData(data);
        } catch (final IOException e) {
            e.printStackTrace(System.err);
        }
    }
    
    public void setKey(final int key) {}
    
    public void writeData(final DataOutputStream dos) throws IOException {}
}