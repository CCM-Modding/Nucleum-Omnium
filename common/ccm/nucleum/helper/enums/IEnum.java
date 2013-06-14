package ccm.nucleum.helper.enums;

import net.minecraft.util.Icon;

/**
 * This interface is the common superclass of both IItemEnum and IBlockEnum,
 * thus it should only be extended by those two. DO NOT under any circumstance
 * extend it
 * 
 * @author Captain_Shadows
 */
public interface IEnum {
    
    /**
     * @return The Icon instance that belongs to the "Item" being used/modified
     */
    public Icon getIcon();
}