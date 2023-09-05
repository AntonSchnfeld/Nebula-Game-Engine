package renderz.materialz;

import interfacez.Bindable;
import interfacez.Disposable;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL30C.*;

public class VBO implements Disposable, Bindable
{
    private static VBO currentlyBoundVBO;
    private final int id = glGenBuffers();
    private int vertexSize, vertexSizeBytes;
    private int mode = GL_DYNAMIC_DRAW, target = GL_ARRAY_BUFFER;

    /*
                    ===============================
                    |         Constructors        |
                    ===============================
     */

    public VBO (int floatCount, int vertexSize)
    {
        init(floatCount, vertexSize, mode, target);
    }

    public VBO ()
    {}

    public VBO (int floatCount, int vertexSize, int mode)
    {
        init(floatCount, vertexSize, mode, target);
    }

    public VBO (int floatCount, int vertexSize, int mode, int target)
    {
        init(floatCount, vertexSize, mode, target);
    }

    private void init (int floatCount, int vertexSize, int mode, int target)
    {
        if (currentlyBoundVBO != this) bind();
        glBufferData(target, floatCount, mode);
        this.mode = mode;
        this.target = target;
        this.vertexSize = vertexSize;
        recalculateVertexSizeBytes();
    }

    public VBO(float[] vertices, int vertexSize)
    {
        init(vertices, vertexSize, target, mode);
    }

    public VBO(FloatBuffer vertices, int vertexSize)
    {
        init(vertices.array(), vertexSize, target, mode);
    }

    public VBO(float[] vertices, int vertexSize, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        init(vertices, vertexSize, target, mode);
    }

    public VBO(FloatBuffer vertices, int vertexSize, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        init(vertices.array(), vertexSize, target, mode);
    }

    public VBO(float[] vertices, int vertexSize, int mode, int target)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        init(vertices, vertexSize, target, mode);
    }

    public VBO(FloatBuffer vertices, int vertexSize, int mode, int target)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        init(vertices.array(), vertexSize, target, mode);
    }

    /*
                    ===============================
                    |       Private methods       |
                    ===============================
     */
    private boolean isValidMode (int mode)
    {
        return mode == GL_DYNAMIC_DRAW || mode == GL_STATIC_DRAW || mode == GL_STREAM_DRAW;
    }

    private void recalculateVertexSizeBytes ()
    {
        this.vertexSizeBytes = vertexSize * Float.BYTES;
    }
    private void init (float[] vertices, int vertexSize, int target, int mode)
    {
        if (currentlyBoundVBO != this) bind();
        this.vertexSize = vertexSize;
        this.target = target;
        this.mode = mode;
        glBufferData(target, vertices, mode);
        recalculateVertexSizeBytes();
    }

    private void reBuffer (float[] vertices, int target)
    {
        if (currentlyBoundVBO != this) currentlyBoundVBO = this;
        this.target = target;
        glBufferSubData(target, 0, vertices);
        recalculateVertexSizeBytes();
    }

    /*
                    ===============================
                    |      Setters / Getters      |
                    ===============================
     */
    public void setVertices (float[] vertices, int vertexSize)
    {
        if (vertexSize == this.vertexSize)
        {
            reBuffer(vertices, target);
            return;
        }
        init(vertices, vertexSize, target, mode);
    }

    public void setVertices (FloatBuffer vertices, int vertexSize)
    {
        if (vertexSize == this.vertexSize)
        {
            reBuffer(vertices.array(), target);
            return;
        }
        init(vertices.array(), vertexSize, target, mode);
    }

    public void setVertices (float[] vertices, int vertexSize, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        if (vertexSize == this.vertexSize)
        {
            reBuffer(vertices, target);
            return;
        }
        init(vertices, vertexSize, target, mode);
    }

    public void setVertices (FloatBuffer vertices, int vertexSize, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        if (vertexSize == this.vertexSize)
        {
            reBuffer(vertices.array(), target);
            return;
        }
        init(vertices.array(), vertexSize, target, mode);
    }

    public void setBufferSubData (float[] subData, int index)
    {
        if (currentlyBoundVBO != this)
        {
            bind();
        }

        glBufferSubData(GL_ARRAY_BUFFER, (long) index * Float.BYTES, subData);
    }

    /**
     * Deletes all Buffer data if there was any and reserves space in the Buffer for the amount of floats defined by floatcount.
     * @param target the target of the GL call
     * @param floatCount the number of floats which space gets reserved for
     * @param mode the mode which GL will reserve the memory for
     */
    public void reserveSpace (int target, int floatCount, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }
        if (currentlyBoundVBO != this) bind();
        glBufferData(target, (long) floatCount * Float.BYTES, mode);
    }

    public int getId ()
    {
        return id;
    }

    public int getVertexSize()
    {
        return vertexSize;
    }

    public int getMode ()
    {
        return mode;
    }

    public int getTarget ()
    {
        return target;
    }

    public int getVertexSizeBytes ()
    {
        return vertexSizeBytes;
    }

    /*
                    ===============================
                    |           Methods           |
                    ===============================
     */

    public void bind ()
    {
        if (currentlyBoundVBO == this) return;
        currentlyBoundVBO = this;
        glBindBuffer(target, id);
    }


    public void unbind ()
    {
        if (currentlyBoundVBO == this) currentlyBoundVBO = null;
        glBindBuffer(target, 0);
    }

    public void dispose ()
    {
        if (currentlyBoundVBO == this) currentlyBoundVBO = null;
        glDeleteBuffers(id);
    }

    public static VBO getCurrentlyBoundVBO ()
    {
        return currentlyBoundVBO;
    }
}
