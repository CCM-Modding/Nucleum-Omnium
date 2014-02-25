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

package ccm.nucleumOmnium.util;

import ccm.nucleumOmnium.NucleumOmnium;
import ccm.nucleumOmnium.network.packets.HandshakePacket;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static ccm.nucleumOmnium.util.NOConstants.MESSAGE_KICK_NO_NO;

public class Players
{
    private static final HashMap<String, PlayerData> playerDataHashMap = new HashMap<String, PlayerData>();

    public static PlayerData getPlayerData(Player player)
    {
        return getPlayerData((EntityPlayer) player);
    }

    public static PlayerData getPlayerData(EntityPlayer player)
    {
        return getPlayerData(player.getEntityName());
    }

    private static PlayerData getPlayerData(String player)
    {
        if (!playerDataHashMap.containsKey(player)) playerDataHashMap.put(player, new PlayerData(player));
        return playerDataHashMap.get(player);
    }

    public static final class PlayerData
    {
        public final String  username;
        public       boolean hasNucleum;

        private PlayerData(String username)
        {
            this.username = username;
        }

        public void logout()
        {

        }
    }

    public static void init()
    {
        GameRegistry.registerPlayerTracker(new IPlayerTracker()
        {
            @Override
            public void onPlayerLogin(final EntityPlayer player)
            {
                getPlayerData(player);
                new HandshakePacket().send(player);
                if (NucleumOmnium.instance.neededOnClient())
                {
                    new Timer().schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            if (!Players.getPlayerData(player).hasNucleum) ((EntityPlayerMP) player).playerNetServerHandler.kickPlayerFromServer(MESSAGE_KICK_NO_NO);
                        }
                    }, 100 * ((EntityPlayerMP) player).ping);
                }
            }

            @Override
            public void onPlayerLogout(EntityPlayer player)
            {
                Players.playerDataHashMap.remove(player.getEntityName()).logout();
            }

            @Override
            public void onPlayerChangedDimension(EntityPlayer player)
            {

            }

            @Override
            public void onPlayerRespawn(EntityPlayer player)
            {

            }
        });
    }
}
