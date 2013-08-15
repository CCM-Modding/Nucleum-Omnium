/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.registry.recipe;

import ccm.nucleum.omnium.api.fuels.IFuelRegistry;
import ccm.nucleum.omnium.api.recipes.IRecipeContainer;

/**
 * RecipeRegistry
 * 
 * @author Captain_Shadows
 */
public abstract class IRecipeRegistry
{

    protected IFuelRegistry fuels;
    protected IRecipeContainer recipes;

    public void register()
    {
        registerFuels();
        registerRecipes();
    }

    protected abstract void registerFuels();

    protected abstract void registerRecipes();
}