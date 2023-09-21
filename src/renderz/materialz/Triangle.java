package renderz.materialz;

import entitiez.Transform;

/**
 * <br>
 * A class implementing the Shape interface meant to work as a Triangle.
 *
 * @author AntonSchnfeld
 */
public class Triangle implements Shape
{
    private float[] vertices;

    /**
     * <br>
     * This constructor calls {@link Triangle#Triangle(float, float, float, float, float, float)} with topXOffset 0.
     *
     * @param transform the transform whose values will be used
     */
    public Triangle (Transform transform)
    {
        this(transform.getPosition().x, transform.getPosition().y, transform.getPosition().y, transform.getScale().x, transform.getScale().y, 0);
    }

    /**
     * <br>
     * Creates a new Triangle object.
     *
     * @param x x coordinate of the bottom left vertex
     * @param y y coordinate of the bottom left vertex
     * @param z z coordinate of the triangle
     * @param width the horizontal distance between the bottom left and bottom right vertices of the triangle
     * @param height the vertical distance between the bottom left vertex and the top vertex
     * @param topXOffset the x offset of the top vertex from the x coordinate of the bottom left vertex
     */
    public Triangle (float x, float y, float z, float width, float height, float topXOffset)
    {
        vertices = new float[]
                {
                        x, y, z, // Bottom left
                        x + width, y, z, // Bottom right
                        x + topXOffset, y + height, z // Top
                };
    }

    /**
     * <br>
     * First checks if the length of the new vertices is accurate, then sets them.
     * If vertices.length is not equal to {@link ShapeType}.TRIANGLES * 3 a IllegalArgumentException is thrown.
     *
     * @param vertices the new vertices
     */
    @Override
    public void setVertices(float[] vertices)
    {
        if (vertices.length == ShapeType.TRIANGLES.VERTEX_COUNT * 3)
        {
            this.vertices = vertices;
            return;
        }

        throw new IllegalArgumentException("Point vertex can only be " + (3 * ShapeType.TRIANGLES.VERTEX_COUNT) + " indices long");
    }

    @Override
    public void setVertices(Transform transform)
    {
        vertices[0] = transform.getPosition().x;
        vertices[1] = transform.getPosition().y;
        vertices[2] = transform.getPosition().z;

        vertices[3] = transform.getPosition().x + transform.getPosition().x;
        vertices[4] = transform.getPosition().y;
        vertices[5] = transform.getPosition().z;

        vertices[6] = transform.getPosition().x;
        vertices[7] = transform.getPosition().y + transform.getScale().y;
        vertices[8] = transform.getPosition().z;
    }

    /**
     * <br>
     * Returns the vertices.
     *
     * @return the vertices
     */
    @Override
    public float[] getVertices()
    {
        return vertices;
    }

    /**
     * <br>
     * Returns the {@link ShapeType} of this {@link Shape}
     *
     * @return {@link ShapeType}.TRIANGLES
     */
    @Override
    public ShapeType getShapeType()
    {
        return ShapeType.TRIANGLES;
    }
}
