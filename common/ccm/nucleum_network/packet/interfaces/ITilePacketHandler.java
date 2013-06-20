package ccm.nucleum_network.packet.interfaces;

import ccm.nucleum_network.packet.PacketTile;

public abstract interface ITilePacketHandler
{
  public abstract void handleTilePacket(PacketTile paramPacketTile);
}