package ccm.nucleumOmnium.client.renderShapes;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import org.lwjgl.opengl.GL11;

public class Line implements IShape
{
    final Point3D[] points = new Point3D[2];

    public Line(Point3D p1, Point3D p2)
    {
        points[0] = p1;
        points[1] = p2;
    }

    @Override
    public void renderShapeWithIcon(Icon icon)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_LINES);
        for (Point3D point : points) tessellator.addVertex(point.getU(), point.getV(), point.getW());
        tessellator.draw();
    }

    @Override
    public void renderShape()
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_LINES);
        for (Point3D point : points) tessellator.addVertex(point.getU(), point.getV(), point.getW());
        tessellator.draw();
    }
}
