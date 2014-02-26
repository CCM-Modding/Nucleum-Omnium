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

import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

import java.util.LinkedList;

/**
 * @author Dries007
 */
class Filler
{
    private int speed = 1;
    private final int dim;
    private final LinkedList<ChunkCoordIntPair> chunkCoordIntPairs = new LinkedList<ChunkCoordIntPair>();
    private       boolean                       enabled            = false;
    private       boolean                       listGenerated      = false;

    public Filler(int dim, final Shape shape, final int centerX, final int centerZ, final int size)
    {
        this.dim = dim;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                shape.generateList(chunkCoordIntPairs, centerX, centerZ, size);
                listGenerated = true;
            }
        }).start();

        TickHandler.INSTANCE.map.put(dim, this);
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("The filler will need " + getListSize() / getSpeed() + " ticks or " + (getListSize() / (20 * getSpeed())) + " seconds at " + speed + " chunks/sec.").setColor(EnumChatFormatting.GREEN));
    }

    int getListSize()
    {
        return chunkCoordIntPairs.size();
    }

    public void tick(WorldServer world)
    {
        if (!listGenerated || !enabled) return;
        for (int i = 0; i < speed; i++)
        {
            if (chunkCoordIntPairs.isEmpty())
            {
                TickHandler.INSTANCE.map.remove(dim);
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler done for dim " + dim + ". Doing a world save..."));

                boolean flag = world.canNotSave;
                world.canNotSave = false;
                try
                {
                    world.saveAllChunks(true, null);
                }
                catch (MinecraftException e)
                {
                    System.out.println("Something went wrong saving the world.");
                    e.printStackTrace();
                }
                world.canNotSave = flag;

                return;
            }

            if (getListSize() % (20 * 10 * getSpeed()) == 0)
            {
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler for " + dim + " has " + (getListSize() / 20) + " seconds to go."));
            }

            ChunkCoordIntPair chunkCoordIntPair = chunkCoordIntPairs.removeFirst();

            if (world.getChunkProvider().chunkExists(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos))
            {
                i--;
                continue;
            }

            world.getChunkProvider().loadChunk(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);

            world.setBlock(chunkCoordIntPair.getCenterXPos(), 100, chunkCoordIntPair.getCenterZPosition(), Block.glowStone.blockID);
        }
    }

    public void start()
    {
        enabled = true;
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("The filler will need " + getListSize() / getSpeed() + " ticks or " + (getListSize() / (20 * getSpeed())) + " seconds.").setColor(EnumChatFormatting.GREEN));
    }

    public void stop()
    {
        enabled = false;
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler stopped.").setColor(EnumChatFormatting.GREEN));
    }

    public boolean isEnabled()
    {
        return enabled;
    }
}
