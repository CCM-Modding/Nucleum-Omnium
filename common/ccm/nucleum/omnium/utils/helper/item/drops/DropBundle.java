package ccm.nucleum.omnium.utils.helper.item.drops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import ccm.nucleum.omnium.utils.helper.item.WrapperStack;

public class DropBundle
{
    private final List<DropItem> drops;

    public DropBundle()
    {
        this.drops = new ArrayList<DropItem>();
    }

    public DropBundle(List<DropItem> drops)
    {
        this.drops = drops;
    }

    public DropBundle(DropItem... items)
    {
        this(Arrays.asList(items));
    }

    public DropBundle(WrapperStack item, int min, int max)
    {
        this(new DropItem(item, min, max));
    }

    public DropBundle addDrop(DropItem item)
    {
        if (item != null)
        {
            drops.add(item);
        }
        return this;
    }
    
    public DropBundle addDrop(WrapperStack item, int min, int max)
    {
        return addDrop(new DropItem(item, min, max));
    }

    public List<DropItem> getDrops()
    {
        return new ArrayList<DropItem>(drops);
    }

    public ArrayList<ItemStack> toItemStack()
    {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (DropItem item : drops)
        {
            ret.add(item.getItem().getWrappedStack());
        }
        return ret;
    }
}