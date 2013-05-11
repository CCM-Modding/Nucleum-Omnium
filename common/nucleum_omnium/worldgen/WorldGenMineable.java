package nucleum_omnium.worldgen;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nucleum_omnium.helper.MathHelper;

public class WorldGenMineable extends WorldGenerator
{

    private final List<Integer>    replaceableBlocks;

    private final int              numberOfBlocks;

    private final int              minableBlockId;

    private final int              density;

    private static List<Integer[]> possibleMoves;
    static{
        possibleMoves = new ArrayList<Integer[]>();
        possibleMoves.add(new Integer[]
        { 1, 0, 0 });
        possibleMoves.add(new Integer[]
        { 0, 1, 0 });
        possibleMoves.add(new Integer[]
        { 0, 0, 1 });
        possibleMoves.add(new Integer[]
        { -1, 0, 0 });
        possibleMoves.add(new Integer[]
        { 0, -1, 0 });
        possibleMoves.add(new Integer[]
        { 0, 0, -1 });
        possibleMoves.add(new Integer[]
        { 1, 1, 0 });
        possibleMoves.add(new Integer[]
        { -1, 1, 0 });
        possibleMoves.add(new Integer[]
        { 1, -1, 0 });
        possibleMoves.add(new Integer[]
        { -1, -1, 0 });
        possibleMoves.add(new Integer[]
        { 1, 0, 1 });
        possibleMoves.add(new Integer[]
        { -1, 0, 1 });
        possibleMoves.add(new Integer[]
        { 1, 0, -1 });
        possibleMoves.add(new Integer[]
        { -1, 0, -1 });
        possibleMoves.add(new Integer[]
        { 0, 1, 1 });
        possibleMoves.add(new Integer[]
        { 0, -1, 1 });
        possibleMoves.add(new Integer[]
        { 0, 1, -1 });
        possibleMoves.add(new Integer[]
        { 0, -1, -1 });
    }

    public WorldGenMineable(final int id,
                            final int size,
                            final int density,
                            final Object... replacableIDs)
    {
        this.minableBlockId = id;
        this.numberOfBlocks = MathHelper.getRandomNon0Int(size);
        this.density = MathHelper.getRandomNon0Int(density);
        this.replaceableBlocks = new ArrayList<Integer>();
        for (final Object i : replacableIDs){
            this.replaceableBlocks.add((Integer) i);
        }
    }

