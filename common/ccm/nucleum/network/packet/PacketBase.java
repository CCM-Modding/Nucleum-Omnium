/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import cpw.mods.fml.common.network.Player;

public class PacketBase
{
    /** The packet's ID */
    public int packetType;
    /** If it is a ChunkDataPacket */
    public boolean isChunkDataPacket;

    public PacketBase(final int packetType, final boolean isChunkDataPacket)
    {
        this.packetType = packetType;
        this.isChunkDataPacket = isChunkDataPacket;
    }

    public void execute(final INetworkManager network, final Player player)
    {}

    public void writeData(final DataOutputStream dos) throws IOException
    {}

    public void readData(final DataInputStream data) throws IOException
    {}

    public byte[] populate()
    {
        final ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        final DataOutputStream data = new DataOutputStream(byteArr);
        try
        {
            data.writeByte(packetType);
            writeData(data);
        } catch (final IOException e)
        {
            CCMLogger.DEFAULT_LOGGER.printCatch(e, "populate FAILED");
        }
        return byteArr.toByteArray();
    }

    public void readPopulate(final DataInputStream data)
    {
        try
        {
            readData(data);
        } catch (final Exception e)
        {
            CCMLogger.DEFAULT_LOGGER.printCatch(e, "readPopulate FAILED\nDATA:\n%s", data);
        }
    }
}