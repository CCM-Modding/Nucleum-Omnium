/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import ccm.nucleum.network.packet.PacketBase;
import ccm.nucleum.omnium.CCMMod;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import ccm.nucleum.omnium.utils.lib.Archive;

public final class PacketTypeHandler
{
    /** SINGLETON Instance */
    private static final PacketTypeHandler INSTANCE = new PacketTypeHandler();
    /** PRIVATE Map used to keep all the PacketTypes */
    private Map<Integer, Class<? extends PacketBase>> types;
    /** The current Packet ID */
    private int packetID;

    /** PRIVATE Constructor */
    private PacketTypeHandler()
    {
        types = new HashMap<Integer, Class<? extends PacketBase>>();
        packetID = 0;
    }

    /** Instance getter */
    public static PacketTypeHandler instance()
    {
        return INSTANCE;
    }

    /** Builds a PacketBase from the given ID */
    public static PacketBase buildPacket(final int id)
    {
        PacketBase packet = null;
        try
        {
            packet = instance().types.get(Integer.valueOf(id)).newInstance();
        } catch (Exception e)
        {
            CCMLogger.DEFAULT_LOGGER.printCatch(e, "FAILED TO CREATE A NEW INTANCE OF\n%s\nWITH ID %s", INSTANCE.types.get(Integer.valueOf(id)), id);
        }
        return packet;
    }

    /** Builds a PacketBase from the given data, and initializes it to the data */
    public static PacketBase buildPacket(final byte[] data)
    {
        final ByteArrayInputStream bytArray = new ByteArrayInputStream(data);
        final int selector = bytArray.read();
        final DataInputStream dataStream = new DataInputStream(bytArray);

        PacketBase packet = buildPacket(selector);
        packet.readPopulate(dataStream);

        return packet;
    }

    /** Populates the given packet */
    public static Packet populatePacket(final CCMMod mod, final PacketBase packetBase)
    {
        final byte[] data = packetBase.populate();

        final Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Archive.MOD_CHANNEL;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packetBase.isChunkDataPacket;

        return packet250;
    }

    /**
     * @param packet
     *            The packet to register
     * @return The packet's ID, it is recommended to save this as you will need it to load the packet from this registry
     */
    public int registerPacket(final Class<? extends PacketBase> packet)
    {
        if (!instance().types.containsKey(Integer.valueOf(instance().packetID)))
        {
            instance().types.put(Integer.valueOf(instance().packetID), packet);
        }
        return instance().packetID++;
    }
}