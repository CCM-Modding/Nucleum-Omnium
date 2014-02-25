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
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

/**
 * @author Dries007
 */
public class ShapelessRecipesType extends BaseType<ShapelessRecipes>
{
    public ShapelessRecipesType()
    {
        super(ShapelessRecipes.class);
    }

    @Override
    public NBTTagCompound getNBTFromRecipe(ShapelessRecipes recipe, ItemStack newOutput) throws IllegalAccessException
    {
        NBTTagCompound nbtRecipe = new NBTTagCompound();
        NBTTagList NBTInput = new NBTTagList();
        for (Object is : recipe.recipeItems)
        {
            if (is == null) NBTInput.appendTag(new NBTTagCompound());
            else NBTInput.appendTag(((ItemStack) is).writeToNBT(new NBTTagCompound()));
        }
        nbtRecipe.setTag(NBT_input, NBTInput);
        nbtRecipe.setCompoundTag(NBT_output, newOutput.writeToNBT(new NBTTagCompound()));

        return nbtRecipe;
    }

    @Override
    public ShapelessRecipes getRecipeFromNBT(NBTTagCompound nbtRecipe)
    {
        ItemStack output = ItemStack.loadItemStackFromNBT(nbtRecipe.getCompoundTag(NBT_output));
        NBTTagList list = nbtRecipe.getTagList(NBT_input);

        ArrayList<ItemStack> input = new ArrayList<ItemStack>();
        for (int i = 0; i < list.tagCount(); i++) input.add(ItemStack.loadItemStackFromNBT((NBTTagCompound) list.tagAt(i)));

        return new ShapelessRecipes(output, input);
    }

    @Override
    public boolean equalsExceptOutput(ShapelessRecipes recipe1, ShapelessRecipes recipe2) throws IllegalAccessException
    {
        return RecipeHelper.inputEquals(recipe1.recipeItems, recipe2.recipeItems);
    }
}
