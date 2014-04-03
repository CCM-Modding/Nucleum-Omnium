package ccm.nucleumOmnium.client.renderShapes.complexShapes;

import ccm.nucleumOmnium.client.renderShapes.IShape;
import ccm.nucleumOmnium.client.renderShapes.Point3D;
import ccm.nucleumOmnium.client.renderShapes.Quad;

/**
 * Flat regular octagon in the horizontal plane.
 *
 * @author Dries007
 */
public class Octagon extends ComplexShape
{
    final IShape[] shapes = new IShape[3];

    public Octagon(Point3D center, final double rad, final double yLevel)
    {
        shapes[0] = new Quad(center.moveNew(-rad, yLevel, -rad / 2), center.moveNew(-rad, yLevel, rad / 2), center.moveNew(-rad / 2, yLevel, rad), center.moveNew(-rad / 2, yLevel, -rad));
        shapes[1] = new Quad(center.moveNew(-rad / 2,    yLevel,     -rad),
                center.moveNew(-rad / 2,    yLevel,     rad),
                center.moveNew(rad / 2,     yLevel,     rad),
                center.moveNew(rad / 2,     yLevel,     -rad));
        shapes[2] = new Quad(
                center.moveNew(rad / 2,     yLevel,     -rad),
                center.moveNew(rad / 2,     yLevel,     rad),
                center.moveNew(rad,         yLevel,     rad / 2),
                center.moveNew(rad,         yLevel,     -rad / 2));
    }

    @Override
    public IShape[] getSubShapes()
    {
        return shapes;
    }
}
