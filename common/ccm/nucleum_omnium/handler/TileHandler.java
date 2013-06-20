package ccm.nucleum_omnium.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import ccm.nucleum_omnium.IMod;
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
    
    public void registerTileEntity(final IMod mod, final String tileID, final TileEntity te) {
        final String fixedID = mod.getName() + tileID;
        GameRegistry.registerTileEntity(te.getClass(), fixedID);
        tileList.put(fixedID.hashCode(), te);
    }
    
    public TileEntity getTileEntity(final IMod mod, final String tileID) {
        
        return tileList.get((mod.getName() + tileID).hashCode());
    }
}