/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.api.recipes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ccm.nucleum.omnium.utils.helper.item.WrapperStack;

/**
 * IRecipeContainer
 * <p>
 * An Abstract Class for any class that wishes to be a Recipe Container, Most likely also a part of the mod's API
 * 
 * @author Captain_Shadows
 */
public abstract class IRecipeContainer
{
    /** Set containing all the recipes stored in this container */
    protected final Set<Recipe> recipes = new HashSet<Recipe>();

    public void addRecipe(final Recipe recipe)
    {
        if (recipe != null)
        {
            recipes.add(recipe);
        }
    }

    public void addRecipe(final WrapperStack input, final WrapperStack output)
    {
        if (input != null && output != null)
        {
            recipes.add(new Recipe(input, output));
        }
    }

    public void addRecipe(final List<WrapperStack> inputs, final List<WrapperStack> outputs)
    {
        if (inputs != null && outputs != null)
        {
            if (!inputs.isEmpty() && !outputs.isEmpty())
            {
                recipes.add(new Recipe(inputs, outputs));
            }
        }
    }

    /**
     * @return The recipe that contains the Item as an input or null if none was found
     */
    public Recipe getResult(final WrapperStack item)
    {// Check if it contains it before looping
        if (recipes.contains(item))
        {
            // Iterate over all the stored recipes
            for (final Recipe recipe : recipes)
            {// check if it is the correct one
                if (recipe.isInput(item))
                {
                    return recipe;
                }
            }
        }
        return null;
    }

    /**
     * @return A <strong>copy</strong> of the current {@code Set<Recipe>} of stored recipes
     */
    public Set<Recipe> getRecipes()
    {
        return new HashSet<Recipe>(recipes);
    }
}
