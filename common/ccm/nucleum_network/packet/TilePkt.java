package ccm.nucleum_network.packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TilePkt extends BasePacket {
    
    public final int x, y, z;
    
    public TilePkt(TileEntity entity) {
        x = entity.xCoord;
        y = entity.yCoord;
        z = entity.zCoord;
        
    }
    
    public TilePkt(ObjectInputStream stream) throws IOException {
        x = stream.readInt();
        y = stream.readInt();
        z = stream.readInt();
        
    }
    
    @Override
    public void writeToStream(ObjectOutputStream stream) throws IOException {
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        
    }
    
    @Override
    public int getID() {
        return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void actionClient(World world, EntityPlayer player) {
        if (world == null)
            return;
        
        world.markBlockForRenderUpdate(x, y, z);
    }
    
    @Override
    public void actionServer(World world, EntityPlayer player) {
        if (world == null)
            return;
        
        PacketDispatcher.sendPacketToAllInDimension(getPayload(), world.provider.dimensionId);
    }
    
}