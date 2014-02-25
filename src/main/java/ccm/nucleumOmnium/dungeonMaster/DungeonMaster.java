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

package ccm.nucleumOmnium.dungeonMaster;

import ccm.nucleumOmnium.NucleumOmnium;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DungeonHooks;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class DungeonMaster
{
    private static final String DUNGEONS_PAWNING  = "DungeonSpawning";
    private static final String DUNGEON_LIST_HELP = "A list of all dungeon mobs with rarity.\nIn vanilla: 200 for Zombie, 100 for Skeleton and Spider.\nFeel free to add ones yourself.\nWILL NOT SPAWN MORE DUNGEONS!\nFormat: 'name:rarity'.";

    public static void init()
    {
        Configuration config = new Configuration(new File(NucleumOmnium.getCCMFolder(), "DungeonMaster.cfg"));

        List<String> ourDungeonMobs = new ArrayList<String>();
        try
        {
            //noinspection unchecked
            ArrayList<DungeonHooks.DungeonMob> dungeonMobs = (ArrayList<DungeonHooks.DungeonMob>) dungeonMobsField.get(null);
            for (DungeonHooks.DungeonMob mob : dungeonMobs.toArray(new DungeonHooks.DungeonMob[dungeonMobs.size()]))
            {
                ourDungeonMobs.add(mob.type + ":" + mob.itemWeight);
                DungeonHooks.removeDungeonMob(mob.type);
            }
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        for (String thing : config.get(DUNGEONS_PAWNING, "dungeonList", ourDungeonMobs.toArray(new String[ourDungeonMobs.size()]), DUNGEON_LIST_HELP).getStringList())
        {
            String[] split = thing.split(":");
            DungeonHooks.addDungeonMob(split[0], Integer.parseInt(split[1]));
        }

        config.save();
    }

    public static void saveLoot()
    {
        NBTTagCompound root = new NBTTagCompound();
        for (String key : getChestList())
        {
            NBTTagCompound tag = new NBTTagCompound(key);

            ChestGenHooks chestGenHooks = ChestGenHooks.getInfo(key);

            tag.setInteger("max", chestGenHooks.getMax());
            tag.setInteger("min", chestGenHooks.getMin());

            NBTTagList items = new NBTTagList();

            for (WeightedRandomChestContent content : getContents(chestGenHooks))
            {
                NBTTagCompound contentTag = new NBTTagCompound();

                contentTag.setCompoundTag("itemData", content.theItemId.writeToNBT(new NBTTagCompound()));
                contentTag.setInteger("weight", content.itemWeight);
                contentTag.setInteger("min", content.theMinimumChanceToGenerateItem);
                contentTag.setInteger("max", content.theMaximumChanceToGenerateItem);

                items.appendTag(contentTag);
            }

            tag.setTag("weightedItemList", items);
            root.setCompoundTag(key, tag);
        }

        try
        {
            CompressedStreamTools.write(root, getFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void loadFromFile()
    {
        if (getFile().exists())
        {
            try
            {
                NBTTagCompound root = CompressedStreamTools.read(getFile());

                //noinspection unchecked
                for (NBTTagCompound tag : (Collection<NBTTagCompound>) root.getTags())
                {
                    ChestGenHooks chestGenHooks = ChestGenHooks.getInfo(tag.getName());

                    chestGenHooks.setMax(tag.getInteger("max"));
                    chestGenHooks.setMin(tag.getInteger("min"));

                    getContents(chestGenHooks).clear();

                    NBTTagList items = tag.getTagList("weightedItemList");
                    for (int i = 0; i < items.tagCount(); i++)
                    {
                        NBTTagCompound contentTag = (NBTTagCompound) items.tagAt(i);
                        getContents(chestGenHooks).add(new WeightedRandomChestContent(ItemStack.loadItemStackFromNBT(contentTag.getCompoundTag("itemData")), contentTag.getInteger("min"), contentTag.getInteger("max"), contentTag.getInteger("weight")));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static File getFile()
    {
        return new File(NucleumOmnium.getCCMFolder(), "DungeonChestContent.dat");
    }

    @SuppressWarnings("unchecked")
    static Set<String> getChestList()
    {
        try
        {
            return ((HashMap<String, ChestGenHooks>) chestInfoField.get(null)).keySet();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    static ArrayList<WeightedRandomChestContent> getContents(ChestGenHooks chestGenHooks)
    {
        try
        {
            return (ArrayList<WeightedRandomChestContent>) contentsField.get(chestGenHooks);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Field dungeonMobsField;
    private static final Field chestInfoField;
    private static final Field contentsField;

    static
    {
        Field temp = null;
        try
        {
            temp = DungeonHooks.class.getDeclaredField("dungeonMobs");
            temp.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        dungeonMobsField = temp;

        temp = null;
        try
        {
            temp = ChestGenHooks.class.getDeclaredField("chestInfo");
            temp.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        chestInfoField = temp;

        temp = null;
        try
        {
            temp = ChestGenHooks.class.getDeclaredField("contents");
            temp.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        contentsField = temp;
    }
}
