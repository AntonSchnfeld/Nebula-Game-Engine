package renderz.materialz;

import entitiez.Transform;

public class Point implements Shape
{
    private float[] vertices;
    private float width, height;

    public Point (Transform transform)
    {
        this(transform.getPosition().x, transform.getPosition().y, transform.getPosition().z, transform.getScale().x, transform.getScale().y);
    }

    public Point (float x, float y, float z, float width, float height)
    {
        this.height = height;
        this.width = width;
        vertices = new float[]
                {
                        x, y, z
                };
    }

    @Override
    public void setVertices (float[] vertices)
    {
        if (vertices.length == 3)
        {
            this.vertices = vertices;
            return;
        }

        throw new IllegalArgumentException("Point vertex can only be "+ (3)+" indices long");
    }

    @Override
    public void setVertices(Transform transform)
    {
        this.width = transform.getScale().x;
        this.height = transform.getScale().y;

        vertices[0] = transform.getPosition().x;
        vertices[1] = transform.getPosition().y;
        vertices[2] = transform.getPosition().z;
    }

    @Override
    public float[] getVertices ()
    {
        return vertices;
    }

    @Override
    public ShapeType getShapeType ()
    {
        return ShapeType.POINTS;
    }
}
