package ccm.nucleumOmnium.client.renderShapes.complexShapes;

import ccm.nucleumOmnium.client.renderShapes.IShape;
import net.minecraft.util.Icon;

public abstract class ComplexShape implements IShape
{
    public abstract IShape[] getSubShapes();

    @Override
    public void renderShapeWithIcon(Icon icon)
    {
        for (IShape shape : getSubShapes()) shape.renderShapeWithIcon(icon);
    }

    @Override
    public void renderShape()
    {
        for (IShape shape : getSubShapes()) shape.renderShape();
    }
}
