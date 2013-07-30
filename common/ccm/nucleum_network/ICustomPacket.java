package ccm.nucleum_network;

import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * A interface that declares a Packet250CustomPayload
 */
public interface ICustomPacket {

    /**
     * @return the Packet250CustomPayload
     */
    public abstract Packet250CustomPayload getPayload();
}
