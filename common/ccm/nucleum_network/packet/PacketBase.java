package ccm.nucleum_network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;

public class PacketBase {

    public int     packetType;
    public boolean isChunkDataPacket;

    public PacketBase(final int packetType, final boolean isChunkDataPacket) {

        this.packetType = packetType;
        this.isChunkDataPacket = isChunkDataPacket;
    }

    public byte[] populate() {

        final ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        final DataOutputStream data = new DataOutputStream(byteArr);

        try {
            data.writeByte(packetType);
            writeData(data);
        } catch (final IOException e) {
            e.printStackTrace(System.err);
        }

        return byteArr.toByteArray();
    }

    public void readPopulate(final DataInputStream data) {

        try {
            readData(data);
        } catch (final IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void readData(final DataInputStream data) throws IOException {}

    public void writeData(final DataOutputStream dos) throws IOException {}

    public void execute(final INetworkManager network, final Player player) {}

    public void setKey(final int key) {}
}