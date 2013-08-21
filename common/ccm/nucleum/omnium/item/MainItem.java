/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum.omnium.item.sub.SubItem;
import ccm.nucleum.omnium.utils.helper.MathHelper;

/**
 * MainItem
 * <p>
 * 
 * @author Captain_Shadows
 */
public class MainItem extends Item
{
    ArrayList<SubItem> subItems;

    public MainItem(final int id)
    {
        super(id - 256);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public int addSubItem(final SubItem subItem)
    {
        if (subItems == null)
        {
            subItems = new ArrayList<SubItem>();
        }

        subItems.add(subItem);
        return subItems.size() - 1;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have different names based on their damage or NBT.
     */
    @Override
    public String getUnlocalizedName(final ItemStack stack)
    {
        final int meta = MathHelper.clampInt(stack.getItemDamage(), 0, subItems.size());

        return "item." + subItems.get(meta).getUnlocalizedName(stack);
    }

    @SideOnly(Side.CLIENT)
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(final int id, final CreativeTabs tab, final List tabs)
    {
        for (final SubItem item : subItems)
        {
            tabs.add(item.getItemStack());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(final int meta)
    {
        if (meta < subItems.size())
        {
            return subItems.get(meta).getIcon();
        }

        return itemIcon;
    }

    @Override
    public Icon getIcon(final ItemStack stack, final int renderPass, final EntityPlayer player, final ItemStack usingItem, final int useRemaining)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getIcon(stack, renderPass, player, usingItem, useRemaining);
        }

        return getIcon(stack, renderPass);
    }

    @Override
    public Icon getIcon(final ItemStack stack, final int pass)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getIcon(stack, pass);
        }

        return getIcon(stack, pass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IconRegister register)
    {
        for (final SubItem item : subItems)
        {
            item.registerIcons(register);
        }
    }

    // ItemRedirects
    @Override
    public ItemStack onEaten(final ItemStack stack, final World world, final EntityPlayer player)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onEaten(stack, world, player);
        }

        return stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getMaxItemUseDuration(stack);
        }

        return 0;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getItemUseAction(stack);
        }

        return EnumAction.none;
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onItemRightClick(stack, world, player);
        }

        return stack;
    }
}