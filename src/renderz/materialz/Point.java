package renderz.materialz;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderz.RenderBatch;

public class Point implements Shape
{
    private float[] vertices;

    public Point (Vector3f position, Vector4f color, Vector2f uv, float texID)
    {
        this(position.x, position.y, position.z, color.x, color.y, color.z, color.w, uv.x, uv.y, texID);
    }

    public Point (float x, float y, float z, float r, float g, float b, float a, float u, float v, float texID)
    {
        vertices = new float[]
                {
                        x, y, z, r, g, b, a, u, v, texID
                };
    }

    @Override
    public void setVertices (float[] vertices)
    {
        if (vertices.length == RenderBatch.VERTEX_SIZE)
        {
            this.vertices = vertices;
            return;
        }

        throw new IllegalArgumentException("Point vertex can only be "+(RenderBatch.VERTEX_SIZE * ShapeType.POINTS.VERTEX_COUNT)+" indices long");
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
