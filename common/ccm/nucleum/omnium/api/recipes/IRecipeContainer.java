/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.api.recipes;

import java.util.Set;

import net.minecraft.item.ItemStack;

/**
 * IRecipeContainer
 * <p>
 * An Interface for any class that wishes to be a Recipe Container, Most likely also a part of the mod's API
 * 
 * @author Captain_Shadows
 */
public interface IRecipeContainer
{

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
     * @param recipe
     *            Adds a recipe from a {@link Recipe}
     */
    public void addRecipe(final Recipe recipe);

    /**
     * @return A {@link Set} of {@link Recipe}s
     */
    public Set<Recipe> getRecipes();

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * 
     * @param item
     *            The Source ItemStack
     * @return The result ItemStack
     */
    public Recipe getResult(final ItemStack item);
}
