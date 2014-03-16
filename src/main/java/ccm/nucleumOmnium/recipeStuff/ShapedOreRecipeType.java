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

package ccm.nucleumOmnium.recipeStuff;

import ccm.nucleumOmnium.NucleumOmnium;
import ccm.nucleumOmnium.helpers.RecipeHelper;
import com.google.common.collect.HashBiMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dries007
 */
public class ShapedOreRecipeType extends BaseType<ShapedOreRecipe>
{
    private static final Field ShapedOreRecipe_width;
    private static final Field ShapedOreRecipe_height;
    private static final Field ShapedOreRecipe_mirror;

    static
    {
        try
        {
            ShapedOreRecipe_mirror = ShapedOreRecipe.class.getDeclaredField("mirrored");
            ShapedOreRecipe_mirror.setAccessible(true);

            ShapedOreRecipe_width = ShapedOreRecipe.class.getDeclaredField("width");
            ShapedOreRecipe_width.setAccessible(true);

            ShapedOreRecipe_height = ShapedOreRecipe.class.getDeclaredField("height");
            ShapedOreRecipe_height.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            NucleumOmnium.getLogger().severe("[OreDictionaryFixes] This is going to be a problem later, so I'm stopping it here.");
            throw new RuntimeException(e);
        }
    }

    public ShapedOreRecipeType()
    {
        super(ShapedOreRecipe.class);
    }

    @Override
    public NBTTagCompound getNBTFromRecipe(ShapedOreRecipe recipe, ItemStack newOutput) throws IllegalAccessException
    {
        NBTTagCompound nbtRecipe = new NBTTagCompound();
        NBTTagList NBTInput = new NBTTagList();

        int width = ShapedOreRecipe_width.getInt(recipe);
        int height = ShapedOreRecipe_height.getInt(recipe);

        /**
         * Build a map to convert the object array into recipe format.
         */
        HashBiMap<Character, Object> map = HashBiMap.create();
        HashMap<ArrayList, Object> arrayListMap = new HashMap<ArrayList, Object>(); // Lookup map for oredict entries.
        for (Object o : recipe.getInput())
        {
            if (o == null) continue;
            if (map.containsValue(o)) continue;
            if (o instanceof ArrayList)
            {
                for (String name : OreDictionary.getOreNames())
                {
                    if (OreDictionary.getOres(name).equals(o))
                    {
                        if (map.containsValue(name)) break;
                        map.put(DUMMY_CHARS.charAt(map.size()), name);
                        arrayListMap.put((ArrayList) o, name);
                        break;
                    }
                }
            }
            else
            {
                map.put(DUMMY_CHARS.charAt(map.size()), o);
            }
        }

        /**
         * Make the recipe strings
         * aka: "aa ", "aa ", "aa "
         */
        char[][] chars = new char[height][width];
        for (int h = 0; h < height; h++)
        {
            for (int w = 0; w < width; w++)
            {
                int i = h * width + w;
                if (recipe.getInput()[i] == null) chars[h][w] = ' ';
                else if (recipe.getInput()[i] instanceof ArrayList) //noinspection SuspiciousMethodCalls
                    chars[h][w] = map.inverse().get(arrayListMap.get(recipe.getInput()[i]));
                else chars[h][w] = map.inverse().get(recipe.getInput()[i]);
            }
            String line = new String(chars[h]);
            NBTInput.appendTag(new NBTTagString(null, line));
        }
        nbtRecipe.setTag(NBT_input, NBTInput);

        /**
         * Add the char to itemstack thing
         * aka: 'a' = "plank"
         */
        NBTTagCompound nbtMap = new NBTTagCompound();
        for (Map.Entry<Character, Object> entry : map.entrySet())
        {
            if (entry.getValue() instanceof String) nbtMap.setString(entry.getKey().toString(), entry.getValue().toString());
            else if (entry.getValue() instanceof ItemStack) nbtMap.setCompoundTag(entry.getKey().toString(), ((ItemStack) entry.getValue()).writeToNBT(new NBTTagCompound()));
            else
            {
                NucleumOmnium.getLogger().severe("[OreDictionaryFixes] NBT RECIPE ERROR: " + entry.getValue() + " IS NOT STRING OR ITEMSTACK ???");
            }
        }
        nbtRecipe.setCompoundTag(NBT_map, nbtMap);
        nbtRecipe.setCompoundTag(NBT_output, newOutput.writeToNBT(new NBTTagCompound()));
        nbtRecipe.setBoolean(NBT_mirror, ShapedOreRecipe_mirror.getBoolean(recipe));

        return nbtRecipe;
    }

    @Override
    public ShapedOreRecipe getRecipeFromNBT(NBTTagCompound nbtRecipe)
    {
        ArrayList<Object> input = new ArrayList<Object>(); // Becomes entire recipe input
        ItemStack output = ItemStack.loadItemStackFromNBT(nbtRecipe.getCompoundTag(NBT_output));

        NBTTagList inputs = nbtRecipe.getTagList(NBT_input);
        for (int i = 0; i < inputs.tagCount(); i++) input.add(((NBTTagString) inputs.tagAt(i)).data);
        NBTTagCompound map = nbtRecipe.getCompoundTag(NBT_map);
        //noinspection unchecked
        for (NBTBase entry : (Collection<NBTBase>) map.getTags())
        {
            input.add(entry.getName().charAt(0));
            if (entry instanceof NBTTagString) input.add(((NBTTagString) entry).data);
            else input.add(ItemStack.loadItemStackFromNBT((NBTTagCompound) entry));
        }

        return new ShapedOreRecipe(output, input.toArray()).setMirrored(nbtRecipe.getBoolean(NBT_mirror));
    }

    @Override
    public boolean equalsExceptOutput(ShapedOreRecipe recipe1, ShapedOreRecipe recipe2) throws IllegalAccessException
    {
        return RecipeHelper.inputEquals(recipe1.getInput(), recipe2.getInput()) && (ShapedOreRecipe_mirror.getBoolean(recipe1) == ShapedOreRecipe_mirror.getBoolean(recipe2));
    }
}
