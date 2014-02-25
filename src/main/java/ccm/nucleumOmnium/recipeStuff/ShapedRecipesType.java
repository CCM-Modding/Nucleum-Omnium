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

import ccm.nucleumOmnium.helpers.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Field;


/**
 * @author Dries007
 */
public class ShapedRecipesType extends BaseType<ShapedRecipes>
{
    private static final Field ShapedRecipes_field_92101_f;

    static
    {
        ShapedRecipes_field_92101_f = ShapedRecipes.class.getDeclaredFields()[5];
        ShapedRecipes_field_92101_f.setAccessible(true);
    }

    public ShapedRecipesType()
    {
        super(ShapedRecipes.class);
    }

    @Override
    public NBTTagCompound getNBTFromRecipe(ShapedRecipes recipe, ItemStack newOutput) throws IllegalAccessException
    {
        NBTTagCompound nbtRecipe = new NBTTagCompound();
        nbtRecipe.setInteger(NBT_recipeWidth, recipe.recipeWidth);
        nbtRecipe.setInteger(NBT_recipeHeight, recipe.recipeHeight);
        NBTTagList NBTInput = new NBTTagList();
        for (ItemStack is : recipe.recipeItems)
        {
            if (is == null) NBTInput.appendTag(new NBTTagCompound());
            else NBTInput.appendTag(is.writeToNBT(new NBTTagCompound()));
        }
        nbtRecipe.setTag(NBT_input, NBTInput);
        nbtRecipe.setCompoundTag(NBT_output, newOutput.writeToNBT(new NBTTagCompound()));
        nbtRecipe.setBoolean(NBT_field_92101_f, ShapedRecipes_field_92101_f.getBoolean(recipe));

        return nbtRecipe;
    }

    @Override
    public ShapedRecipes getRecipeFromNBT(NBTTagCompound nbtRecipe)
    {
        int width = nbtRecipe.getInteger(NBT_recipeWidth);
        int height = nbtRecipe.getInteger(NBT_recipeHeight);
        NBTTagList list = nbtRecipe.getTagList(NBT_input);

        ItemStack[] input = new ItemStack[list.tagCount()];
        for (int i = 0; i < list.tagCount(); i++) input[i] = ItemStack.loadItemStackFromNBT((NBTTagCompound) list.tagAt(i));

        ItemStack output = ItemStack.loadItemStackFromNBT(nbtRecipe.getCompoundTag(NBT_output));

        ShapedRecipes recipes = new ShapedRecipes(width, height, input, output);

        if (nbtRecipe.getBoolean(NBT_field_92101_f)) recipes.func_92100_c();

        return recipes;
    }

    @Override
    public boolean equalsExceptOutput(ShapedRecipes recipe1, ShapedRecipes recipe2) throws IllegalAccessException
    {

        return recipe1.recipeHeight == recipe2.recipeHeight &&
                recipe1.recipeWidth == recipe2.recipeWidth &&
                RecipeHelper.inputEquals(recipe1.recipeItems, recipe2.recipeItems) &&
                ShapedRecipes_field_92101_f.getBoolean(recipe1) == ShapedRecipes_field_92101_f.getBoolean(recipe2);
    }
}
