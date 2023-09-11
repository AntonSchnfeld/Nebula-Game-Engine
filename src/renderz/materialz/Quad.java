package renderz.materialz;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderz.RenderBatch;

public class Quad implements Shape
{
    private float[] vertices;

    private static final float[] DEFAULT_UVS = {
            1, 0,
            0, 0,
            1, 1,
            0, 1
    };

    public Quad (float x, float y, float z, float width, float height, float r, float g, float b, float a, float[] uvs, float texID)
    {
        vertices = new float[]
                {
                        x, y, z, r, g, b, a, uvs[0], uvs[1], texID,
                        x+width, y, z, r, g, b, a, uvs[2], uvs[3], texID,
                        x, y+height, z, r, g, b, a, uvs[4], uvs[5], texID,
                        x+width, y+height, z, r, g, b, a, uvs[6], uvs[7], texID
                };
    }

    public Quad (Vector3f position, Vector2f scale, Vector4f color, float[] uvs, float texID)
    {
        this(position.x, position.y, position.z, scale.x, scale.y, color.x, color.y, color.z, color.w, uvs, texID);
    }

    public Quad (Vector3f position, Vector2f scale, Vector4f color, float texID)
    {
        this(position, scale, color, DEFAULT_UVS, texID);
    }

    public Quad (float x, float y, float z, float width, float height, float r, float b, float g, float a, float texID)
    {
        this(x, y, z, width, height, r, g, b, a, DEFAULT_UVS, texID);
    }

    @Override
    public void setVertices (float[] vertices)
    {
        if (vertices.length == RenderBatch.VERTEX_SIZE * ShapeType.QUADS.VERTEX_COUNT)
        {
            this.vertices = vertices;
            return;
        }
        throw new IllegalArgumentException("Quad vertices can only be "+(RenderBatch.VERTEX_SIZE * ShapeType.QUADS.VERTEX_COUNT)+" indices long");
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
