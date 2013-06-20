package ccm.nucleum_network.packet.interfaces;

import java.io.DataInputStream;
import net.minecraft.entity.player.EntityPlayer;

public abstract interface IGeneralPacketHandler
{
  public abstract void handlePacket(int paramInt, DataInputStream paramDataInputStream, EntityPlayer paramEntityPlayer)
    throws Exception;
}