package renderz.materialz;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Quad
{
    private float[] vertices;

    public Quad (Vector3f position, Vector2f dimensions, Vector4f color, float texID)
    {
        vertices = new float[]{
                position.x, position.y, position.z, color.x, color.y, color.z, color.w, 1, 0, texID,

        };
    }
}
