package ccm.nucleum_omnium.api.recipes;

import java.util.Set;

import net.minecraft.item.ItemStack;

public interface IRecipeContainer {

    /**
     * @param recipe
     *            Adds a recipe from a {@link Recipe}
     */
    public void addRecipe(final Recipe recipe);

    /**
     * Adds a recipe
     * 
     * @param input
     *            The input ItemStack
     * @param output
     *            The output ItemStack
     */
    public void addRecipe(final ItemStack input, final ItemStack output);

    /**
     * Adds a recipe
     * 
     * @param input
     *            The input ItemStack
     * @param output
     *            The output ItemStack
     * @param output2
     *            The second output ItemStack
     */
    public void addRecipe(final ItemStack input, final ItemStack output, final ItemStack output2);

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * 
     * @param item
     *            The Source ItemStack
     * @return The result ItemStack
     */
    public Recipe getResult(final ItemStack item);

    /**
     * @return A {@link Set} of {@link Recipe}s
     */
    public Set<Recipe> getRecipes();
}
