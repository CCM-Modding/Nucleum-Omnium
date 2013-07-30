package ccm.nucleum_network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import ccm.nucleum_network.packet.PacketBase;
import ccm.nucleum_omnium.utils.lib.Archive;

public final class PacketTypeHandler {

    private static Map<Integer, Class<? extends PacketBase>> types    = new HashMap<Integer, Class<? extends PacketBase>>();

    private static final PacketTypeHandler                   INSTANCE = new PacketTypeHandler();

    public static PacketTypeHandler instance() {
        return INSTANCE;
    }

    public static void registerPacket(final int id, final Class<? extends PacketBase> packet) {
        if (!types.containsKey(Integer.valueOf(id))) {
            types.put(Integer.valueOf(id), packet);
        }
    }

    public static PacketBase buildPacket(final byte[] data) {

        final ByteArrayInputStream bytArray = new ByteArrayInputStream(data);
        final int selector = bytArray.read();
        final DataInputStream dataStream = new DataInputStream(bytArray);

        PacketBase packet = null;

        try {
            packet = types.get(selector).newInstance();
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dataStream);

        return packet;
    }

    public static PacketBase buildPacket(final int id) {

        PacketBase packet = null;

        try {
            packet = types.get(id).newInstance();
        } catch (final Exception e) {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(final PacketBase packetBase) {

        final byte[] data = packetBase.populate();

        final Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Archive.MOD_CHANNEL;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packetBase.isChunkDataPacket;

        return packet250;
    }
}