    @Override
    public boolean generate(final World world, final Random random, final int x, final int y, final int z)
    {
        final float f = random.nextFloat() * (float) Math.PI;
        final double d0 = x + 8 + ((Math.sin(f) * this.numberOfBlocks) / 8.0F);
        final double d1 = (x + 8) - ((Math.sin(f) * this.numberOfBlocks) / 8.0F);
        final double d2 = z + 8 + ((Math.cos(f) * this.numberOfBlocks) / 8.0F);
        final double d3 = (z + 8) - ((Math.cos(f) * this.numberOfBlocks) / 8.0F);
        final double d4 = (y + random.nextInt(3)) - 2;
        final double d5 = (y + random.nextInt(3)) - 2;
        for (int l = 0; l <= this.numberOfBlocks; ++l){
            final double d6 = d0 + (((d1 - d0) * l) / this.numberOfBlocks);
            final double d7 = d4 + (((d5 - d4) * l) / this.numberOfBlocks);
            final double d8 = d2 + (((d3 - d2) * l) / this.numberOfBlocks);
            final double d9 = (random.nextDouble() * this.numberOfBlocks) / 16.0D;
            final double d10 = ((Math.sin((l * (float) Math.PI) / this.numberOfBlocks) + 1.0F) * d9) + 1.0D;
            final double d11 = ((Math.sin((l * (float) Math.PI) / this.numberOfBlocks) + 1.0F) * d9) + 1.0D;
            final int i1 = MathHelper.floor_double(d6 - (d10 / 2.0D));
            final int j1 = MathHelper.floor_double(d7 - (d11 / 2.0D));
            final int k1 = MathHelper.floor_double(d8 - (d10 / 2.0D));
            final int l1 = MathHelper.floor_double(d6 + (d10 / 2.0D));
            final int i2 = MathHelper.floor_double(d7 + (d11 / 2.0D));
            final int j2 = MathHelper.floor_double(d8 + (d10 / 2.0D));
            for (int k2 = i1; k2 <= l1; ++k2){
                final double d12 = ((k2 + 0.5D) - d6) / (d10 / 2.0D);
                if ((d12 * d12) < 1.0D){
                    for (int l2 = j1; l2 <= i2; ++l2){
                        final double d13 = ((l2 + 0.5D) - d7) / (d11 / 2.0D);
                        if (((d12 * d12) + (d13 * d13)) < 1.0D){
                            for (int i3 = k1; i3 <= j2; ++i3){
                                final double d14 = ((i3 + 0.5D) - d8) / (d10 / 2.0D);
                                final Block block = Block.blocksList[world.getBlockId(k2, l2, i3)];
                                if (((d12 * d12) + (d13 * d13) + (d14 * d14)) < 1.0D){
                                    if (block != null){
                                        // System.out.println("Generationg in" +
                                        // block.blockID);
                                        if ((block.blockID == Block.stone.blockID)
                                            || block.isGenMineableReplaceable(world, k2, l2, i3, Block.stone.blockID)
                                            || (block.blockID == Block.netherrack.blockID)
                                            || (block.blockID == Block.whiteStone.blockID)){
                                            // System.out.println("not null" +
                                            // this.minableBlockId);
                                            world.setBlock(k2, l2, i3, this.minableBlockId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean generate2(final World world, final Random random, final int x, final int y, final int z)
    {
        final List<Integer[]> spawnedCoords = new ArrayList<Integer[]>();
        // SortedList<Integer[]> spawnedCoords = new SortedList<Integer[]>(new
        // CompareCoordinates());
        this.spawnOre(world, x, y, z);
        spawnedCoords.add(new Integer[]
        { x, y, z });
        // int trueSize = 1;
        for (int n = 1; n < this.numberOfBlocks; n++){
            final List<Integer[]> cpm = new ArrayList<Integer[]>();
            for (final Integer[] i : possibleMoves){
                cpm.add(i);
            }
            int pickedOre = (int) (((random.nextFloat() * random.nextFloat())) * spawnedCoords.size());
            pickedOre = spawnedCoords.size() - 1;
            final Integer[] coords = spawnedCoords.get(pickedOre);
            Integer[] finalCoords =
            { coords[0], coords[1], coords[2] };
            do{
                if (cpm.size() == 0){
                    n--;
                    spawnedCoords.remove(pickedOre);
                    break;
                }
                final int pick = (int) (random.nextFloat() * cpm.size());
                final Integer[] offset = cpm.get(pick);
                finalCoords = new Integer[]
                { coords[0] + offset[0], coords[1] + offset[1], coords[2] + offset[2] };
                cpm.remove(pick);
            }while (spawnedCoords.contains(finalCoords));
            if (random.nextInt(100) < this.density){
                this.spawnOre(world, finalCoords);
                // trueSize++;
            }
            spawnedCoords.add(finalCoords);
        }
        // System.out.println("Ore vein size = " + trueSize);
        return true;
    }

    public boolean spawnOre(final World world, final int x, final int y, final int z)
    {
        final int currentID = world.getBlockId(x, y, z);
        if (this.replaceableBlocks.contains(currentID)){
            world.setBlock(x, y, z, this.minableBlockId);
            return true;
        }
        return false;
    }

    public boolean spawnOre(final World world, final Integer[] coords)
    {
        return this.spawnOre(world, coords[0], coords[1], coords[2]);
    }
}