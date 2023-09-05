package renderz;

import static org.lwjgl.opengl.GL30C.*;

public enum Shape
{
    QUADS (4, 6, GL_TRIANGLES),
    TRIANGLES (3, 3, GL_TRIANGLES),
    POINTS (1, 1, GL_POINTS),
    LINES (2, 2, GL_LINES);

    public final int VERTEX_COUNT, INDEX_COUNT, GL_DRAW_MODE;

    private Shape (int VERTEX_COUNT, int INDEX_COUNT, int GL_DRAW_MODE)
    {
        this.INDEX_COUNT = INDEX_COUNT;
        this.VERTEX_COUNT = VERTEX_COUNT;
        this.GL_DRAW_MODE = GL_DRAW_MODE;
    }

    private Shape (int VERTEX_COUNT, int GL_DRAW_MODE)
    {
        this (VERTEX_COUNT, VERTEX_COUNT, GL_DRAW_MODE);
    }

}
