package ccm.nucleum.omnium.utils.handler.mods;

import cpw.mods.fml.common.Loader;

import ccm.nucleum.omnium.utils.helper.CCMLogger;

/**
 * Internal Helper used to keep data values and run the init method
 * 
 * @author Captain_Shadows
 */
final class APIHandler
{

    private final String name;
    private final String handler;

    public APIHandler(String name, String handler)
    {
        super();
        this.name = name;
        this.handler = handler;
    }

    public void init()
    {
        // Check if the API is loaded
        if (Loader.isModLoaded(name))
        {
            // Create the temporal variables for internal usage
            Class<? extends IModHandler> tmpHandler = null;
            IModHandler tmp = null;

            try
            {
                // Try to find the Handler Class
                tmpHandler = (Class<? extends IModHandler>) Class.forName(handler);
            } catch (ClassNotFoundException e)
            {
                CCMLogger.DEFAULT_LOGGER.printCatch(e, "A CCM Mod has failed to find it's compatibility class with %s, pleace inform the CCM Team", name);
                return;
            }
            try
            {
                // Try to crate a new instance of said handler
                tmp = tmpHandler.newInstance();
            } catch (Exception e)
            {
                CCMLogger.DEFAULT_LOGGER.printCatch(e, "A CCM Mod has failed to create a new instance of it's compatibility with %s, pleace inform the CCM Team", name);
                return;
            }
            try
            {
                // Initialize the compatibility features
                tmp.init();
            } catch (final Exception e)
            {
                CCMLogger.DEFAULT_LOGGER.printCatch(e, "A CCM Mod has failed to load it's compatibility with %s, pleace inform the CCM Team", name);
                return;
            }
        }
    }
}