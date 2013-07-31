/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.helper.enums;

import net.minecraft.block.Block;

/**
 * This Interface Has to be extended by any enum that registers Block in order for it to work
 * correctly
 * 
 * @author Captain_Shadows
 */
public interface IBlockEnum
{

    /**
     * @return A new {@link Block} instance, the instance should be the registered and instantiated
     *         Block that contains all of these sub Block
     */
    public Block getBaseBlock();

    /**
     * @param base
     *            The Block to set the enum's base block as
     */
    public void setBaseBlock(Block base);
}