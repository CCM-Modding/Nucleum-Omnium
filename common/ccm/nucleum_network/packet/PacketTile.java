package ccm.nucleum_network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTile extends BasePacket
{
  public int xCoord;
  public int yCoord;
  public int zCoord;
  public Payload payload;
  public EntityPlayerMP player;

  public PacketTile(int id, EntityPlayerMP player)
  {
    this.packetId = id;
    this.payload = new Payload();

    this.player = player;
  }

  public PacketTile(int id, int x, int y, int z, Payload payload)
  {
    this.packetId = id;

    this.xCoord = x;
    this.yCoord = y;
    this.zCoord = z;

    this.payload = payload;
  }

  public TileEntity getTarget(World world)
  {
    return world.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
  }

  public boolean targetExists(World world)
  {
    return world.blockExists(this.xCoord, this.yCoord, this.zCoord);
  }

  public void readData(DataInputStream data)
    throws IOException
  {
    this.xCoord = data.readInt();
    this.yCoord = data.readInt();
    this.zCoord = data.readInt();

    this.payload.readPayloadData(data);
  }

  public void writeData(DataOutputStream data)
    throws IOException
  {
    data.writeInt(this.xCoord);
    data.writeInt(this.yCoord);
    data.writeInt(this.zCoord);

    this.payload.writePayloadData(data);
  }
}