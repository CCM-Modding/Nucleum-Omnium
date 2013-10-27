/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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