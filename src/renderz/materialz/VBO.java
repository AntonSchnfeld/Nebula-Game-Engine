package renderz.materialz;

import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL30C.*;

public class VBO
{
    private final int id = glGenBuffers();
    private int vertexSize, vertexSizeBytes;
    private int mode = GL_DYNAMIC_DRAW, target = GL_ARRAY_BUFFER;
    private float[] vertices;

    /*
                    ===============================
                    |         Constructors        |
                    ===============================
     */

    public VBO (float[] vertices, int vertexSize)
    {
        this.vertices = vertices;
        this.vertexSize = vertexSize;

        init();
    }

    public VBO (FloatBuffer vertices, int vertexSize)
    {
        this.vertices = vertices.array();
        this.vertexSize = vertexSize;

        init();
    }

    public VBO (float[] vertices, int vertexSize, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        this.vertices = vertices;
        this.vertexSize = vertexSize;
        this.mode = mode;

        init();
    }

    public VBO (FloatBuffer vertices, int vertexSize, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        this.vertices = vertices.array();
        this.vertexSize = vertexSize;
        this.mode = mode;

        init();
    }

    public VBO (float[] vertices, int vertexSize, int mode, int target)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        this.vertices = vertices;
        this.vertexSize = vertexSize;
        this.mode = mode;
        this.target = target;

        init();
    }

    public VBO (FloatBuffer vertices, int vertexSize, int mode, int target)
    {
        this.vertices = vertices.array();
        this.vertexSize = vertexSize;
        this.mode = mode;
        this.target = target;

        init();
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
    private void init ()
    {
        bind();
        glBufferData(target, vertices, mode);
        recalculateVertexSizeBytes();
    }

    /*
                    ===============================
                    |      Setters / Getters      |
                    ===============================
     */
    public void setVertices (float[] vertices, int stride)
    {
        this.vertices = vertices;
        this.vertexSize = stride;

        init();
    }

    public void setVertices (FloatBuffer vertices, int stride)
    {
        this.vertices = vertices.array();
        this.vertexSize = stride;

        init();
    }

    public void setVertices (float[] vertices, int stride, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        this.vertices = vertices;
        this.vertexSize = stride;
        this.mode = mode;

        init();
    }

    public void setVertices (FloatBuffer vertices, int stride, int mode)
    {
        if (!isValidMode(mode))
        {
            throw new IllegalArgumentException("mode can only be equal to GL_DYNAMIC_DRAW or GL_STATIC_DRAW");
        }

        this.vertices = vertices.array();
        this.vertexSize = stride;
        this.mode = mode;

        init();
    }

    public float[] getVertices ()
    {
        return vertices;
    }

    public FloatBuffer getVerticesBuffer ()
    {
        return FloatBuffer.wrap(vertices);
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
        glBindBuffer(target, id);
    }


    public void unbind ()
    {
        glBindBuffer(target, 0);
    }

    public void delete ()
    {
        glDeleteBuffers(id);
    }
}
