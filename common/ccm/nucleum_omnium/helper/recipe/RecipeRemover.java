package ccm.nucleum_omnium.helper.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import ccm.nucleum_omnium.base.BaseNIC;
import ccm.nucleum_omnium.handler.LogHandler;

public final class RecipeRemover extends BaseNIC {

    private static List<ItemStack> items = new ArrayList<ItemStack>();

    public static void delete(final List<String> noCraft) {
        // Decompose list into StringItems
        for (final String s : noCraft) {
            items.add(getItemStack(s));
        }

        deleteRecipes();
    }

    private static ItemStack getItemStack(final String itemID) {
        int id;
        int meta;

        // Decompose String into (item ID, Meta) pairs
        id = meta = 0;
        final String[] tmp = itemID.split(":");
        if ((tmp != null) && (tmp.length > 0)) {
            try {
                id = Integer.parseInt(tmp[0]);
                if (tmp.length > 1) {
                    try {
                        meta = Integer.parseInt(tmp[1]);
                    } catch (final Exception ex) {
                        meta = 0;
                    }
                }
            } catch (final Exception ex) {
                id = 0;
            }
        }
        return new ItemStack(id, 1, meta);
    }

    /*
     * Iterate over recipe list, and remove a recipe when its output matches one of our
     * ItemStacks.
     */
    private static void deleteRecipes() {
        @SuppressWarnings("unchecked")
        final List<IRecipe> minecraftRecipes = CraftingManager.getInstance().getRecipeList();
        ItemStack result;
        for (int i = 0; i < minecraftRecipes.size(); ++i) {
            final IRecipe tmp = minecraftRecipes.get(i);
            result = tmp.getRecipeOutput();
            if (result != null) {
                for (final ItemStack removedItem : items) {
                    /*
                     * Remove the item if the ID & meta match, OR if the IDs match, and banned meta
                     * is -1.
                     */
                    if ((result.itemID == removedItem.itemID) && ((removedItem.getItemDamage() == -1) || (result.getItemDamage() == removedItem.getItemDamage()))) {
                        minecraftRecipes.remove(i);
                        LogHandler.debug("Recipes removed for " + removedItem.getDisplayName()
                                         + " @"
                                         + removedItem.itemID
                                         + "\n");
                        --i;
                    }
                }
            }
        }
    }
}