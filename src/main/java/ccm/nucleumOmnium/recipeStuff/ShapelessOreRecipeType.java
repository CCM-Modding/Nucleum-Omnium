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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

/**
 * @author Dries007
 */
public class ShapelessOreRecipeType extends BaseType<ShapelessOreRecipe>
{
    public ShapelessOreRecipeType()
    {
        super(ShapelessOreRecipe.class);
    }

    @Override
    public NBTTagCompound getNBTFromRecipe(ShapelessOreRecipe recipe, ItemStack newOutput) throws IllegalAccessException
    {
        NBTTagCompound nbtRecipe = new NBTTagCompound();
        NBTTagList nbtInput = new NBTTagList();

        for (Object o : recipe.getInput())
        {
            if (o instanceof ArrayList)
            {
                for (String name : OreDictionary.getOreNames())
                {
                    if (OreDictionary.getOres(name).equals(o))
                    {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setString(NBT_oredictname, name);
                        nbtInput.appendTag(tag);
                        break;
                    }
                }
            }
            else if (o instanceof ItemStack)
            {
                nbtInput.appendTag(((ItemStack) o).writeToNBT(new NBTTagCompound()));
            }
            else
            {
                NucleumOmnium.getLogger().severe("[OreDictionaryFixes] NBT RECIPE ERROR: " + o + " IS NOT STRING OR ITEMSTACK ???");
            }
        }
        nbtRecipe.setTag(NBT_input, nbtInput);
        nbtRecipe.setCompoundTag(NBT_output, newOutput.writeToNBT(new NBTTagCompound()));

        return nbtRecipe;
    }

    @Override
    public ShapelessOreRecipe getRecipeFromNBT(NBTTagCompound nbtRecipe)
    {
        ArrayList<Object> input = new ArrayList<Object>();
        ItemStack output = ItemStack.loadItemStackFromNBT(nbtRecipe.getCompoundTag(NBT_output));

        NBTTagList inputs = nbtRecipe.getTagList(NBT_input);
        for (int i = 0; i < inputs.tagCount(); i++)
        {
            NBTTagCompound nbtInput = (NBTTagCompound) inputs.tagAt(i);
            if (nbtInput.hasKey(NBT_oredictname)) input.add(nbtInput.getString(NBT_oredictname));
            else input.add(ItemStack.loadItemStackFromNBT(nbtInput));
        }

        return new ShapelessOreRecipe(output, input.toArray());
    }

    @Override
    public boolean equalsExceptOutput(ShapelessOreRecipe recipe1, ShapelessOreRecipe recipe2) throws IllegalAccessException
    {
        return RecipeHelper.inputEquals(recipe1.getInput(), recipe2.getInput());
    }
}
