package ccm.nucleum_omnium.network;

import net.minecraft.network.packet.Packet250CustomPayload;

public interface ICustomPacket
{
    public abstract Packet250CustomPayload getPayload();
}
