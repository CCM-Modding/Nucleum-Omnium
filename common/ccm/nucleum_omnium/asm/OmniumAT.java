package ccm.nucleum_omnium.asm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class OmniumAT extends AccessTransformer {
    
    private static OmniumAT     instance;
    
    private static List<String> mapFileList = new LinkedList<String>();
    
    public OmniumAT() throws IOException {
        super();
        OmniumAT.instance = this;
        
        OmniumAT.mapFileList.add("ccm_at.cfg");
        
        for (final String file : OmniumAT.mapFileList) {
            readMapFile(file);
        }
    }
    
    public static void addTransformerMap(final String mapFile) {
        if (OmniumAT.instance == null) {
            OmniumAT.mapFileList.add(mapFile);
        } else {
            OmniumAT.instance.readMapFile(mapFile);
        }
    }
    
    private void readMapFile(final String mapFile) {
        System.out.println("Adding Accesstransformer map: " + mapFile);
        try {
            final Method parentMapFile = AccessTransformer.class.getDeclaredMethod("readMapFile", String.class);
            parentMapFile.setAccessible(true);
            parentMapFile.invoke(this, mapFile);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}