package ccm.nucleum_omnium.handler.mods;

import ccm.nucleum_omnium.utils.lib.Properties;

public class ModsMystcraft implements IModHandler
{

    @Override
    public void init()
    {
        Properties.mystLoaded = true;
    }
}