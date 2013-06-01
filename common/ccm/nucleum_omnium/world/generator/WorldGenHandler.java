package ccm.nucleum_omnium.world.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent.Load;
import net.minecraftforge.event.world.ChunkDataEvent.Save;

import ccm.nucleum_omnium.NucleumOmnium;
import ccm.nucleum_omnium.handler.Handler;
import ccm.nucleum_omnium.helper.ChunkCoord;
import ccm.nucleum_omnium.world.utils.IOreGenerator;
import ccm.nucleum_omnium.world.utils.IOreHandler;
import ccm.nucleum_omnium.world.utils.TickHandlerWorld;
import ccm.nucleum_omnium.world.utils.lib.Properties;

public class WorldGenHandler implements IWorldGenerator, IOreHandler
{

    private final List<IOreGenerator> ores               = new ArrayList<IOreGenerator>();

    private final HashSet<String>     OreNames           = new HashSet<String>();

    private final HashSet<Integer>    dimensionBlacklist = new HashSet<Integer>();

    public static WorldGenHandler     instance           = new WorldGenHandler();

    @ForgeSubscribe
    public void handleChunkSaveEvent(final Save event)
    {
        final NBTTagCompound tag = new NBTTagCompound();

        if ((Properties.retroFlatBedrock) && (Properties.genFlatBedrock)){
            tag.setBoolean("Bedrock", true);
        }
        if (Properties.retroOreGen){
            tag.setBoolean("Ores", true);
        }
        event.getData().setTag("Properties", tag);
    }

    @ForgeSubscribe
    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    public void handleChunkLoadEvent(final Load event)
    {
        final int dim = event.world.provider.dimensionId;

        if (this.dimensionBlacklist.contains(Integer.valueOf(dim))){
            return;
        }
        boolean bedrock = false;
        boolean ores = false;
        boolean regen = false;
        final NBTTagCompound tag = (NBTTagCompound) event.getData().getTag("Properties");

        if (tag != null){
            bedrock = (!tag.hasKey("Bedrock")) && (Properties.retroFlatBedrock) && (Properties.genFlatBedrock);
            ores = (!tag.hasKey("Ores")) && (Properties.retroOreGen);
        }
        final ChunkCoord cCoord = new ChunkCoord(event.getChunk());

        if ((tag == null) && (((Properties.retroFlatBedrock) && (Properties.genFlatBedrock)) || (Properties.retroOreGen))){
            regen = true;
        }
        if (bedrock){
            Handler.log(NucleumOmnium.instance, "Regenerating flat bedrock for the chunk at " + cCoord.toString() + ".");
            regen = true;
        }
        if (ores){
            Handler.log(NucleumOmnium.instance, "Regenerating ores for the chunk at " + cCoord.toString() + ".");
            regen = true;
        }
        if (regen){
            ArrayList chunks = TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));

            if (chunks == null){
                TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), new ArrayList<ChunkCoord>());
                chunks = TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
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
    public boolean registerOre(final IOreGenerator ore)
    {
        if (this.OreNames.contains(ore.getOreName())){
            return false;
        }
        this.OreNames.add(ore.getOreName());
        this.ores.add(ore);
        return true;
    }

    public static boolean addOre(final IOreGenerator Ore)
    {
        return instance.registerOre(Ore);
    }

    public void generateWorld(final Random random, final int chunkX, final int chunkZ, final World world, final boolean newGen)
    {
        this.replaceBedrock(random, chunkX, chunkZ, world, newGen);

        if ((!newGen) && (!Properties.retroOreGen)){
            return;
        }
        if ((world.provider.dimensionId == 1) || (world.provider.dimensionId == -1)){
            return;
        }
        for (final IOreGenerator ore : this.ores){
            ore.generateOre(random, chunkX, chunkZ, world, newGen);
        }
        if (!newGen){
            world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
        }
    }

    public void replaceBedrock(final Random random, final int chunkX, final int chunkZ, final World world, final boolean newGen)
    {
        if ((!Properties.genFlatBedrock) || ((!newGen) && (!Properties.retroFlatBedrock))){
            return;
        }
        final boolean isNether = world.getBiomeGenForCoords(chunkX, chunkZ).biomeName.toLowerCase().equals("hell");

        if (isNether){
            for (int blockX = 0; blockX < 16; blockX++){
                for (int blockZ = 0; blockZ < 16; blockZ++){
                    for (int blockY = 126; blockY > 121; blockY--){
                        if (world.getBlockId((chunkX * 16) + blockX, blockY, (chunkZ * 16) + blockZ) == Block.bedrock.blockID){
                            world.setBlock((chunkX * 16) + blockX, blockY, (chunkZ * 16) + blockZ, Block.netherrack.blockID, 0, 2);
                        }
                    }
                    for (int blockY = 5; blockY > 0; blockY--){
                        if (world.getBlockId((chunkX * 16) + blockX, blockY, (chunkZ * 16) + blockZ) == Block.bedrock.blockID){
                            world.setBlock((chunkX * 16) + blockX, blockY, (chunkZ * 16) + blockZ, Block.netherrack.blockID, 0, 2);
                        }
                    }
                }
            }
        }else{
            for (int blockX = 0; blockX < 16; blockX++){
                for (int blockZ = 0; blockZ < 16; blockZ++){
                    for (int blockY = 5; blockY > 0; blockY--){
                        if (world.getBlockId((chunkX * 16) + blockX, blockY, (chunkZ * 16) + blockZ) == Block.bedrock.blockID){
                            world.setBlock((chunkX * 16) + blockX, blockY, (chunkZ * 16) + blockZ, Block.stone.blockID, 0, 2);
                        }
                    }
                }
            }
        }
    }
}