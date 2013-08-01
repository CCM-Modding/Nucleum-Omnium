/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.language;

import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.utils.lib.Locations;

public class OmniumLP extends BaseNIC
{

    /**
     * Adds all the supported Languages
     */
    public static void init()
    {
        final LanguagePack pack = new LanguagePack();
        pack.setPath(Locations.LANGUAGE_FILE);
        pack.addSuport("en_US");
        pack.addSuport("es_ES");
        pack.loadLangs();
    }
}