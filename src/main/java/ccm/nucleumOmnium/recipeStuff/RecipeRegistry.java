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

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

/**
 * Replaces a lot of the old helper crap
 *
 * @author Dries007
 */
class RecipeRegistry
{
    private static final ArrayList<BaseType<IRecipe>> typeList = new ArrayList<BaseType<IRecipe>>();

    public static void apply()
    {
        for (BaseType<IRecipe> baseType : typeList)
        {
            baseType.apply();
        }
    }

    /**
     * Used on both sides.
     */
    static void register(BaseType<IRecipe> baseType)
    {
        typeList.add(baseType);
    }

    public static void unify(IRecipe recipe, ItemStack newOut)
    {
        for (BaseType<IRecipe> baseType : typeList)
        {
            if (baseType.accept(recipe))
            {
                System.out.println("Unifying " + recipe.getRecipeOutput() + " with " + newOut);
                baseType.applyNewOutput(recipe, newOut);

                break;
            }
        }
    }
}
