package ccm.nucleumOmnium.client.renderShapes;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

/**
 * Base shape with 4 points. Make sure the coords are put in the right order or your shape will render backwards.
 *
 * @author Dries007
 */
public class Quad implements IShape
{
    final Point3D[] points = new Point3D[4];

    public Quad(Point3D p1, Point3D p2, Point3D p3, Point3D p4)
    {
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;
    }

    @Override
    public void renderShapeWithIcon(Icon icon)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        for (Point3D point : points) tessellator.addVertexWithUV(point.getU(), point.getV(), point.getW(), icon.getInterpolatedU(point.getU()), icon.getInterpolatedV(point.getW()));
        tessellator.draw();
    }

    @Override
    public void renderShape()
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        for (Point3D point : points) tessellator.addVertex(point.getU(), point.getV(), point.getW());
        tessellator.draw();
    }
}
