package ccm.nucleum.omnium.utils.handler.gui;

import ccm.nucleum.omnium.inventory.container.BaseContainer;
import ccm.nucleum.omnium.inventory.gui.BaseGui;

final class GuiWrapper
{
    private final BaseContainer server;
    private final BaseGui client;

    public GuiWrapper(BaseContainer container, BaseGui gui)
    {
        server = container;
        client = gui;
    }

    public BaseContainer getServer()
    {
        return server;
    }

    public BaseGui getClient()
    {
        return client;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((client == null) ? 0 : client.hashCode());
        result = (prime * result) + ((server == null) ? 0 : server.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GuiWrapper [");
        if (server != null)
        {
            builder.append("server=").append(server).append(", ");
        }
        if (client != null)
        {
            builder.append("client=").append(client);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof GuiWrapper))
        {
            return false;
        }
        GuiWrapper other = (GuiWrapper) obj;
        if (client == null)
        {
            if (other.client != null)
            {
                return false;
            }
        } else if (!client.equals(other.client))
        {
            return false;
        }
        if (server == null)
        {
            if (other.server != null)
            {
                return false;
            }
        } else if (!server.equals(other.server))
        {
            return false;
        }
        return true;
    }

}