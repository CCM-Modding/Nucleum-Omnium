/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block.texture;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import ccm.nucleum.omnium.tileentity.BaseTE;
import ccm.nucleum.omnium.utils.lib.BlockFacings;

/**
 * @author Captain_Shadows
 */
public class MultyTexture extends BasicTexture
{

    protected List<BlockFacings> goodSides = new ArrayList<BlockFacings>();
    protected Icon[] icons = new Icon[8];

    public MultyTexture(final String iconName, final List<BlockFacings> goodSides)
    {
        super(iconName);
        this.goodSides = goodSides;
    }

    @Override
    public Icon getBlockTexture(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side)
    {

        final BaseTE te = (BaseTE) blockAccess.getBlockTileEntity(x, y, z);

        if (te != null)
        {
            if (goodSides.contains(BlockFacings.Front) && (side == te.getOrientationOrdinal()))
            {
                return icons[BlockFacings.Front.ordinal()];
            }
        }
        if (goodSides.contains(BlockFacings.Top) && (side == ForgeDirection.UP.ordinal()))
        {
            return icons[BlockFacings.Top.ordinal()];
        } else if (goodSides.contains(BlockFacings.Bottom) && (side == ForgeDirection.DOWN.ordinal()))
        {
            return icons[BlockFacings.Bottom.ordinal()];
        } else if (goodSides.contains(BlockFacings.Sides))
        {
            return icons[BlockFacings.Sides.ordinal()];
        } else
        {
            throw new RuntimeException("A CCM Member has derped, To the member: DO NOT FORGET TO ADD THINGS TO THE goodSides ARRAY!!!");
        }
    }

    @Override
    public Icon getIcon(final int side, final int meta)
    {
        if (goodSides.contains(BlockFacings.Front) && (side == ForgeDirection.SOUTH.ordinal()))
        {
            return icons[BlockFacings.Front.ordinal()];
        } else if (goodSides.contains(BlockFacings.Top) && (side == ForgeDirection.UP.ordinal()))
        {
            return icons[BlockFacings.Top.ordinal()];
        } else if (goodSides.contains(BlockFacings.Bottom) && (side == ForgeDirection.DOWN.ordinal()))
        {
            return icons[BlockFacings.Bottom.ordinal()];
        } else if (goodSides.contains(BlockFacings.Sides))
        {
            return icons[BlockFacings.Sides.ordinal()];
        } else
        {
            throw new RuntimeException("A CCM Member has derped, To the member: DO NOT FORGET TO ADD THINGS TO THE goodSides ARRAY!!!");
        }
    }

    @Override
    public void registerIcons(final IconRegister register)
    {
        for (final BlockFacings direction : goodSides)
        {
            icons[direction.ordinal()] = register.registerIcon(iconName + direction.name());
        }
    }
}