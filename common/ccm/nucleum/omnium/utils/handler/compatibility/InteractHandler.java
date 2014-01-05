package ccm.nucleum.omnium.utils.handler.compatibility;

import ccm.nucleum.omnium.utils.helper.CCMLogger;
import cpw.mods.fml.common.Loader;

/**
 * Internal Helper used to keep data values and run the init method
 * 
 * @author Captain_Shadows
 */
final class InteractHandler
{
    private final String name;
    private final String handler;

    public InteractHandler(String name, String handler)
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
            Class<? extends InteractCompatibility> tmpHandler = null;
            InteractCompatibility tmp = null;
            try
            {
                // Try to find the Handler Class
                tmpHandler = (Class<? extends InteractCompatibility>) Class.forName(handler, false, Loader.instance().getModClassLoader());
            } catch (ClassNotFoundException e)
            {
                CCMLogger.DEFAULT.printCatch(e, "CCM has failed to find a compatibility class with %s, please inform the CCM Team", name);
                return;
            }
            try
            {
                // Try to crate a new instance of said handler
                tmp = tmpHandler.newInstance();
            } catch (Exception e)
            {
                CCMLogger.DEFAULT.printCatch(e,
                                                    "CCM has failed to create a new instance of a compatibility with %s, please inform the CCM Team",
                                                    name);
                return;
            }
            try
            {
                // Initialize the compatibility features
                tmp.init();
            } catch (final Exception e)
            {
                CCMLogger.DEFAULT.printCatch(e, "CCM has failed to load a compatibility with %s, please inform the CCM Team", name);
                return;
            }
        }
    }
}