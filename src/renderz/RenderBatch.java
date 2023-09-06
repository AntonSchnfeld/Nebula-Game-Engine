package renderz;

import entitiez.componentz.RenderComponent;
import renderz.texturez.Texture2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL30C.*;

public class RenderBatch
{
    // Vertex
    // Position                 Colour                          UV              TexId
    // float, float, float      float, float, float, float      float, float    float

    private static final int DEFAULT_BATCH_SIZE = 1000;
    private static final int POSITION_OFFSET = 0;
    private static final int POSITION_SIZE = 3;
    private static final int POSITION_SIZE_BYTES = POSITION_SIZE * Float.BYTES;
    private static final int COLOUR_OFFSET = POSITION_OFFSET + POSITION_SIZE_BYTES;
    private static final int COLOUR_SIZE = 4;
    private static final int COLOUR_SIZE_BYTES = COLOUR_SIZE * Float.BYTES;
    private static final int UV_OFFSET = COLOUR_OFFSET + COLOUR_SIZE_BYTES;
    private static final int UV_SIZE = 2;
    private static final int UV_SIZE_BYTES = UV_SIZE * Float.BYTES;
    private static final int TEXTURE_ID_OFFSET = UV_OFFSET + UV_SIZE_BYTES;
    private static final int TEXTURE_ID_SIZE = 1;
    private static final int TEXTURE_ID_SIZE_BYTES = TEXTURE_ID_SIZE * Float.BYTES;
    private static final int VERTEX_OFFSET = TEXTURE_ID_OFFSET + TEXTURE_ID_SIZE_BYTES;
    private static final int VERTEX_SIZE = POSITION_SIZE + COLOUR_SIZE + UV_SIZE + TEXTURE_ID_SIZE;
    private static final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private static final int TEXTURE_COUNT = 10;

    private final int EBO, VAO, VBO;
    private final int MAX_BATCHSIZE;
    private int vertexCount;
    private int[] indices;
    private float[] vertices;
    private final List<RenderComponent> RENDER_COMPONENTS;
    private final List<Texture2D> TEXTURES;
    private final Shape SHAPE;

    public RenderBatch (int maxBatchSize, Shape shape)
    {
        this.SHAPE = shape;
        this.MAX_BATCHSIZE = maxBatchSize;
        this.vertexCount = 0;

        this.VAO = glGenVertexArrays();
        this.VBO = glGenBuffers();
        this.EBO = glGenBuffers();

        this.vertices = new float[shape.VERTEX_COUNT * VERTEX_SIZE * maxBatchSize];
        this.indices = new int[maxBatchSize * shape.INDEX_COUNT];

        generateIndices();

        this.RENDER_COMPONENTS = new ArrayList<>();
        this.TEXTURES = new ArrayList<>();

        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW);
    }

    public RenderBatch (Shape shape)
    {
        this(DEFAULT_BATCH_SIZE, shape);
    }

    private void generateIndices ()
    {
        // If this is a QUAD based renderer, generate Quad indices
        if (SHAPE == Shape.QUADS)
        {
            int arrayIndex = 0;
            int indexOffset;
            for (int i = 0; i < MAX_BATCHSIZE; i++)
            {
                indexOffset = i * 4;
                indices[arrayIndex++] = indexOffset + 3;
                indices[arrayIndex++] = indexOffset + 2;
                indices[arrayIndex++] = indexOffset + 0;

                indices[arrayIndex++] = indexOffset + 0;
                indices[arrayIndex++] = indexOffset + 2;
                indices[arrayIndex++] = indexOffset + 1;
            }
            return;
        }

        // Otherwise generate a linear array, since the other shapes don't really require a EBO
        for (int i = 0; i < MAX_BATCHSIZE; i++)
        {
            indices[i] = i;
        }
    }

    public boolean hasRoom ()
    {
        return vertexCount < MAX_BATCHSIZE * SHAPE.VERTEX_COUNT;
    }

    public boolean hasTextureRoom ()
    {
        return TEXTURES.size() < 10;
    }

    private void addVertex (float[] vertex)
    {
        int index = vertexCount * VERTEX_SIZE;

        // Position
        vertices[index++] = vertex[0];
        vertices[index++] = vertex[1];
        vertices[index++] = vertex[2];

        // Color
        vertices[index++] = vertex[3];
        vertices[index++] = vertex[4];
        vertices[index++] = vertex[5];
        vertices[index++] = vertex[6];

        // UVs
        vertices[index++] = vertex[7];
        vertices[index++] = vertex[8];

        // Texture ID
        vertices[index] = vertex[9];

        vertexCount++;
    }

    private void deleteVertex (int index)
    {
        // TODO: Add some sort of way to actually delete array elements and move all other vertices behind that by VERTEX_SIZE

        int elementsToDelete = Math.min(VERTEX_SIZE, vertices.length - index);

        System.arraycopy(vertices, index + elementsToDelete, vertices, index, vertices.length - index - elementsToDelete);

        for (int i = vertices.length - elementsToDelete; i < vertices.length; i++)
        {
            vertices[i] = 0.0f;
        }

        vertexCount--;
    }

    public void removeRenderComponent (RenderComponent rc)
    {
        if (!RENDER_COMPONENTS.contains(rc)) return;

        int index = RENDER_COMPONENTS.indexOf(rc);

        for (int i = 0; i < SHAPE.VERTEX_COUNT; i++)
        {
            deleteVertex(index * SHAPE.VERTEX_COUNT * VERTEX_SIZE);
        }

        RENDER_COMPONENTS.remove(rc);
    }

    public void addRenderComponent (RenderComponent rc)
    {
        if (!hasRoom()) throw new IllegalStateException("Tried to add a RenderComponent even though max batch size has been achieved");
        if (!TEXTURES.contains(rc.getSprite().getTexture()))
        {
            if (!hasTextureRoom()) throw new IllegalStateException("Tried to add a RenderComponent with new texture even though there is no texture room left");

            TEXTURES.add(rc.getSprite().getTexture());
        }

        RENDER_COMPONENTS.add(rc);

        for (int i = 0; i < SHAPE.VERTEX_COUNT; i++)
        {
            // Get sub array of a single vertex and pass it into addVertex
            // i * VERTEX_SIZE gets the start of the next vertex
            // (i + 1) * VERTEX_SIZE gets its end
            addVertex(Arrays.copyOfRange(rc.getVertices(), i * VERTEX_SIZE, (i + 1) * VERTEX_SIZE));
        }
    }

    public float[] getVertices ()
    {
        return vertices;
    }
}
