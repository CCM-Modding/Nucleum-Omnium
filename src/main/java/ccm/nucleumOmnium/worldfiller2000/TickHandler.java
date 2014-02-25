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

package ccm.nucleumOmnium.worldfiller2000;

import ccm.nucleumOmnium.NucleumOmnium;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.WorldServer;

import java.util.EnumSet;
import java.util.HashMap;

public class TickHandler implements ITickHandler
{
    public static final TickHandler INSTANCE = new TickHandler();

    private TickHandler()
    {
        TickRegistry.registerTickHandler(INSTANCE, Side.SERVER);
    }

    public final HashMap<Integer, Filler> map = new HashMap<Integer, Filler>();

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        if (tickData[0] instanceof WorldServer)
        {
            WorldServer world = (WorldServer) tickData[0];
            int dim = world.provider.dimensionId;

            if (map.containsKey(dim))
            {
                map.get(dim).tick(world);
            }
        }
        else
        {
            NucleumOmnium.getLogger().severe("??? " + tickData[0]);
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    public String getLabel()
    {
        return "WorldFiller2000_TickHandler";
    }
}
