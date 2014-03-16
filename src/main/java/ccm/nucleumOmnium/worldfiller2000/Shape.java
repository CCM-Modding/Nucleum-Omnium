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

import net.minecraft.world.ChunkCoordIntPair;

import java.util.LinkedList;

/**
 * @author Dries007
 */
public enum Shape
{
    SQUARE
            {
                @Override
                public void generateList(LinkedList<ChunkCoordIntPair> chunkCoordIntPairs, int centerX, int centerZ, int rad)
                {
                    for (int x = centerX - rad; x < centerX + rad; x++)
                    {
                        for (int z = centerZ - rad; z < centerZ + rad; z++)
                        {
                            chunkCoordIntPairs.add(new ChunkCoordIntPair(x, z));
                        }
                    }
                }
            },
    ROUND
            {
                @Override
                public void generateList(LinkedList<ChunkCoordIntPair> chunkCoordIntPairs, int centerX, int centerZ, int rad)
                {
                    for (int x = centerX - rad; x < centerX + rad; x++)
                    {
                        for (int z = centerZ - rad; z < centerZ + rad; z++)
                        {
                            if (((centerX - x) * (centerX - x)) + ((centerZ - z) * (centerZ - z)) < (rad * rad)) chunkCoordIntPairs.add(new ChunkCoordIntPair(x, z));
                        }
                    }
                }
            };

    public abstract void generateList(LinkedList<ChunkCoordIntPair> chunkCoordIntPairs, int centerX, int centerZ, int rad);
}
