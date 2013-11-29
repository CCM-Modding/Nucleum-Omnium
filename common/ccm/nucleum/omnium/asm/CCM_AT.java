/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.asm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import ccm.nucleum.omnium.utils.helper.CCMLogger;
import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class CCM_AT extends AccessTransformer
{

    private static CCM_AT instance;

    private static List<String> mapFileList = new LinkedList<String>();

    public static void addTransformerMap(final String mapFile)
    {
        if (CCM_AT.instance == null)
        {
            CCM_AT.mapFileList.add(mapFile);
        } else
        {
            CCM_AT.instance.readMapFile(mapFile);
        }
    }

    public CCM_AT() throws IOException
    {
        super();
        CCM_AT.instance = this;

        CCM_AT.mapFileList.add("ccm_at.cfg");

        for (final String file : CCM_AT.mapFileList)
        {
            readMapFile(file);
        }
    }

    private void readMapFile(final String mapFile)
    {
        CCMLogger.DEFAULT_LOGGER.debug("Adding Accesstransformer map: " + mapFile);
        try
        {
            final Method parentMapFile = AccessTransformer.class.getDeclaredMethod("readMapFile", String.class);
            parentMapFile.setAccessible(true);
            parentMapFile.invoke(this, mapFile);
        } catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}