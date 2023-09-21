package renderz.materialz;

import entitiez.Transform;

public class Quad implements Shape
{
    private float[] vertices;

    public Quad (float x, float y, float z, float width, float height)
    {
        vertices = new float[]
                {
                        x, y, z,
                        x+width, y, z,
                        x, y+height, z,
                        x+width, y+height, z
                };
    }

    public Quad (Transform transform)
    {
        this(transform.getPosition().x, transform.getPosition().y, transform.getPosition().z, transform.getScale().x, transform.getScale().y);
    }

    @Override
    public void setVertices (float[] vertices)
    {
        if (vertices.length == 3 * ShapeType.QUADS.VERTEX_COUNT)
        {
            this.vertices = vertices;
            return;
        }
        throw new IllegalArgumentException("Quad vertices can only be "+(ShapeType.QUADS.VERTEX_COUNT * 3)+" indices long");
    }

    @Override
    public void setVertices (Transform transform)
    {
        vertices[0] = transform.getPosition().x;
        vertices[1] = transform.getPosition().y;
        vertices[2] = transform.getPosition().z;

        vertices[3] = transform.getPosition().x + transform.getScale().x;
        vertices[4] = transform.getPosition().y;
        vertices[5] = transform.getPosition().z;

        vertices[6] = transform.getPosition().x;
        vertices[7] = transform.getPosition().y + transform.getScale().y;
        vertices[8] = transform.getPosition().z;

        vertices[9] = transform.getPosition().x + transform.getScale().x;
        vertices[10] = transform.getPosition().y + transform.getScale().y;
        vertices[11] = transform.getPosition().z;
    }

    @Override
    public float[] getVertices()
    {
        return vertices;
    }

    @Override
    public ShapeType getShapeType ()
    {
        return ShapeType.QUADS;
    }
}
