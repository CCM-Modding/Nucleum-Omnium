/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.config;

import ccm.nucleum.omnium.BaseNIC;
import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.configuration.AdvConfiguration;

public final class ConfigurationHandler extends BaseNIC
{
    /**
     * @param config
     *            The {@link IConfig} that needs to be run
     */
    public static void init(final IMod mod, final IConfig config)
    {
        final AdvConfiguration temp = config.getConfiguration();
        try
        {
            mod.getLogger().debug("LOADING CONFIGURATION FOR %s", mod.getName());

            // Loads a pre-existing Configuration file.
            temp.load();

            config.init();
            
        } catch (final Exception e)
        {
            mod.getLogger().printCatch(e, "%s HAS HAD A PROBLEM LOADING ITS CONFIGURATION", mod.getName());
        } finally
        {
            temp.save();
        }
    }
}