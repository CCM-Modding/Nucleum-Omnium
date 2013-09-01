/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.handler.config;

import java.util.logging.Level;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.base.BaseNIC;
import ccm.nucleum.omnium.configuration.AdvConfiguration;
import ccm.nucleum.omnium.utils.handler.LogHandler;

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
            LogHandler.debug("Loading configuration for %s", mod.getName());

            // Loads a pre-existing Configuration file.
            temp.load();

            config.init();

        } catch (final Exception e)
        {
            LogHandler.log(mod, Level.SEVERE, mod.getName() + " has had a problem loading its configuration\n");
            e.printStackTrace();
        } finally
        {
            temp.save();
        }
    }

    /**
     * @param mod
     *            The Mod that owns this Configuration
     * @param iConf
     *            The Class that implements {@link IConfig}
     */
    public static void init(final IMod mod, final Class<? extends IConfig> iConf)
    {
        try
        {
            init(mod, iConf.newInstance().setConfiguration(mod.getConfigFile()));
        } catch (final Exception e)
        {
            LogHandler.severe(mod, "%s has had a problem creating an instance of its configuration", mod.getName());
            e.printStackTrace();
        }
    }
}