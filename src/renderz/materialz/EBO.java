package renderz.materialz;

import interfacez.Bindable;
import interfacez.Disposable;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30C.*;

/**
 * <h2>Element Array Buffer Object / EBO</h2>
 * <br>
 * <p>The <code>EBO</code> is a Buffer used to store the indices from the <code>VBO</code> which OpenGL should use
 * when drawing primitives.
 * <br>
 * <br>
 * It can be attached to a <code>VAO</code> by binding it while the target <code>VAO</code> is bound.</p>
 */
public class EBO implements Disposable, Bindable
{
    private static EBO currentlyBoundEBO;
    private int[] indices;
    private int id, vertexSize, mode = GL_STATIC_DRAW;

    /**
     * Constructs a new Element Buffer Object.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     */
    public EBO (int[] indices, int vertexSize)
    {
        this.indices = indices;
        this.vertexSize = vertexSize;

        init();
    }

    public EBO () {}

    /**
     * Constructs a new Element Buffer Object.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     */
    public EBO (IntBuffer indices, int vertexSize)
    {
        this.indices = indices.array();
        this.vertexSize = vertexSize;

        init();
    }

    /**
     * Constructs a new Element Buffer Object.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     * @param mode wether the indices will be changed or not
     */
    public EBO (int[] indices, int vertexSize, int mode)
    {
        this.indices = indices;
        this.vertexSize = vertexSize;
        this.mode = mode;

        init();
    }

    /**
     * Constructs a new Element Buffer Object.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     * @param mode wether the indices will be changed or not
     */
    public EBO (IntBuffer indices, int vertexSize, int mode)
    {
        this.indices = indices.array();
        this.vertexSize = vertexSize;
        this.mode = mode;

        init();
    }

    private void init ()
    {
        this.id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indices, this.mode);
    }

    /**
     * Binds the EBO and unbinds the last bound EBO.
     */
    public void bind ()
    {
        if (EBO.currentlyBoundEBO == this)
        {
            return;
        }
        EBO.currentlyBoundEBO = this;
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.id);
    }

    /**
     * Unbinds the EBO.
     */
    public void unbind ()
    {
        if (EBO.currentlyBoundEBO == this) EBO.currentlyBoundEBO = null;
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Changes indices to new ones.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     */
    public void setIndices (int[] indices, int vertexSize)
    {
        this.indices = indices;
        this.vertexSize = vertexSize;

        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indices, this.mode);
        unbind();
    }

    /**
     * Changes indices to new ones.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     */
    public void setIndices (IntBuffer indices, int vertexSize)
    {
        this.indices = indices.array();
        this.vertexSize = vertexSize;

        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indices, this.mode);
        unbind();
    }

    /**
     * Changes indices to new ones.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     * @param mode wether the indices will be changed or not
     */
    public void setIndices (int[] indices, int vertexSize, int mode)
    {
        this.mode = mode;
        this.vertexSize = vertexSize;
        this.indices = indices;

        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indices, this.mode);
        unbind();
    }

    /**
     * Changes indices to new ones.
     * @param indices the indices which the EBO should use
     * @param vertexSize the number of elements in each vertex
     * @param mode wether the indices will be changed or not
     */
    public void setIndices (IntBuffer indices, int vertexSize, int mode)
    {
        this.mode = mode;
        this.indices = indices.array();
        this.vertexSize = vertexSize;

        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indices, this.mode);
        unbind();
    }

    /**
     * Disposes of the EBO, the object is unusable after.
     */
    public void dispose ()
    {
        if (EBO.currentlyBoundEBO == this) EBO.currentlyBoundEBO = null;
        glDeleteBuffers(this.id);
    }

    /**
     * Getter
     * @return OpenGL id of the EBO
     */
    public int getId ()
    {
        return this.id;
    }

    /**
     * Getter
     * @return the mode which is currently being used
     */
    public int getMode ()
    {
        return this.mode;
    }

    /**
     * Getter
     * @return the number of Elements in each Vertex
     */
    public int getVertexSize ()
    {
        return this.vertexSize;
    }

    /**
     * Getter
     * @return the Byte size of the number of Elements in each Vertex
     */
    public int getVertexSizeBytes ()
    {
        return this.vertexSize * Integer.BYTES;
    }

    /**
     * Getter
     * @return the currently bound EBO
     */
    public static EBO getCurrentlyBoundEBO ()
    {
        return EBO.currentlyBoundEBO;
    }
}

