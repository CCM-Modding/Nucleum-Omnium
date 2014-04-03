package ccm.nucleumOmnium.client.renderShapes.complexShapes;

import ccm.nucleumOmnium.client.renderShapes.IShape;
import ccm.nucleumOmnium.client.renderShapes.Line;
import ccm.nucleumOmnium.client.renderShapes.Point3D;

public class Wireframe extends ComplexShape
{
    final IShape[] shapes = new IShape[12];

    public Wireframe(double size)
    {
        init(new Point3D(0, 0, 0), size);
    }

    public Wireframe()
    {
        init(new Point3D(0, 0, 0), 0.55D);
    }

    public Wireframe(Point3D center, double size)
    {
        init(center, size);
    }

    public Wireframe(Point3D center)
    {
        init(center, 0.55D);
    }

    private void init(Point3D center, double size)
    {
        Point3D b1 = center.moveNew(-size, -size, -size);
        Point3D b2 = center.moveNew(-size, -size, size);
        Point3D b3 = center.moveNew(size, -size, -size);
        Point3D b4 = center.moveNew(size, -size, size);

        Point3D t1 = center.moveNew(-size, -size, -size);
        Point3D t2 = center.moveNew(-size, -size, size);
        Point3D t3 = center.moveNew(size, -size, -size);
        Point3D t4 = center.moveNew(size, -size, size);
        
        shapes[0] = new Line(b1, b2);
        shapes[1] = new Line(b2, b3);
        shapes[2] = new Line(b3, b4);
        shapes[3] = new Line(b4, b1);

        shapes[4] = new Line(t1, t2);
        shapes[5] = new Line(t2, t3);
        shapes[6] = new Line(t3, t4);
        shapes[7] = new Line(t4, t1);

        shapes[8]  = new Line(b1, t1);
        shapes[9]  = new Line(b2, t2);
        shapes[10] = new Line(b3, t3);
        shapes[11] = new Line(b4, t4);
    }

    @Override
    public IShape[] getSubShapes()
    {
        return shapes;
    }
}
