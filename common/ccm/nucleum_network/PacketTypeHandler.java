package ccm.nucleum_network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.nucleum_network.packet.BasePacket;
import ccm.nucleum_network.packet.PacketTileUpdate;
import ccm.nucleum_network.utils.lib.Archive;

public enum PacketTypeHandler {
    TILE(PacketTileUpdate.class);
    
    public static BasePacket buildPacket(final byte[] data) {
        final ByteArrayInputStream bis = new ByteArrayInputStream(data);
        final int selector = bis.read();
        final DataInputStream dis = new DataInputStream(bis);
        BasePacket packet = null;
        try {
            packet = PacketTypeHandler.values()[selector].clazz.newInstance();
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }
        packet.readPopulate(dis);
        return packet;
    }
    
    public static BasePacket buildPacket(final PacketTypeHandler type) {
        BasePacket packet = null;
        try {
            packet = PacketTypeHandler.values()[type.ordinal()].clazz.newInstance();
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }
        return packet;
    }
    
    public static Packet populatePacket(final BasePacket packet) {
        final byte[] data = packet.populate();
        final Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Archive.MOD_CHANNEL;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packet.isChunkDataPacket;
        return packet250;
    }
    
    private Class<? extends BasePacket> clazz;
    
    PacketTypeHandler(final Class<? extends BasePacket> clazz) {
        this.clazz = clazz;
    }
}