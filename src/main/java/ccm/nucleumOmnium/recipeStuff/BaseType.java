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
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.LinkedList;

/**
 * Extend to add new recipe type to the system
 * This class registers itself, just make sure you initiate it somewhere.
 *
 * @param <T>
 * @author Dries007
 */
abstract class BaseType<T extends IRecipe>
{
    /**
     * Constants for this package only
     */

    static final String DUMMY_CHARS = "azertyuiopqsdfghjklmwxcvbn"; // Dummy caracters for the map in ShapedOreRecipeType

    static final String NBT_recipeWidth   = "recipeWidth";
    static final String NBT_recipeHeight  = "recipeHeight";
    static final String NBT_input         = "input";
    static final String NBT_output        = "output";
    static final String NBT_field_92101_f = "field_92101_f";
    static final String NBT_map           = "map";
    static final String NBT_mirror        = "mirror";
    static final String NBT_oredictname   = "oredictname";

    private final Class<T> type;

    private final LinkedList<T> addedList   = new LinkedList<T>();
    private final LinkedList<T> removedList = new LinkedList<T>();

    private boolean applied;

    /**
     * Just pass this the class your type wants. Stupid java...
     */
    @SuppressWarnings("unchecked")
    BaseType(Class<T> type)
    {
        this.type = type;
        if (!IRecipe.class.isAssignableFrom(type)) throw new IllegalArgumentException("Type must be specified and extend IRecipe.");
        RecipeRegistry.register((BaseType<IRecipe>) this);
    }

    /**
     * Don't miss private fields...
     * Used on server only.
     */
    protected abstract NBTTagCompound getNBTFromRecipe(T recipe, ItemStack newOutput) throws IllegalAccessException;

    /**
     * Don't forget to set special properties if required.
     * Used on both sides.
     */
    protected abstract T getRecipeFromNBT(NBTTagCompound nbtRecipe);

    /**
     * Check everything EXCEPT the output ItemStack.
     * Used on both sides.
     *
     * @see ccm.nucleumOmnium.helpers.RecipeHelper
     */
    protected abstract boolean equalsExceptOutput(T recipe1, T recipe2) throws IllegalAccessException;

    /**
     * Must be unique!
     * Used on both sides.
     */
    String getTypeName()
    {
        return type.getSimpleName();
    }

    /**
     * Replaces an instanceof check
     * Used on both sides.
     */
    public boolean accept(IRecipe recipe)
    {
        return type.isAssignableFrom(recipe.getClass());
    }

    /**
     * Used on both sides.
     */
    @SuppressWarnings("unchecked")
    public void apply()
    {
        if (!applied)
        {
            NucleumOmnium.getLogger().warning("[OreDictionaryFixes] APPLY " + getTypeName() + " Removed " + removedList.size() + "  & added " + addedList.size() + " recipes from " + FMLCommonHandler.instance().getEffectiveSide());
            applied = true;
            CraftingManager.getInstance().getRecipeList().removeAll(removedList);
            CraftingManager.getInstance().getRecipeList().addAll(addedList);
        }
    }

    /**
     * Used on server only.
     */
    public void applyNewOutput(T recipe, ItemStack itemStack)
    {
        try
        {
            removedList.add(recipe);
            NBTTagCompound nbtRecipe = getNBTFromRecipe(recipe, itemStack);
            addedList.add(getRecipeFromNBT(nbtRecipe));
        }
        catch (Exception e)
        {
            NucleumOmnium.getLogger().warning("[OreDictionaryFixes] Error in " + getTypeName() + " (" + recipe + "), adding back the original.");
            removedList.remove(recipe);
            e.printStackTrace();
        }
    }
}
