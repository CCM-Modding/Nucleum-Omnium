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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class OreDictionaryFixes
{
    private OreDictionaryFixes() {}

    static
    {
        new ShapedRecipesType();
        new ShapelessRecipesType();
        new ShapedOreRecipeType();
        new ShapelessOreRecipeType();
    }

    public static void init()
    {
        //noinspection unchecked
        for (IRecipe recipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList())
        {
            int id = OreDictionary.getOreID(recipe.getRecipeOutput());
            if (id != -1)
            {
                String name = OreDictionary.getOreName(id);
                if (machtes(name))
                {
                    ItemStack newOut = OreDictionary.getOres(id).get(0);

                    if (!RecipeHelper.itemsEqual(recipe.getRecipeOutput(), newOut))
                    {
                        newOut = newOut.copy();
                        newOut.stackSize = recipe.getRecipeOutput().stackSize;
                        RecipeRegistry.unify(recipe, newOut);
                    }
                }
            }
        }

        RecipeRegistry.apply();
    }

    private static boolean machtes(String oreName)
    {
        for (String name : NucleumOmnium.getConfig().oreDictionaryFixesWhiteList)
        {
            if (name.endsWith("."))
            {
                if (oreName.startsWith(name.substring(0, name.length() - 1))) return true;
            }
            else
            {
                if (oreName.equals(name)) return true;
            }
        }

        return false;
    }
}
