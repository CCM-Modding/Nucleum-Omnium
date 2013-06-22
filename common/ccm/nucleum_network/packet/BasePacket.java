package ccm.nucleum_network.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import ccm.nucleum_network.packet.interfaces.ICustomPacket;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.utils.lib.Archive;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BasePacket implements ICustomPacket {
    
    public BasePacket() {}
    
    /**
     * For reading packets off of the stream. Another constructor is usually good as well.
     * 
     * @param stream
     */
    public BasePacket(ObjectInputStream stream) throws IOException {}
    
    public Packet250CustomPayload getPayload() {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = Archive.MOD_CHANNEL;
        try {
            ByteArrayOutputStream array = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(array);
            stream.writeInt(getID());
            writeToStream(stream);
            stream.close();
            array.close();
            packet.data = array.toByteArray();
            packet.length = packet.data.length;
            
        } catch (Throwable t) {
            Handler.log("Error sending CCM packet! " + toString(), t);
        }
        
        return packet;
    }
    
    public abstract int getID();
    
    public abstract void writeToStream(ObjectOutputStream stream) throws IOException;
    
    @SideOnly(Side.CLIENT)
    public abstract void actionClient(World world, EntityPlayer player);
    
    public abstract void actionServer(World world, EntityPlayer player);
}