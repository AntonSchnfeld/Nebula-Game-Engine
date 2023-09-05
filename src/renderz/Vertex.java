package renderz;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex
{
    // VERTEX
    //=========
    //Position                  Colour                          UV             Tex Id
    //float, float, float       float, float, float, float      float, float    float
    private final Vector3f vertices;
    private final Vector4f colour;
    private final Vector2f uv;
    private float id;

    public Vertex (Vector3f vertices, Vector4f colour, Vector2f uv, float id)
    {
        this.vertices = vertices;
        this.uv = uv;
        this.colour = colour;
        this.id = id;
    }

    public Vertex (float[] vertices)
    {
        if (vertices.length != 10) throw new IllegalArgumentException("vertices Array has to contain 10 elements");

        this.vertices = new Vector3f(vertices[0], vertices[1], vertices[2]);
        this.colour = new Vector4f(vertices[3], vertices[4], vertices[5], vertices[6]);
        this.uv = new Vector2f(vertices[7], vertices[8]);
        this.id = vertices[9];
    }

    public Vertex ()
    {
        this.vertices = new Vector3f();
        this.uv = new Vector2f();
        this.colour = new Vector4f();
        this.id = 0;
    }

    public Vertex withVertices (Vector3f vertices)
    {
        this.vertices.set(vertices);
        return this;
    }

    public Vertex withColour (Vector4f colour)
    {
        this.colour.set(colour);
        return this;
    }

    public Vertex withUV (Vector2f uv)
    {
        this.uv.set(uv);
        return this;
    }

    public Vertex withTexID (float texID)
    {
        this.id = texID;
        return this;
    }

    public float getId ()
    {
        return id;
    }

    public Vector4f getColour ()
    {
        return colour;
    }

    public Vector2f getUv ()
    {
        return uv;
    }

    public Vector3f getVertices ()
    {
        return vertices;
    }
}
