/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.item;

import java.util.ArrayList;
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
import net.minecraft.item.EnumRarity;
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

        return null;
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

    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX,
            final float hitY, final float hitZ)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    public boolean onItemUseFirst(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX,
            final float hitY, final float hitZ)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    public boolean hitEntity(final ItemStack stack, final EntityLivingBase entity, final EntityLivingBase entity2)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).hitEntity(stack, entity, entity2);
        }
        return false;
    }

    @Override
    public boolean onBlockDestroyed(final ItemStack stack, final World world, final int x, final int y, final int z, final int side, final EntityLivingBase entity)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onBlockDestroyed(stack, world, x, y, z, side, entity);
        }
        return false;
    }

    @Override
    /**
     * itemInteractionForEntity
     */
    public boolean func_111207_a(final ItemStack stack, final EntityPlayer player, final EntityLivingBase entity)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).itemInteractionForEntity(stack, player, entity);
        }
        return false;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).doesContainerItemLeaveCraftingGrid(stack);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack stack, final int par2)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getColorFromItemStack(stack, par2);
        }
        return 16777215;
    }

    @Override
    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int slot, final boolean current)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).onUpdate(stack, world, entity, slot, current);
        }
    }

    @Override
    public void onCreated(final ItemStack stack, final World world, final EntityPlayer player)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).onCreated(stack, world, player);
        }
    }

    @Override
    public void onPlayerStoppedUsing(final ItemStack stack, final World world, final EntityPlayer player, final int par4)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).onPlayerStoppedUsing(stack, world, player, par4);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).addInformation(stack, player, list, par4);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).getRarity(stack);
        }
        return stack.isItemEnchanted() ? EnumRarity.rare : EnumRarity.common;
    }

    @Override
    public boolean isItemTool(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).isItemTool(stack);
        }
        return (getItemStackLimit() == 1) && isDamageable();
    }

    @Override
    public boolean onDroppedByPlayer(final ItemStack stack, final EntityPlayer player)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onDroppedByPlayer(stack, player);
        }
        return true;
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

    @Override
    public float getStrVsBlock(final ItemStack stack, final Block block, final int metadata)
    {
        if (metadata < subItems.size())
        {
            return subItems.get(metadata).getStrVsBlock(stack, block);
        }
        return 1;
    }

    @Override
    public float getStrVsBlock(final ItemStack stack, final Block block)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getStrVsBlock(stack, block);
        }
        return 1;
    }

    @Override
    public boolean onBlockStartBreak(final ItemStack stack, final int x, final int y, final int z, final EntityPlayer player)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onBlockStartBreak(stack, x, y, z, player);
        }
        return false;
    }

    @Override
    public int getRenderPasses(final int metadata)
    {
        if (metadata < subItems.size())
        {
            return subItems.get(metadata).getRenderPasses();
        }
        return requiresMultipleRenderPasses() ? 2 : 1;
    }

    @Override
    public ItemStack getContainerItemStack(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getContainerItemStack(stack);
        }

        if (!hasContainerItem())
        {
            return null;
        }
        return new ItemStack(getContainerItem());
    }

    @Override
    public int getEntityLifespan(final ItemStack stack, final World world)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getEntityLifespan(stack, world);
        }
        return 6000;
    }

    @Override
    public boolean hasCustomEntity(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).hasCustomEntity(stack);
        }
        return false;
    }

    @Override
    public Entity createEntity(final World world, final Entity location, final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).createEntity(world, location, stack);
        }
        return null;
    }

    @Override
    public void onUsingItemTick(final ItemStack stack, final EntityPlayer player, final int count)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).onUsingItemTick(stack, player, count);
        }
    }

    @Override
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onLeftClickEntity(stack, player, entity);
        }
        return false;
    }

    @Override
    public boolean onEntityItemUpdate(final EntityItem entityItem)
    {
        final int damage = entityItem.getEntityItem().getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onEntityItemUpdate(entityItem);
        }
        return false;
    }

    @Override
    public float getSmeltingExperience(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getSmeltingExperience(stack);
        }
        return -1; // -1 will default to the old lookups.
    }

    @Override
    public void onArmorTickUpdate(final World world, final EntityPlayer player, final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).onArmorTickUpdate(world, player, stack);
        }
    }

    @Override
    public boolean isValidArmor(final ItemStack stack, final int armorType, final Entity entity)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).isValidArmor(stack, armorType, entity);
        }
        return false;
    }

    @Override
    public boolean isPotionIngredient(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).isPotionIngredient(stack);
        }
        return isPotionIngredient();
    }

    @Override
    public String getPotionEffect(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getPotionEffect(stack);
        }
        return getPotionEffect();
    }

    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getArmorTexture(stack, entity, slot, type);
        }
        return getArmorTexture(stack, entity, slot, (slot == 2 ? 2 : 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getFontRenderer(stack);
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack stack, final int armorSlot)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getArmorModel(entityLiving, stack, armorSlot);
        }
        return null;
    }

    @Override
    public boolean onEntitySwing(final EntityLivingBase entityLiving, final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).onEntitySwing(entityLiving, stack);
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(final ItemStack stack, final EntityPlayer player, final ScaledResolution resolution, final float partialTicks, final boolean hasScreen,
            final int mouseX, final int mouseY)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            subItems.get(damage).renderHelmetOverlay(stack, player, resolution, partialTicks, hasScreen, mouseX, mouseY);
        }
    }

    @Override
    public int getDamage(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getDamage(stack);
        }
        return stack.getItemDamage();
    }

    @Override
    public int getDisplayDamage(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getDisplayDamage(stack);
        }
        return stack.getItemDamage();
    }

    @Override
    public int getMaxDamage(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).getMaxDamage(stack);
        }
        return getMaxDamage();
    }

    @Override
    public boolean isDamaged(final ItemStack stack)
    {
        final int damage = stack.getItemDamage();
        if (damage < subItems.size())
        {
            return subItems.get(damage).isDamaged(stack);
        }
        return stack.getItemDamage() > 0;
    }

    @Override
    public void setDamage(final ItemStack stack, final int damage)
    {
        final int meta = stack.getItemDamage();
        if (meta < subItems.size())
        {
            subItems.get(meta).setDamage(stack, damage);
        } else
        {
            stack.setItemDamage(damage);

            if (stack.getItemDamage() < 0)
            {
                stack.setItemDamage(0);
            }
        }
    }

    @Override
    public boolean canHarvestBlock(final Block block, final ItemStack stack)
    {
        final int meta = stack.getItemDamage();
        if (meta < subItems.size())
        {
            return subItems.get(meta).canHarvestBlock(block, stack);
        }
        return canHarvestBlock(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack, final int pass)
    {
        final int meta = stack.getItemDamage();
        if (meta < subItems.size())
        {
            return subItems.get(meta).hasEffect(stack, pass);
        }
        return hasEffect(stack) && ((pass == 0) || (itemID != Item.potion.itemID));
    }
}