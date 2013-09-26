/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.item.sub;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import ccm.nucleum.omnium.item.MainItem;
import ccm.nucleum.omnium.utils.helper.CCMLogger;

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
                CCMLogger.DEFAULT_LOGGER.severe("CONFLICT @ %s item slot already occupied by %s while adding %s", id, Item.itemsList[itemID], this);
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

    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX,
            final float hitY, final float hitZ)
    {
        return false;
    }

    public boolean onItemUseFirst(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX,
            final float hitY, final float hitZ)
    {
        return false;
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

    public float getStrVsBlock(final ItemStack stack, final Block block)
    {
        return 1;
    }

    public boolean hitEntity(final ItemStack stack, final EntityLivingBase entity, final EntityLivingBase entity2)
    {
        return false;
    }

    public boolean onBlockDestroyed(final ItemStack stack, final World world, final int x, final int y, final int z, final int side, final EntityLivingBase entity)
    {
        return false;
    }

    public boolean itemInteractionForEntity(final ItemStack stack, final EntityPlayer player, final EntityLivingBase entity)
    {
        return false;
    }

    public boolean doesContainerItemLeaveCraftingGrid(final ItemStack stack)
    {
        return true;
    }

    public int getColorFromItemStack(final ItemStack stack, final int par2)
    {
        return 16777215;
    }

    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int slot, final boolean current)
    {}

    public void onCreated(final ItemStack stack, final World world, final EntityPlayer player)
    {}

    public void onPlayerStoppedUsing(final ItemStack stack, final World world, final EntityPlayer player, final int par4)
    {}

    public void addInformation(final ItemStack stack, final EntityPlayer player, final List<String> list, final boolean par4)
    {}

    public void getRarity(final ItemStack stack)
    {}

    public void isItemTool(final ItemStack stack)
    {}

    public boolean onDroppedByPlayer(final ItemStack stack, final EntityPlayer player)
    {
        return true;
    }

    public boolean onBlockStartBreak(final ItemStack stack, final int x, final int y, final int z, final EntityPlayer player)
    {
        return false;
    }

    public int getRenderPasses()
    {
        return 0;
    }

    public ItemStack getContainerItemStack(final ItemStack stack)
    {
        return null;
    }

    public int getEntityLifespan(final ItemStack stack, final World world)
    {
        return 0;
    }

    public boolean hasCustomEntity(final ItemStack stack)
    {
        return false;
    }

    public Entity createEntity(final World world, final Entity location, final ItemStack stack)
    {
        return null;
    }

    public void onUsingItemTick(final ItemStack stack, final EntityPlayer player, final int count)
    {}

    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity)
    {
        return false;
    }

    public boolean onEntityItemUpdate(final EntityItem entityItem)
    {
        return false;
    }

    public float getSmeltingExperience(final ItemStack stack)
    {
        return 0;
    }

    public void onArmorTickUpdate(final World world, final EntityPlayer player, final ItemStack stack)
    {}

    public boolean isValidArmor(final ItemStack stack, final int armorType, final Entity entity)
    {
        return false;
    }

    public boolean isPotionIngredient(final ItemStack stack)
    {
        return false;
    }

    public String getPotionEffect(final ItemStack stack)
    {
        return null;
    }

    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type)
    {
        return null;
    }

    public FontRenderer getFontRenderer(final ItemStack stack)
    {
        return null;
    }

    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack stack, final int armorSlot)
    {
        return null;
    }

    public boolean onEntitySwing(final EntityLivingBase entityLiving, final ItemStack stack)
    {
        return false;
    }

    public void renderHelmetOverlay(final ItemStack stack, final EntityPlayer player, final ScaledResolution resolution, final float partialTicks, final boolean hasScreen,
            final int mouseX, final int mouseY)
    {}

    public int getDamage(final ItemStack stack)
    {
        return 0;
    }

    public int getDisplayDamage(final ItemStack stack)
    {
        return 0;
    }

    public int getMaxDamage(final ItemStack stack)
    {
        return 0;
    }

    public boolean isDamaged(final ItemStack stack)
    {
        return false;
    }

    public void setDamage(final ItemStack stack, final int damage)
    {}

    public boolean canHarvestBlock(final Block block, final ItemStack stack)
    {
        return false;
    }

    public boolean hasEffect(final ItemStack stack, final int pass)
    {
        return false;
    }
}