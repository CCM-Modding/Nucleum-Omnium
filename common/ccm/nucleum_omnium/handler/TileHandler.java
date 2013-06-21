package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileHandler {
    
    private static Map<Integer, TileEntity> tileList = new HashMap<Integer, TileEntity>();
    
    private static TileHandler              instance;
    
    public static TileHandler instance() {
        if (TileHandler.instance == null) {
            TileHandler.instance = new TileHandler();
        }
        return TileHandler.instance;
    }
    
    public void registerTileEntity(final String tileID, final TileEntity te) {
        
        GameRegistry.registerTileEntity(te.getClass(), hash(tileID));
        
        tileList.put(hash(tileID).hashCode(), te);
    }
    
    public TileEntity getTileEntity(final String tileID) {
        
        return tileList.get(hash(tileID));
    }
    
    private static String hash(String name) {
        return ("CCM.TILE.ENTITY." + name + "." + name.hashCode());
    }
}