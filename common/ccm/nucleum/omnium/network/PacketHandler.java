/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.nucleum.omnium.network.packet.PacketBase;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * A VERY simple Packet Handler, taken from EE3
 * 
 * @author pahimar
 */
public class PacketHandler implements IPacketHandler
{
    /***
     * Handles Packet250CustomPayload packets
     * 
     * @param manager
     *            The NetworkManager associated with the current platform (client/server)
     * @param packet
     *            The Packet250CustomPayload that was received
     * @param player
     *            The Player associated with the packet
     */
    @Override
    public void onPacketData(final INetworkManager manager, final Packet250CustomPayload packet, final Player player)
    {
        final PacketBase packetBase = PacketTypeHandler.buildPacket(packet.data);
        packetBase.execute(manager, player);
    }
}