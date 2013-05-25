package ccm.nucleum_omnium.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs
{

    /**
     * The Tab's Icon
     */
    private ItemStack tabIcon;

    /**
     * Creates a new Creative Tab.
     * 
     * @param name
     *            The Tab Name
     * @param tabIcon
     *            The Tab Icon
     */
    public CreativeTab(final String name)
    {
        super(name);
    }

    /**
     * @param tabIcon
     *            The Icon for the Tab
     */
    public void init(final ItemStack tabIcon)
    {
        this.tabIcon = tabIcon;
    }

    /**
     * Gets the Icon from a Item to display as the Tab's Icon
     */
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        return this.tabIcon;
    }
}
