package renderz.materialz;

import static org.lwjgl.opengl.GL30C.*;

public class VAO
{
    private final int id = glGenVertexArrays();

    /**
     * Constructs a Vertex Array Object.
     */
    public VAO () {}

    /**
     * Binds the VAO.
     */
    public void bind ()
    {
        glBindVertexArray(id);
    }

    public void unbind ()
    {
        glBindVertexArray(0);
    }

    /**
     * Deletes the VAO.
     */
    public void delete ()
    {
        glDeleteVertexArrays(id);
    }

    /**
     * Stores information on how to pass data into a vertex shader attribute.
     * @param vbo the VBO from which the information is being pulled
     * @param index the index of the vertex shader attribute
     */
    public void enableAttribute (VBO vbo, int index)
    {
        bind();
        vbo.bind();
        glVertexAttribPointer(index, vbo.getVertexSize(), GL_FLOAT, false, vbo.getVertexSizeBytes(), 0);
        glEnableVertexAttribArray(index);
        vbo.unbind();
        unbind();
    }
}
