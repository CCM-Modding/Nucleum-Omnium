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

package ccm.nucleumOmnium.network.packets;

import ccm.nucleumOmnium.NucleumOmnium;
import ccm.nucleumOmnium.network.CustomPacket;
import ccm.nucleumOmnium.util.NOConstants;
import ccm.nucleumOmnium.util.Players;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class HandshakePacket extends CustomPacket
{
    private static final String HANDSHAKE = "Digital fist bump!";

    @Override
    public void handlePacker(Side side, byte[] data, Player player)
    {
        if (new String(data).equals(HANDSHAKE))
        {
            if (side.isClient())
            {
                NucleumOmnium.instance.serverHasMod = true;
                NucleumOmnium.getLogger().info("The server has Nucleum.");
            }
            else
            {
                Players.getPlayerData(player).hasNucleum = true;
                NucleumOmnium.getLogger().info(player.toString() + " has Nucleum.");
            }
        }
        else
        {
            NucleumOmnium.getLogger().warning("We got a weird handshake on the " + side + ". It was " + new String(data));
        }
    }

    @Override
    public byte[] getDataForPlayer(Player player)
    {
        NucleumOmnium.getLogger().info("Client handshake");
        return HANDSHAKE.getBytes();
    }

    @Override
    public byte[] getDataForServer()
    {
        NucleumOmnium.getLogger().info("Server handshake");
        return HANDSHAKE.getBytes();
    }

    @Override
    public String getChannel()
    {
        return NOConstants.CHANNEL_HS;
    }

    /**
     * Gets send to find out if client has nucleum so yea...
     */
    @Override
    public boolean needsNucleum()
    {
        return false;
    }
}
