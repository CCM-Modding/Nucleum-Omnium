package ccm.nucleum_omnium.world.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkDataEvent.Load;
import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.utils.lib.Properties;
import ccm.nucleum_omnium.world.utils.ChunkCoord;
import ccm.nucleum_omnium.world.utils.IOreGenerator;
import ccm.nucleum_omnium.world.utils.IOreHandler;
import ccm.nucleum_omnium.world.utils.TickHandlerWorld;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenHandler implements IWorldGenerator, IOreHandler
{

    private static List<IOreGenerator> ores               = new ArrayList<IOreGenerator>();

    private static HashSet<String>     oreNames           = new HashSet<String>();

    private static HashSet<Integer>    dimensionBlacklist = new HashSet<Integer>();

    public static WorldGenHandler      instance           = new WorldGenHandler();

    @ForgeSubscribe
    public void handleChunkSaveEvent(final ChunkDataEvent.Save event)
    {
        final NBTTagCompound tag = new NBTTagCompound();

        if (Properties.retroOreGen){
            tag.setBoolean("CCM-Ores", true);
        }
        event.getData().setTag("CCM-Nucleum_World", tag);
    }

    @ForgeSubscribe
    @SuppressWarnings("unchecked")
    public void handleChunkLoadEvent(final Load event)
    {
        final int dim = event.world.provider.dimensionId;

        if (dimensionBlacklist.contains(Integer.valueOf(dim))){
            return;
        }
        boolean ores = false;
        boolean regen = false;
        final NBTTagCompound tag = (NBTTagCompound) event.getData().getTag("CCM-Nucleum_World");

        if (tag != null){
            ores = (!tag.hasKey("CCM-Ores")) && (Properties.retroOreGen);
        }
        final ChunkCoord cCoord = new ChunkCoord(event.getChunk());

        if ((tag == null) || (Properties.retroOreGen)){
            regen = true;
        }
        if (ores){
            Handler.log(NucleumOmnium.instance, "Regenerating ores for the chunk at " + cCoord.toString() + ".");
            regen = true;
        }
        if (regen){
            ArrayList<ChunkCoord> chunks = (ArrayList<ChunkCoord>) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));

            if (chunks == null){
                TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), new ArrayList<ChunkCoord>());
                chunks = (ArrayList<ChunkCoord>) TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
            }
            if (chunks != null){
                chunks.add(cCoord);
                TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), chunks);
            }
        }
    }

    @Override
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider)
    {
        this.generateWorld(random, chunkX, chunkZ, world, true);
    }

    @Override
    public boolean registerOre(final IOreGenerator feature)
    {
        if (oreNames.contains(feature.getOreName())){
            return false;
        }
        oreNames.add(feature.getOreName());
        ores.add(feature);
        return true;
    }

    public static boolean addFeature(final IOreGenerator feature)
    {
        return instance.registerOre(feature);
    }

    public void generateWorld(final Random random, final int chunkX, final int chunkZ, final World world, final boolean newGen)
    {
        if ((!newGen) && (!Properties.retroOreGen)){
            return;
        }
        if ((world.provider.dimensionId == 1) || (world.provider.dimensionId == -1)){
            return;
        }
        for (final IOreGenerator ore : ores){
            ore.generateOre(random, chunkX, chunkZ, world, newGen);
        }
        if (!newGen){
            world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
        }
    }
}