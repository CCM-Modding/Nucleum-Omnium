package ccm.nucleumOmnium.client.renderShapes;

/**
 * Point used for rendering stuff.
 * Used U, V and W to avoid confusion with the block coords.
 *
 * @author Dries007
 */
public class Point3D
{
    double U, V, W;

    public Point3D(double U, double V, double W)
    {
        this.U = U;
        this.V = V;
        this.W = W;
    }

    public double getU()
    {
        return U;
    }

    public void setU(double u)
    {
        this.U = u;
    }

    public double getV()
    {
        return V;
    }

    public void setV(double v)
    {
        this.V = v;
    }

    public double getW()
    {
        return W;
    }

    public void setW(double w)
    {
        W = w;
    }

    public Point3D copy()
    {
        return new Point3D(U, V, W);
    }

    public Point3D move(double U, double V, double W)
    {
        this.U += U;
        this.V += V;
        this.W += W;

        return this;
    }

    public Point3D moveNew(double U, double V, double W)
    {
        return copy().move(U, V, W);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3D point3D = (Point3D) o;

        return Double.compare(point3D.U, U) == 0 && Double.compare(point3D.V, V) == 0 && Double.compare(point3D.W, W) == 0;

    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(U);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(V);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(W);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return "Point3D[" + U + ';' + V + ';'+ W + ']';
    }
}
