package ccm.nucleumOmnium.client.renderShapes;

import net.minecraft.util.Icon;

/**
 * For render shapes (only line, triangle and quad should uses this, more complex stuff should extend ComplexShape
 *
 * @author Dries007
 * @see ccm.nucleumOmnium.client.renderShapes.complexShapes.ComplexShape
 */
public interface IShape
{
    public void renderShapeWithIcon(Icon icon);

    public void renderShape();
}
