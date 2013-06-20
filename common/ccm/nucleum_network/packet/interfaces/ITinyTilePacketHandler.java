package ccm.nucleum_network.packet.interfaces;

import java.io.DataInputStream;
import java.io.IOException;

public abstract interface ITinyTilePacketHandler
{
  public abstract void handleTinyTilePacket(DataInputStream paramDataInputStream)
    throws IOException;
}