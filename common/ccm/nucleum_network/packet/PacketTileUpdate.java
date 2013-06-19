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
    
    public PacketTileUpdate(final int x, final int y, final int z, final ForgeDirection orientation, final short state, final String owner, final String customName) {
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
        NucleumNetwork.proxy.handleTileEntityPacket(x, y, z, ForgeDirection.getOrientation(orientation), state, owner, customName);
    }
    
    @Override
    public void readData(final DataInputStream data) throws IOException {
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        orientation = data.readByte();
        state = data.readShort();
        owner = data.readUTF();
        customName = data.readUTF();
    }
    
    @Override
    public void writeData(final DataOutputStream data) throws IOException {
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeByte(orientation);
        data.writeShort(state);
        data.writeUTF(owner);
        data.writeUTF(customName);
    }
}