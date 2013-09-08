package ccm.nucleum.omnium.block.sub;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ccm.nucleum.omnium.block.texture.CropTexture;
import ccm.nucleum.omnium.block.tile.TileBase;
import ccm.nucleum.omnium.utils.helper.item.drops.DropBundle;
import ccm.nucleum.omnium.utils.helper.item.drops.DropItem;

public class SubCrop extends SubBlock
{
    private DropBundle drops;

    public SubCrop(final int id, final int meta, final Material material, final String iconName, final int stages, DropBundle drops)
    {
        super(id, meta, material, new CropTexture(iconName, stages), new TileBase());
        this.drops = drops;
    }

    public SubCrop(final int id, final int meta, final String iconName, final int stages, DropBundle drops)
    {
        this(id, meta, Material.plants, iconName, stages, drops);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int fortune)
    {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        for (DropItem item : drops.getDrops())
        {
            final int count = quantityDroppedWithBonus(item.getMin(), item.getMax(), fortune, world.rand);
            item.getItem().getWrappedStack().stackSize = 1;
            for (int i = 0; i < count; i++)
            {
                ret.add(item.getItem().getWrappedStack());
            }
        }
        return ret;
    }
}