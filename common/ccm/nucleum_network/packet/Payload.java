package ccm.nucleum_network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Payload
{
  public boolean[] boolPayload = new boolean[0];
  public byte[] bytePayload = new byte[0];
  public int[] intPayload = new int[0];
  public float[] floatPayload = new float[0];
  public String[] stringPayload = new String[0];

  public Payload()
  {
  }

  public Payload(int boolSize, int byteSize, int intSize, int floatSize, int stringSize)
  {
    this.boolPayload = new boolean[boolSize];
    this.bytePayload = new byte[byteSize];
    this.intPayload = new int[intSize];
    this.floatPayload = new float[floatSize];
    this.stringPayload = new String[stringSize];
  }

  public void readPayloadData(DataInputStream data) throws IOException
  {
    this.boolPayload = new boolean[data.readByte()];
    this.bytePayload = new byte[data.readByte()];
    this.intPayload = new int[data.readByte()];
    this.floatPayload = new float[data.readByte()];
    this.stringPayload = new String[data.readShort()];

    for (int i = 0; i < this.boolPayload.length; i++) {
      this.boolPayload[i] = data.readBoolean();
    }
    for (int i = 0; i < this.bytePayload.length; i++) {
      this.bytePayload[i] = data.readByte();
    }
    for (int i = 0; i < this.intPayload.length; i++) {
      this.intPayload[i] = data.readInt();
    }
    for (int i = 0; i < this.floatPayload.length; i++) {
      this.floatPayload[i] = data.readFloat();
    }
    for (int i = 0; i < this.stringPayload.length; i++)
      this.stringPayload[i] = data.readUTF();
  }

  public void writePayloadData(DataOutputStream data)
    throws IOException
  {
    data.writeByte(this.boolPayload.length);
    data.writeByte(this.bytePayload.length);
    data.writeByte(this.intPayload.length);
    data.writeByte(this.floatPayload.length);
    data.writeShort(this.stringPayload.length);

    for (boolean boolData : this.boolPayload) {
      data.writeBoolean(boolData);
    }
    for (byte byteData : this.bytePayload) {
      data.writeByte(byteData);
    }
    for (int intData : this.intPayload) {
      data.writeInt(intData);
    }
    for (float floatData : this.floatPayload) {
      data.writeFloat(floatData);
    }
    for (String stringData : this.stringPayload)
      data.writeUTF(stringData);
  }
}