package lib.cofh.util;

import net.minecraft.world.World;

public final class ServerHelper
{

    private ServerHelper()
    {}

    public static final boolean isClientWorld(final World world)
    {

        return world.isRemote;
    }

    public static final boolean isServerWorld(final World world)
    {

        return !world.isRemote;
    }

}
