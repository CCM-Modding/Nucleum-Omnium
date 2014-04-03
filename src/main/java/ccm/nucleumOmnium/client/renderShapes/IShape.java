package ccm.nucleumOmnium.client.renderShapes;

import net.minecraft.util.Icon;

/**
 * For render shapes (only line, triangle and quad should uses this, more complex stuff should use IComplexShape
 *
 * @see ccm.nucleumOmnium.client.renderShapes.IComplexShape
 * @author Dries007
 */
public interface IShape
{
    public void renderShapeWithIcon(Icon icon);

    public void renderShape();
}
