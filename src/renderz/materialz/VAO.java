package renderz.materialz;

import interfacez.Bindable;
import interfacez.Disposable;

import static org.lwjgl.opengl.GL30C.*;

public class VAO implements Disposable, Bindable
{
    private static VAO currentlyBoundVAO;
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
        currentlyBoundVAO = this;
        glBindVertexArray(id);
    }

    public void unbind ()
    {
        if (currentlyBoundVAO == this)
        {
            currentlyBoundVAO = null;
        }
        glBindVertexArray(0);
    }

    /**
     * Deletes the VAO. The Object is unusable after this call.
     */
    public void dispose ()
    {
        if (currentlyBoundVAO == this)
        {
            currentlyBoundVAO = null;
        }
        glDeleteVertexArrays(id);
    }

    public void vertexAttribPtr (VBO vbo, int index)
    {
        if (currentlyBoundVAO != this)
        {
            bind();
        }
        if (VBO.getCurrentlyBoundVBO() != vbo)
        {
            vbo.bind();
        }
        glVertexAttribPointer(index, vbo.getVertexSize(), GL_FLOAT, false, vbo.getVertexSizeBytes(), 0);
    }

    public void vertexAttribPtr (VBO vbo, int index, int size)
    {
        if (currentlyBoundVAO != this)
        {
            bind();
        }
        if (VBO.getCurrentlyBoundVBO() != vbo)
        {
            vbo.bind();
        }
        glVertexAttribPointer(index, size, GL_FLOAT, false, vbo.getVertexSizeBytes(), 0);
    }

    /**
     * Stores information on how to pass data into a vertex shader attribute.
     * @param index the index of the vertex shader attribute
     */
    public void enableAttribute (int index)
    {
        if (currentlyBoundVAO != this)
        {
            bind();
        }
        glEnableVertexAttribArray(index);
    }

    public void disableAttribute (int index)
    {
        if (currentlyBoundVAO != this)
        {
            bind();
        }
        glDisableVertexAttribArray(index);
    }

    public static VAO getCurrentlyBoundVAO ()
    {
        return currentlyBoundVAO;
    }
}
