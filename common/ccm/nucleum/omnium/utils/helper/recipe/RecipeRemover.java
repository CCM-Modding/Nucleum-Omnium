/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import ccm.nucleum.omnium.utils.helper.FunctionHelper;

public final class RecipeRemover
{
    public static void delete(final List<String> noCraft)
    {
        final List<ItemStack> items = new ArrayList<ItemStack>();
        // Decompose list into StringItems
        for (final String s : noCraft)
        {
            items.add(FunctionHelper.getItemFromString(s, ":"));
        }
        deleteRecipes(items);
    }

    /**
     * Iterate over recipe list, and remove a recipe when its output matches one of our ItemStacks.
     */
    public static void deleteRecipes(final List<ItemStack> items)
    {
        final List<IRecipe> minecraftRecipes = CraftingManager.getInstance().getRecipeList();
        ItemStack result;
        for (int i = 0; i < minecraftRecipes.size(); ++i)
        {
            final IRecipe tmp = minecraftRecipes.get(i);
            result = tmp.getRecipeOutput();
            if (result != null)
            {
                for (final ItemStack removedItem : items)
                {
                    /*
                     * Remove the item if the ID & meta match, OR if the IDs match, and banned meta is -1.
                     */
                    if ((result.itemID == removedItem.itemID)
                        && ((removedItem.getItemDamage() == -1) || (result.getItemDamage() == removedItem.getItemDamage())))
                    {
                        minecraftRecipes.remove(i);
                        CCMLogger.DEFAULT.debug("Recipes removed for " + removedItem.getDisplayName() + " @" + removedItem.itemID + "\n");
                        --i;
                    }
                }
            }
        }
    }
}