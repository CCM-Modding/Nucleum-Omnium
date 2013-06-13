package ccm.nucleum.network;

import net.minecraft.network.packet.Packet250CustomPayload;

public interface ICustomPacket {

    public abstract Packet250CustomPayload getPayload();
}
