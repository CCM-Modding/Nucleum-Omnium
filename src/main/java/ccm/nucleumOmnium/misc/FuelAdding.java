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

package ccm.nucleumOmnium.misc;

import ccm.nucleumOmnium.NucleumOmnium;
import ccm.nucleumOmnium.helpers.MiscHelper;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Dries007
 */
public class FuelAdding
{
    private FuelAdding() {}

    private static final HashSet<ItemStackOrOreDictEntry> fuelSet = new HashSet<ItemStackOrOreDictEntry>();

    public static void init()
    {
        String[] fuelEdits = NucleumOmnium.getConfig().fuelEdits;

        for (String edit : fuelEdits)
        {
            String[] split = edit.split(":");

            int fuelLevel = Integer.parseInt(split[split.length - 1]);

            if (Arrays.asList(OreDictionary.getOreNames()).contains(split[0]))
            {
                fuelSet.add(new ItemStackOrOreDictEntry(split[0], fuelLevel));
            }
            else
            {
                int id = Integer.parseInt(split[0]);
                int meta = OreDictionary.WILDCARD_VALUE;
                if (split.length == 3) meta = Integer.parseInt(split[1]);

                fuelSet.add(new ItemStackOrOreDictEntry(new ItemStack(id, 1, meta), fuelLevel));
            }
        }

        GameRegistry.registerFuelHandler(new IFuelHandler()
        {
            @Override
            public int getBurnTime(ItemStack fuel)
            {
                for (ItemStackOrOreDictEntry entry : fuelSet)
                {
                    if (entry.is(fuel))
                    {
                        return entry.getFuelLvl();
                    }
                }
                return 0;
            }
        });
    }

    public static final class ItemStackOrOreDictEntry
    {
        private final int     fuelLvl;
        private final Object  entry;
        private final boolean isItemStack;

        public ItemStackOrOreDictEntry(ItemStack itemStack, int fuelLvl)
        {
            this.fuelLvl = fuelLvl;
            this.entry = itemStack;
            this.isItemStack = true;
        }

        public ItemStackOrOreDictEntry(String oreDictEntry, int fuelLvl)
        {
            this.fuelLvl = fuelLvl;
            this.entry = oreDictEntry;
            this.isItemStack = false;
        }

        public int getFuelLvl()
        {
            return fuelLvl;
        }

        public boolean is(ItemStack fuel)
        {
            if (isItemStack) return MiscHelper.checkItemEquals(fuel, (ItemStack) entry);
            else return OreDictionary.getOres((String) entry).contains(fuel);
        }
    }
}
