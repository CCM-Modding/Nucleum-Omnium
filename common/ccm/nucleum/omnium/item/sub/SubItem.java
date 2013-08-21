/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.item.sub;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import ccm.nucleum.omnium.item.MainItem;

/**
 * SubItem
 * <p>
 * 
 * @author Captain_Shadows
 */
public class SubItem
{
    public int itemID;
    public int damage;
    public MainItem item;

    public String unlocalizedName;
    private Icon itemIcon;
    private String textureName;

    public SubItem(final int id)
    {
        itemID = id + 256;

        if (Item.itemsList[itemID] == null)
        {
            item = new MainItem(id);
        } else
        {
            if (Item.itemsList[itemID] instanceof MainItem)
            {
                item = (MainItem) Item.itemsList[itemID];
            } else
            {
                System.out.println("CONFLICT @ " + id + " item slot already occupied by " + Item.itemsList[itemID] + " while adding " + this);
            }
        }

        damage = item.addSubItem(this);
    }

    /**
     * returns this;
     */
    public SubItem setCreativeTab(final CreativeTabs tab)
    {
        item.setCreativeTab(tab);
        return this;
    }

    /**
     * Sets the unlocalized name of this item to the string passed as the parameter
     */
    public SubItem setUnlocalizedName(final String name)
    {
        unlocalizedName = name;
        return this;
    }

    public SubItem setTextureName(final String texture)
    {
        textureName = texture;
        return this;
    }

    public ItemStack getItemStack()
    {
        return getItemStack(1);
    }

    public ItemStack getItemStack(final int size)
    {
        return new ItemStack(itemID, size, damage);
    }

    public String getUnlocalizedName(final ItemStack stack)
    {
        return unlocalizedName;
    }

    public void registerIcons(final IconRegister register)
    {
        itemIcon = register.registerIcon(textureName);
    }

    public ItemStack onEaten(final ItemStack stack, final World world, final EntityPlayer player)
    {
        return stack;
    }

    public int getMaxItemUseDuration(final ItemStack stack)
    {
        return 0;
    }

    public EnumAction getItemUseAction(final ItemStack stack)
    {
        return EnumAction.none;
    }

    public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player)
    {
        return stack;
    }

    public Icon getIcon()
    {
        return itemIcon;
    }

    public Icon getIcon(final ItemStack stack)
    {
        return getIcon();
    }

    public Icon getIcon(final ItemStack stack, final int pass)
    {
        return getIcon(stack);
    }

    public Icon getIcon(final ItemStack stack, final int renderPass, final EntityPlayer player, final ItemStack usingItem, final int useRemaining)
    {
        return getIcon(stack, renderPass);
    }
}