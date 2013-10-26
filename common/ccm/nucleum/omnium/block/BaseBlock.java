/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.nucleum.omnium.item.block.MainItemBlock;
import ccm.nucleum.omnium.utils.helper.CCMLogger;
import ccm.nucleum.omnium.utils.helper.FunctionHelper;

/**
 * MainBlock
 * <p>
 * A Block that redirects all of it's data to a specific meta data value
 * <p>
 * This class SHOULD NOT be extended unless it is something Block wide in which case one MUST Check that there is NO meta data way of doing the action
 */
public class BaseBlock extends Block
{

    public BaseBlock(int id, Material material)
    {
        super(id, material);
        // TODO Auto-generated constructor stub
    }

}