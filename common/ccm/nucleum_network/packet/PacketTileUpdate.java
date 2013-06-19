package ccm.nucleum_network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraftforge.common.ForgeDirection;
import ccm.nucleum_network.NucleumNetwork;
import ccm.nucleum_network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketTileUpdate extends BasePacket {
    
    public int    x, y, z;
    
    public byte   orientation;
    
    public short  state;
    
    public String owner;
    
    public String customName;
    
    public PacketTileUpdate() {
        super(PacketTypeHandler.TILE, true);
    }
    
    public PacketTileUpdate(final int x, final int y, final int z,
            final ForgeDirection orientation, final short state, final String owner,
            final String customName) {
        super(PacketTypeHandler.TILE, true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientation = (byte) orientation.ordinal();
        this.state = state;
        this.owner = owner;
        this.customName = customName;
    }
    
    @Override
    public void execute(final INetworkManager manager, final Player player) {
        NucleumNetwork.proxy
                .handleTileEntityPacket(this.x,
                                        this.y,
                                        this.z,
                                        ForgeDirection.getOrientation(this.orientation),
                                        this.state,
                                        this.owner,
                                        this.customName);
    }
    
    @Override
    public void readData(final DataInputStream data) throws IOException {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.orientation = data.readByte();
        this.state = data.readShort();
        this.owner = data.readUTF();
        this.customName = data.readUTF();
    }
    
    @Override
    public void writeData(final DataOutputStream data) throws IOException {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.orientation);
        data.writeShort(this.state);
        data.writeUTF(this.owner);
        data.writeUTF(this.customName);
    }
}