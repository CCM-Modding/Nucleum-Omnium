package ccm.nucleum_omnium.handler.mods;

import ccm.nucleum_omnium.NucleumOmnium;

public class ModsMystcraft implements IModHandler
{

    @Override
    public void init()
    {
        NucleumOmnium.mystLoaded = true;
    }
}