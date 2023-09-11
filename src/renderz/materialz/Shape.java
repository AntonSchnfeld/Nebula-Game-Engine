package renderz.materialz;

public interface Shape
{

    /**
     * Sets the vertices of the shape.
     * @param vertices
     * @throws IllegalArgumentException if length of vertices is not equal to getShapeType().VERTEX_COUNT * RenderBatch.VERTEX_SIZE
     */
    void setVertices (float[] vertices);

    /**
     * Getter
     * @return the vertices
     */
    float[] getVertices();

    /**
     * Getter
     * @return the ShapeType of the Shape
     */
    ShapeType getShapeType();
}
