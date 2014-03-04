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

package ccm.nucleumOmnium.helpers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class MiscHelper
{
    public static boolean checkItemEquals(ItemStack is1, ItemStack is2)
    {
        return is1.itemID == is2.itemID && (is1.getItemDamage() == OreDictionary.WILDCARD_VALUE || is2.getItemDamage() == OreDictionary.WILDCARD_VALUE || is1.getItemDamage() == is2.getItemDamage());
    }

    @SafeVarargs
    public static <T> T getRandomFromSet(Random random, T... collection)
    {
        return getRandomFromSet(random, Arrays.asList(collection));
    }

    public static <T> T getRandomFromSet(Random random, Collection<T> collection)
    {
        if (collection.isEmpty()) return null;
        if (collection.size() == 1) //noinspection unchecked
            return (T) collection.toArray()[0];
        int item = random.nextInt(collection.size());
        int i = 0;
        for (T obj : collection)
        {
            if (i == item) return obj;
            i = i + 1;
        }
        return null;
    }
    
    public static NBTTagCompound getPersistentDataTag(EntityPlayer player, String name)
    {
        NBTTagCompound persistentTag = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        player.getEntityData().setCompoundTag(EntityPlayer.PERSISTED_NBT_TAG, persistentTag);
        NBTTagCompound ccmTag = persistentTag.getCompoundTag("CCM");
        persistentTag.setCompoundTag("CCM", ccmTag);
        NBTTagCompound requestedData = ccmTag.getCompoundTag(name);
        ccmTag.setCompoundTag(name, requestedData);

        return requestedData;
    }

    public static void setPersistentDataTag(EntityPlayer player, String name, NBTTagCompound tag)
    {
        if (tag == null) tag = new NBTTagCompound();
        NBTTagCompound persistentTag = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        player.getEntityData().setCompoundTag(EntityPlayer.PERSISTED_NBT_TAG, persistentTag);
        NBTTagCompound ccmTag = persistentTag.getCompoundTag("CCM");
        persistentTag.setCompoundTag("CCM", ccmTag);
        ccmTag.setCompoundTag(name, tag);
    }
}
