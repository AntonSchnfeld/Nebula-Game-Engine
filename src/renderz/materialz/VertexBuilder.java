package renderz.materialz;

import renderz.Vertex;

import java.util.ArrayList;
import java.util.List;

public class VertexBuilder
{
    private final List<Vertex> vertices;

    public VertexBuilder ()
    {
        vertices = new ArrayList<>();
    }

    public List<Vertex> getVerticesAsList ()
    {
        return vertices;
    }

    @SuppressWarnings("all")
    public Vertex[] getVerticesAsArray ()
    {
        return vertices.toArray(new Vertex[vertices.size()]);
    }

    public VertexBuilder addVertex (Vertex vertex)
    {
        vertices.add(vertex);
        return this;
    }
}
