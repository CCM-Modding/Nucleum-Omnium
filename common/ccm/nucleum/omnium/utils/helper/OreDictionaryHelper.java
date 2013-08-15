/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.oredict.OreDictionary;

import ccm.nucleum.omnium.base.BaseNIC;

public class OreDictionaryHelper extends BaseNIC
{

    public static boolean containsOre(final String oreName)
    {

        final List<String> ores = Arrays.asList(OreDictionary.getOreNames());

        return ores.contains(oreName);
    }
}
