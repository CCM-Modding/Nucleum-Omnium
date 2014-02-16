/*
 * Copyright (c) 2014 CCM modding crew.
 * View members of the CCM modding crew on https://github.com/orgs/CCM-Modding/members
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ccm.nucleumOmnium.network;

import ccm.nucleumOmnium.util.Players;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public abstract class CustomPacket
{
    public abstract void handlePacker(Side side, byte[] data, Player player);

    public boolean send(Player player)
    {
        if (!Players.getPlayerData(player).hasNucleum && needsNucleum()) return false;
        PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(getChannel(), getDataForPlayer(player)), player);
        return true;
    }

    public boolean needsNucleum()
    {
        return true;
    }

    public boolean send(EntityPlayer player)
    {
        return send((Player) player);
    }

    public void sendToAllInDim(int dim)
    {
        ServerConfigurationManager mcscm = MinecraftServer.getServer().getConfigurationManager();
        for (int j = 0; j < mcscm.playerEntityList.size(); ++j)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) mcscm.playerEntityList.get(j);

            if (Players.getPlayerData(entityplayermp).hasNucleum && entityplayermp.dimension == dim)
            {
                send(entityplayermp);
            }
        }
    }

    public void sendToAll()
    {
        ServerConfigurationManager mcscm = MinecraftServer.getServer().getConfigurationManager();
        for (int j = 0; j < mcscm.playerEntityList.size(); ++j)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) mcscm.playerEntityList.get(j);

            if (Players.getPlayerData(entityplayermp).hasNucleum)
            {
                send(entityplayermp);
            }
        }
    }

    public void send()
    {
        PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(getChannel(), getDataForServer()));
    }

    public abstract byte[] getDataForPlayer(Player player);

    public abstract byte[] getDataForServer();

    public abstract String getChannel();
}
