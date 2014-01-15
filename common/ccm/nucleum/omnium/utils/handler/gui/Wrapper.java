package ccm.nucleum.omnium.utils.handler.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum.omnium.inventory.container.BaseContainer;
import ccm.nucleum.omnium.inventory.gui.BaseGui;

final class Wrapper
{
    private BaseContainer server;
    @SideOnly(Side.CLIENT)
    private BaseGui client;

    @SideOnly(Side.CLIENT)
    public Wrapper(BaseContainer container, BaseGui gui)
    {
        server = container;
        client = gui;
    }

    public Wrapper(BaseContainer container)
    {
        server = container;
    }

    public BaseContainer getServer()
    {
        return server;
    }

    @SideOnly(Side.CLIENT)
    public BaseGui getClient()
    {
        return client;
    }
}