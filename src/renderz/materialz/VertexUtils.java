package renderz.materialz;

import org.joml.Vector4f;
import renderz.RenderBatch;

public class VertexUtils
{
    // This is not a class that should be instantiated
    private VertexUtils () {}

    /**
     * Checks if vertices are valid
     * @param vertices the vertices which need to be checked
     * @param shapeType the ShapeType which of the vertices
     * @return wether these vertices have the correct length for that ShapeType
     */
    public static boolean validateVertices (float[] vertices, ShapeType shapeType)
    {
        return vertices.length == shapeType.VERTEX_COUNT * RenderBatch.VERTEX_SIZE;
    }

    /**
     * Increments x coordinate of all vertices.
     * @param vertices the float[] to be changed
     * @param shapeType the ShapeType of the vertices
     * @param x is added to every x coordinate in the array
     */
    public static void moveX (float[] vertices, ShapeType shapeType, float x)
    {
        for (int i = 0; i < shapeType.VERTEX_COUNT; i++)
        {
            vertices[i * RenderBatch.VERTEX_SIZE] += x;
        }
    }

    /**
     * Increments y coordinate of all vertices.
     * @param vertices the float[] to be changed
     * @param shapeType the ShapeType of the vertices
     * @param y is added to every y coordinate in the array
     */
    public static void moveY (float[] vertices, ShapeType shapeType, float y)
    {
        for (int i = 0; i < shapeType.VERTEX_COUNT; i++)
        {
            vertices[(i * RenderBatch.VERTEX_SIZE) + 1] += y;
        }
    }

    public static void moveZ (float[] vertices, ShapeType shapeType, float z)
    {
        for (int i = 0; i < shapeType.VERTEX_COUNT; i++)
        {
            vertices[(i * RenderBatch.VERTEX_SIZE) + 2] += z;
        }
    }

    /**
     * Sets all vertices of the float[] to that color.
     * @param vertices the float[] of vertices
     * @param shapeType the ShapeType of the vertices
     * @param color the color all the vertices should be set to
     */
    public void setColor (float[] vertices, ShapeType shapeType, Vector4f color)
    {
        for (int i = 0; i < shapeType.VERTEX_COUNT; i++)
        {
            int colorPos = i * RenderBatch.VERTEX_SIZE + ( RenderBatch.COLOUR_SIZE + RenderBatch.POSITION_SIZE );

            vertices[colorPos++] = color.x; // Red
            vertices[colorPos++] = color.y; // Blue
            vertices[colorPos++] = color.z; // Green
            vertices[colorPos] = color.w; // Alpha
        }
    }

    /**
     * Sets the texID of all vertices.
     * @param vertices the float[] of vertices
     * @param shapeType the ShapeType of the vertices
     * @param texID the new texture id
     */
    public void setTextureID (float[] vertices, ShapeType shapeType, float texID)
    {
        for (int i = 0; i < shapeType.VERTEX_COUNT; i++)
        {
            int texIDPosition = i * RenderBatch.VERTEX_SIZE + ( RenderBatch.POSITION_SIZE + RenderBatch.COLOUR_SIZE + RenderBatch.UV_SIZE );

            vertices[texIDPosition] = texID;
        }
    }
}
