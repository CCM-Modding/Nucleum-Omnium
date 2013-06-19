package ccm.nucleum_network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.nucleum_network.packet.BasePacket;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public final class PacketHandler implements IPacketHandler {
    
    /***
     * Handles Packet250CustomPayload packets that are registered to an CCM network channel
     * 
     * @param manager
     *            The NetworkManager associated with the current platform (client/server)
     * @param packet
     *            The Packet250CustomPayload that was received
     * @param player
     *            The Player associated with the packet
     */
    @Override
    public void onPacketData(final INetworkManager manager, final Packet250CustomPayload packet, final Player player) {
        /*
         * Build a BasePacket object from the data contained within the Packet250CustomPayload packet
         */
        final BasePacket basePacket = PacketTypeHandler.buildPacket(packet.data);
        // Execute the appropriate actions based on the BasePacket type
        basePacket.execute(manager, player);
    }
}