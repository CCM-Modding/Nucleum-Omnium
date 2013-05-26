package ccm.nucleum_omnium.handler.mods;

import ccm.nucleum_omnium.utils.lib.Archive;

public class ModsMystcraft implements IModHandler
{

    @Override
    public void init()
    {
        Archive.mystLoaded = true;
    }
}